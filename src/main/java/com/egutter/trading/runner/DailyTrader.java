package com.egutter.trading.runner;

import com.egutter.trading.finnhub.FinnhubClient;
import com.egutter.trading.order.BuyBracketOrder;
import com.egutter.trading.order.BuyOrderWithPendingSellOrders;
import com.egutter.trading.out.BuyBracketOrdersFileHandler;
import com.egutter.trading.tda.QuoteAdapter;
import com.egutter.trading.tda.TooExpensiveOrder;
import com.studerw.tda.client.HttpTdaClient;
import com.studerw.tda.client.TdaClient;
import com.studerw.tda.model.account.*;
import com.studerw.tda.model.quote.EquityQuote;
import com.studerw.tda.model.quote.Quote;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DailyTrader {

    private final static Logger logger = LoggerFactory.getLogger(DailyTrader.class);

    private String accountId;
    private TdaClient client;
    private BigDecimal cashAvailable;
    private CashAccount tdaAccount;
    private LocalDate tradeOn = LocalDate.now();
    private LocalDate lastTradeAt;
    private String baseOrdersPath;
    private final static LocalDate today = LocalDate.now().plusDays(1);

    public DailyTrader(TdaClient client, LocalDate tradeOn, LocalDate lastTradeAt, String baseOrdersPath) {
        this.client = client;
        this.tradeOn = tradeOn;
        this.lastTradeAt = lastTradeAt;
        this.baseOrdersPath = baseOrdersPath;
        CashAccount tdaAccount = fetchCashAccount(client);
        this.cashAvailable = tdaAccount.getCurrentBalances().getCashAvailableForTrading();
        this.tdaAccount = tdaAccount;
        this.accountId = tdaAccount.getAccountId();
    }

    public DailyTrader(BigDecimal cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    public static void main1(String[] args) throws IOException {

        TdaClient client = new HttpTdaClient();
        CashAccount tdaAccount = fetchCashAccount(client);
        OrderRequest orderReq = new OrderRequest(ZonedDateTime.now().minusDays(180), ZonedDateTime.now());
        List<Order> orders = client.fetchOrders(orderReq);
        String stockName = "WORK";
        Predicate<? super Order> filterOrder = order -> (Status.FILLED.equals(order.getStatus()) &&
                order.getOrderLegCollection().stream().anyMatch(leg -> leg.getInstrument().getSymbol().equals(stockName)));

        Optional<Order> optionalOrder = orders.stream().filter(filterOrder).sorted(Comparator.comparing(Order::getEnteredTime).reversed()).findFirst();
        optionalOrder.ifPresent(order -> {
            List<OrderActivity> orderActivityCollection = order.getOrderActivityCollection();
            if (orderActivityCollection.size() != 1) return;
            List<ExecutionLeg> executionLegs = orderActivityCollection.get(0).getExecutionLegs();
            if (executionLegs.size() != 1) return;
            executionLegs.get(0).getPrice();
        });
    }
    public static void main2(String[] args) throws IOException {


        TdaClient client = new HttpTdaClient();
        String stock = "PYPL";


        Order o1 = client.fetchOrder(fetchCashAccount(client).getAccountId(), 2215236405L);
        logger.info(o1.toString());
        final Quote quote = client.fetchQuote(stock);
        EquityQuote equityQuote = (EquityQuote) quote;

        System.out.println("Stock: " + stock);
        System.out.println("TDA");

        System.out.println("Ask price: " + equityQuote.getAskPrice());
        System.out.println("Bid price: " + equityQuote.getBidPrice());
        System.out.println("Close price: " + equityQuote.getClosePrice());
        System.out.println("Open price: " + equityQuote.getOpenPrice());
        System.out.println("Last price: " + equityQuote.getLastPrice());
        Date dateQuote = new Date(equityQuote.getQuoteTimeInLong());
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");;
        System.out.println("Last time quote: " + format.format(dateQuote));
        Date dateTrade = new Date(equityQuote.getTradeTimeInLong());
        System.out.println("Last time trade: " + format.format(dateTrade));

        Stock yahooStock = YahooFinance.get(stock);

        StockQuote yahooQuote = yahooStock.getQuote();
        System.out.println("YAHOO");
        System.out.println("Ask price: " + yahooQuote.getAsk());
        System.out.println("Bid price: " + yahooQuote.getBid());
        System.out.println("Close price: " + yahooQuote.getPreviousClose());
        System.out.println("Open price: " + yahooQuote.getOpen());
        System.out.println("Last price: " + yahooQuote.getPrice());
        Date yahooDateQuote = new Date(yahooQuote.getLastTradeTime().getTimeInMillis());
        System.out.println("Last time trade: " + format.format(yahooDateQuote));
    }


    public static void main(String[] args) {
        String baseOrdersPath = "";
        if (args.length > 0){
            baseOrdersPath = args[0];
        }

        LocalDate tradeOn = isMonday(today) ? today.minusDays(3) : today.minusDays(1);
        LocalDate lastTradeAt = isMonday(tradeOn) ? tradeOn.minusDays(3) : tradeOn.minusDays(1);

        logger.info("Trading on "+ tradeOn + " last trade at "+ lastTradeAt);

        TdaClient client = new HttpTdaClient();
        DailyTrader dailyTrader = new DailyTrader(client, tradeOn, lastTradeAt, baseOrdersPath);

        dailyTrader.tradeGeneratedOrders();
    }

    private static boolean isMonday(LocalDate aDate) {
        return (aDate.dayOfWeek().get() == DateTimeConstants.MONDAY);
    }

    private void tradeGeneratedOrders() {
        addOcaSellLimitsForExecutedOrders();
        buyGeneratedOrders();
    }

    private void buyGeneratedOrders() {
        List<BuyBracketOrder> orders = new BuyBracketOrdersFileHandler(tradeOn, baseOrdersPath).fromJson();
        orders.forEach(bracketOrder -> {

            boolean holdsPosition = tdaAccount.getPositions().stream().anyMatch(position -> position.getInstrument().getSymbol().equals(bracketOrder.getStockName()));
            if (holdsPosition) {
                System.out.println("DO NOT BUY STOCK IN PORTFOLIO : " + bracketOrder);
                return;
            }

        final Quote quoteOri = client.fetchQuote(bracketOrder.getStockName());
//            QuoteAdapter quote = new QuoteAdapter(bracketOrder.getStockName());
        QuoteAdapter quote = new QuoteAdapter(quoteOri);

            try {
                BigDecimal orderQuantity = calculateOrderQuantity(quote);
                Order order = buildBuyTdaOrder(bracketOrder, orderQuantity);
                List<Object> childOrderStrategies = new ArrayList<>();
                order.setChildOrderStrategies(childOrderStrategies);
                buildSellStopLimitChildTdaOrder(bracketOrder,
                        childOrderStrategies,
                        Duration.DAY,
                        orderQuantity,
                        bracketOrder.getSellStopLossPercentage());

                client.placeOrder(accountId, order);

                System.out.println("STOCK BOUGHT: " + bracketOrder + " LAST PRICE " + quote.getLastPrice());
            } catch (TooExpensiveOrder tooExpensiveOrder) {
                System.out.println("STOCK IS TOO EXPENSIVE. AVOID BUYING IT: " + bracketOrder);
            }

        });
    }

    private void addOcaSellLimitsForExecutedOrders() {
        List<BuyBracketOrder> orders = new BuyBracketOrdersFileHandler(lastTradeAt, baseOrdersPath).fromJson();
        orders.forEach(bracketOrder -> {


            Optional<Position> currentPosition = tdaAccount.getPositions().stream().filter(position -> position.getInstrument().getSymbol().equals(bracketOrder.getStockName())).findFirst();

            if (!currentPosition.isPresent() || hasPendingOrders(bracketOrder.getStockName())) return;

            BigDecimal orderQuantity = currentPosition.get().getLongQuantity();

            Order order = new Order();
            order.setOrderStrategyType(OrderStrategyType.OCO);

            List<Object> childOrderStrategies = new ArrayList<>();
            order.setChildOrderStrategies(childOrderStrategies);
            buildSellGetProfitChildTdaOrder(bracketOrder, childOrderStrategies, Duration.GOOD_TILL_CANCEL, orderQuantity);
            buildSellStopLimitChildTdaOrder(bracketOrder, childOrderStrategies, Duration.GOOD_TILL_CANCEL, orderQuantity, bracketOrder.getSellTrailingLossPercentage());

            System.out.println("PLACING OCA: " + bracketOrder.getStockName());
            try {
                client.placeOrder(accountId, order);
                System.out.println("STOCK OCA: " + bracketOrder.getStockName());
            } catch (RuntimeException e) {
                if (e.getMessage().contains("This order may result in an oversold/overbought position in your account.  Please check your position quantity and/or open orders.")) {
                    System.out.println("CANNOT PLACE OCA FOR " + bracketOrder.getStockName() + " OVERSOLD");
                } else {
                    throw e;
                }
            }

        });
    }

    private boolean hasPendingOrders(String stockName) {
        OrderRequest orderReq = new OrderRequest(ZonedDateTime.now().minusDays(180), ZonedDateTime.now());
        List<Order> orders = client.fetchOrders(orderReq);
        List<Order> pendingOrders = orders.stream().filter(order -> {
            if (Status.WORKING.equals(order.getStatus()) && order.getOrderLegCollection().stream().anyMatch(leg -> leg.getInstrument().getSymbol().equals(stockName))) return true;

            if (order.getOrderStrategyType().equals(OrderStrategyType.OCO)) {
                return order.getChildOrderStrategies().stream().anyMatch(child -> {
                    List<HashMap> legs = (List<HashMap>) ((HashMap)child).get("orderLegCollection");
                    return ((HashMap)child).get("status").equals("WORKING") && legs.stream().anyMatch(leg -> {
                        HashMap instrument = (HashMap) leg.get("instrument");
                        return instrument.get("symbol").equals(stockName);
                    });
                });
            }
            return false;
        }).collect(Collectors.toList());
        return !pendingOrders.isEmpty();
    }

    private void buildSellGetProfitChildTdaOrder(BuyBracketOrder bracketOrder, List<Object> childOrderStrategies, Duration orderDuration, BigDecimal orderQuantity) {
        Order childOrder = new Order();
        childOrder.setSession(Session.NORMAL);
        childOrder.setOrderStrategyType(OrderStrategyType.SINGLE);

        childOrder.setDuration(orderDuration);
        childOrder.setOrderType(OrderType.LIMIT);
        childOrder.setPrice(fetchSellTargetPrice(bracketOrder));

        OrderLegCollection childOlc = new OrderLegCollection();
        childOlc.setInstruction(OrderLegCollection.Instruction.SELL);
        childOlc.setQuantity(orderQuantity);
        childOrder.getOrderLegCollection().add(childOlc);

        Instrument instrument = new EquityInstrument();
        instrument.setSymbol(bracketOrder.getStockName());
        childOlc.setInstrument(instrument);

        childOrderStrategies.add(childOrder);
    }

    private void buildSellStopLimitChildTdaOrder(BuyBracketOrder bracketOrder, List<Object> childOrderStrategies, Duration orderDuration, BigDecimal orderQuantity, BigDecimal sellStopLossPercentage) {
        Order childOrder = new Order();
        childOrder.setSession(Session.NORMAL);
        childOrder.setOrderStrategyType(OrderStrategyType.SINGLE);

        childOrder.setDuration(orderDuration);
        childOrder.setOrderType(OrderType.TRAILING_STOP);
        childOrder.setStopPriceLinkBasis(StopPriceLinkBasis.MARK);
        childOrder.setStopPriceLinkType(StopPriceLinkType.PERCENT);
        childOrder.setStopPriceOffset(sellStopLossPercentage);

        OrderLegCollection childOlc = new OrderLegCollection();
        childOlc.setInstruction(OrderLegCollection.Instruction.SELL);
        childOlc.setQuantity(orderQuantity);
        childOrder.getOrderLegCollection().add(childOlc);

        Instrument instrument = new EquityInstrument();
        instrument.setSymbol(bracketOrder.getStockName());
        childOlc.setInstrument(instrument);

        childOrderStrategies.add(childOrder);
    }

    public Order buildBuyTdaOrder(BuyBracketOrder bracketOrder, BigDecimal quantity) throws TooExpensiveOrder {
        Order order = new Order();
        order.setOrderType(OrderType.MARKET);
        order.setSession(Session.NORMAL);
        order.setDuration(Duration.DAY);
        order.setOrderStrategyType(OrderStrategyType.TRIGGER);

        OrderLegCollection olc = new OrderLegCollection();
        olc.setInstruction(OrderLegCollection.Instruction.BUY);
        olc.setQuantity(quantity);
        order.getOrderLegCollection().add(olc);

        Instrument instrument = new EquityInstrument();
        instrument.setSymbol(bracketOrder.getStockName());
        olc.setInstrument(instrument);
        return order;
    }

    private BigDecimal calculateOrderQuantity(QuoteAdapter quote) throws TooExpensiveOrder {
        BigDecimal oneHundred = new BigDecimal(100.0);
        BigDecimal twoHundred = new BigDecimal(200.0);
        BigDecimal threeHundred = new BigDecimal(300.0);
        BigDecimal oneThousand = new BigDecimal(1000.0);
        BigDecimal lastPrice = quote.getLastPrice();
        if (lastPrice.compareTo(oneHundred) <= 0) {
            return new BigDecimal("5.0");
        } else if (lastPrice.compareTo(twoHundred) <= 0) {
            return new BigDecimal("3.0");
        } else if (lastPrice.compareTo(threeHundred) <= 0) {
            return new BigDecimal("2.0");
        } else if (lastPrice.compareTo(oneThousand) <= 0) {
            return new BigDecimal("1.0");
        }
        throw new TooExpensiveOrder("The stock is too expensive");
    }

    public Order buildSellTdaOrder(BuyBracketOrder bracketOrder, BigDecimal orderQuantity) {
        Order order = new Order();
        order.setOrderType(OrderType.MARKET);
        order.setSession(Session.NORMAL);
        order.setDuration(Duration.DAY);
        order.setOrderStrategyType(OrderStrategyType.SINGLE);

        OrderLegCollection olc = new OrderLegCollection();
        olc.setInstruction(OrderLegCollection.Instruction.SELL);
        olc.setQuantity(orderQuantity);
        order.getOrderLegCollection().add(olc);

        Instrument instrument = new EquityInstrument();
        instrument.setSymbol(bracketOrder.getStockName());
        olc.setInstrument(instrument);
        return order;
    }

    public void generateDailyBestBuyOrdersFile(Map<String, List<BuyOrderWithPendingSellOrders>> buyOrders) {
        writeToFile(buildBestBuyOrders(buyOrders));

    }
    public List<BuyBracketOrder> buildBestBuyOrders(Map<String, List<BuyOrderWithPendingSellOrders>> buyOrders) {
        List<BuyBracketOrder> ordersToTrade = new ArrayList<>();
        FinnhubClient finnhubClient = new FinnhubClient();
        buyOrders.entrySet().stream().forEach(entry -> {
            Optional<BuyOrderWithPendingSellOrders> bestOrder = bestSellOrder(entry.getValue());
            bestOrder.ifPresent(order -> ordersToTrade.add(new BuyBracketOrder(entry.getKey(),
                    order, orderExpectedReturn(order), orderMaxLoss(order), order.getCandidate(),
                    finnhubClient.newsSentimentFor(entry.getKey()),
                    finnhubClient.socialSentimentFor(entry.getKey()),
                    finnhubClient.supportResistanceFor(entry.getKey()),
                    finnhubClient.aggregateIndicator(entry.getKey())
            )));
        });
        return ordersToTrade;
    }

    public Optional<BuyOrderWithPendingSellOrders> bestSellOrder(List<BuyOrderWithPendingSellOrders> sellOrders) {
        int count = sellOrders.size();
        return sellOrders.stream().
                sorted(ordersComparator()).skip(count - 1).findFirst();
    }

    private Comparator<BuyOrderWithPendingSellOrders> ordersComparator() {
        return (orderOne, orderTwo) -> {
            int morePercentageWon = orderOne.getStockGroup().getPercentageOfOrdersWon().compareTo(orderTwo.getStockGroup().getPercentageOfOrdersWon());
            if (morePercentageWon != 0) return morePercentageWon;
            int moreReturn = orderExpectedReturn(orderOne).compareTo(orderExpectedReturn(orderTwo));
            if (moreReturn != 0) return moreReturn;
            int moreOrdersWon = Integer.valueOf(orderOne.getStockGroup().getOrdersWon()).compareTo(orderTwo.getStockGroup().getOrdersWon());
            return moreOrdersWon;
        };
    }

    private BigDecimal orderExpectedReturn(BuyOrderWithPendingSellOrders order) {
        return orderPercentage(order.getSellTargetPrice(), order.getMarketOrder().getPrice());
    }

    private BigDecimal orderMaxLoss(BuyOrderWithPendingSellOrders order) {
        return orderPercentage(order.getMarketOrder().getPrice(), order.getStopLossPrice());
    }

    private BigDecimal orderPercentage(BigDecimal dividend, BigDecimal divisor) {
        MathContext mc3 = new MathContext(6, RoundingMode.HALF_EVEN);
        BigDecimal orderMaxLoss = dividend.divide(divisor, mc3).subtract(BigDecimal.ONE);
        return orderMaxLoss.multiply(new BigDecimal(100.0));
    }

    private void writeToFile(List<BuyBracketOrder> orders) {
        new BuyBracketOrdersFileHandler(tradeOn).toJson(orders);
    }

    private static CashAccount fetchCashAccount(TdaClient client) {
        List<SecuritiesAccount> accounts = client.getAccounts(true, true);
        if (accounts.size() > 1) {
            throw new RuntimeException("I actually have multiple accounts...");
        }

        CashAccount myAccount = (CashAccount) accounts.get(0);
        return myAccount;
    }

    private boolean isLastPriceAboveSellTarget(QuoteAdapter quote, BuyBracketOrder order) {
        return quote.getLastPrice().compareTo(fetchSellTargetPrice(order)) > 0;
    }

    private boolean isLastPriceBelowResistance(QuoteAdapter quote, BuyBracketOrder order) {
        return quote.getLastPrice().compareTo(order.getSellResistancePrice()) < 0;
    }

    private boolean isOpenPriceBelowLastClose(QuoteAdapter quote) {
        return quote.getOpenPrice().compareTo(quote.getLastClosePrice()) < 0;
    }

    private BigDecimal fetchSellTargetPrice(BuyBracketOrder bracketOrder) {
        OrderRequest orderReq = new OrderRequest(ZonedDateTime.now().minusDays(180), ZonedDateTime.now());
        List<Order> orders = client.fetchOrders(orderReq);

        Predicate<? super Order> filterOrder = order -> (Status.FILLED.equals(order.getStatus()) &&
                order.getOrderLegCollection().stream().anyMatch(leg -> leg.getInstrument().getSymbol().equals(bracketOrder.getStockName())));

        Optional<Order> optionalOrder = orders.stream().filter(filterOrder).sorted(Comparator.comparing(Order::getEnteredTime).reversed()).findFirst();
        if (!optionalOrder.isPresent()) return bracketOrder.getSellTargetPrice();

        Order order = optionalOrder.get();
        List<OrderActivity> orderActivityCollection = order.getOrderActivityCollection();
        if (orderActivityCollection.size() != 1) return bracketOrder.getSellTargetPrice();
        List<ExecutionLeg> executionLegs = orderActivityCollection.get(0).getExecutionLegs();
        if (executionLegs.size() != 1) return bracketOrder.getSellTargetPrice();

        BigDecimal multiplier = bracketOrder.getExpectedReturn().divide(BigDecimal.valueOf(100)).add(BigDecimal.ONE);
        return executionLegs.get(0).getPrice().multiply(multiplier).setScale(2, RoundingMode.HALF_EVEN);
    }

}
