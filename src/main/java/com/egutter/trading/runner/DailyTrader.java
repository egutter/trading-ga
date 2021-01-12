package com.egutter.trading.runner;

import com.egutter.trading.order.BuyBracketOrder;
import com.egutter.trading.order.BuyOrderWithPendingSellOrders;
import com.egutter.trading.out.BuyBracketOrdersFileHandler;
import com.studerw.tda.client.HttpTdaClient;
import com.studerw.tda.client.TdaClient;
import com.studerw.tda.model.account.*;
import com.studerw.tda.model.quote.EquityQuote;
import com.studerw.tda.model.quote.Quote;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Predicate;

public class DailyTrader {

    public static final double MINIMUM_PROFIT_EXPECTED = 1.00;
    private String accountId;
    private TdaClient client;
    private BigDecimal cashAvailable;
    private CashAccount tdaAccount;
    private LocalDate tradeOn = LocalDate.now();
    private LocalDate lastTradeAt;
    private final static String GENERATE_TYPE = "GENERATE";
    private final static String TRADE_TYPE = "TRADE";

    public DailyTrader(TdaClient client, LocalDate tradeOn, LocalDate lastTradeAt) {
        this.client = client;
        this.tradeOn = tradeOn;
        this.lastTradeAt = lastTradeAt;
        CashAccount tdaAccount = fetchCashAccount(client);
        this.cashAvailable = tdaAccount.getCurrentBalances().getCashAvailableForTrading();
        this.tdaAccount = tdaAccount;
        this.accountId = tdaAccount.getAccountId();
    }

    public DailyTrader(BigDecimal cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    public static void main2(String[] args) {
        TdaClient client = new HttpTdaClient();
        final Quote quote = client.fetchQuote("AVGO");
        EquityQuote equityQuote = (EquityQuote) quote;

        System.out.println("Ask price of AVGO: " + equityQuote.getAskPrice());
        System.out.println("Bid price of AVGO: " + equityQuote.getBidPrice());
        System.out.println("Close price of AVGO: " + equityQuote.getClosePrice());
        System.out.println("Open price of AVGO: " + equityQuote.getOpenPrice());
        System.out.println("Last price of AVGO: " + equityQuote.getLastPrice());

    }


    public static void main(String[] args) {
        String generateOrTrade = TRADE_TYPE;
        LocalDate tradeOn = new LocalDate(2021, 1, 11);
        LocalDate lastTradeAt = new LocalDate(2021, 1, 8);

        TdaClient client = new HttpTdaClient();
        DailyTrader dailyTrader = new DailyTrader(client, tradeOn, lastTradeAt);

        if (generateOrTrade.equals(GENERATE_TYPE)) {
            dailyTrader.runTraderAndGenerateOrders();
        } else {
            dailyTrader.tradeGeneratedOrders();
        }
    }

    private void tradeGeneratedOrders() {
        addOcaSellLimitsForExecutedOrders();
//        buyGeneratedOrders();
    }

    private void buyGeneratedOrders() {
        List<BuyBracketOrder> orders = new BuyBracketOrdersFileHandler(tradeOn).fromJson();
        orders.forEach(bracketOrder -> {

            Order order = buildBuyTdaOrder(bracketOrder);

            List<Object> childOrderStrategies = new ArrayList<>();
            order.setChildOrderStrategies(childOrderStrategies);
            buildSellStopLimitChildTdaOrder(bracketOrder, childOrderStrategies, Duration.DAY);

            client.placeOrder(accountId, order);

            System.out.println("STOCK BOUGHT: " + bracketOrder.getStockName());
        });
    }

    private void addOcaSellLimitsForExecutedOrders() {
        List<BuyBracketOrder> orders = new BuyBracketOrdersFileHandler(lastTradeAt).fromJson();
        orders.forEach(bracketOrder -> {

            Order order = new Order();
            order.setOrderStrategyType(OrderStrategyType.OCO);

            final Quote quote = client.fetchQuote(bracketOrder.getStockName());
            EquityQuote equityQuote = (EquityQuote) quote;
            equityQuote.getLastPrice();

            List<Object> childOrderStrategies = new ArrayList<>();
            order.setChildOrderStrategies(childOrderStrategies);

            buildSellGetProfitChildTdaOrder(bracketOrder, childOrderStrategies, Duration.GOOD_TILL_CANCEL);
            buildSellStopLimitChildTdaOrder(bracketOrder, childOrderStrategies, Duration.GOOD_TILL_CANCEL);

            client.placeOrder(accountId, order);

            System.out.println("STOCK OCA: " + bracketOrder.getStockName());
        });
    }

    private void buildSellGetProfitChildTdaOrder(BuyBracketOrder bracketOrder, List<Object> childOrderStrategies, Duration orderDuration) {
        Order childOrder = new Order();
        childOrder.setOrderType(OrderType.LIMIT);
        childOrder.setSession(Session.NORMAL);
        childOrder.setDuration(orderDuration);
        childOrder.setOrderStrategyType(OrderStrategyType.SINGLE);
        childOrder.setPrice(bracketOrder.getSellTargetPrice());

        OrderLegCollection childOlc = new OrderLegCollection();
        childOlc.setInstruction(OrderLegCollection.Instruction.SELL);
        childOlc.setQuantity(new BigDecimal("1.0"));
        childOrder.getOrderLegCollection().add(childOlc);

        Instrument instrument = new EquityInstrument();
        instrument.setSymbol(bracketOrder.getStockName());
        childOlc.setInstrument(instrument);

        childOrderStrategies.add(childOrder);
    }

    private void buildSellStopLimitChildTdaOrder(BuyBracketOrder bracketOrder, List<Object> childOrderStrategies, Duration orderDuration) {
        Order childOrder = new Order();
        childOrder.setOrderType(OrderType.STOP);
        childOrder.setSession(Session.NORMAL);
        childOrder.setDuration(orderDuration);
        childOrder.setOrderStrategyType(OrderStrategyType.SINGLE);
        childOrder.setStopPrice(bracketOrder.getSellResistancePrice());

        OrderLegCollection childOlc = new OrderLegCollection();
        childOlc.setInstruction(OrderLegCollection.Instruction.SELL);
        childOlc.setQuantity(new BigDecimal("1.0"));
        childOrder.getOrderLegCollection().add(childOlc);

        Instrument instrument = new EquityInstrument();
        instrument.setSymbol(bracketOrder.getStockName());
        childOlc.setInstrument(instrument);

        childOrderStrategies.add(childOrder);
    }

    public Order buildBuyTdaOrder(BuyBracketOrder bracketOrder) {
        Order order = new Order();
        order.setOrderType(OrderType.MARKET);
        order.setSession(Session.NORMAL);
        order.setDuration(Duration.DAY);
        order.setOrderStrategyType(OrderStrategyType.TRIGGER);

        OrderLegCollection olc = new OrderLegCollection();
        olc.setInstruction(OrderLegCollection.Instruction.BUY);
        olc.setQuantity(new BigDecimal("1.0"));
        order.getOrderLegCollection().add(olc);

        Instrument instrument = new EquityInstrument();
        instrument.setSymbol(bracketOrder.getStockName());
        olc.setInstrument(instrument);
        return order;
    }


    public void runTraderAndGenerateOrders() {
        LocalDate fromDate = new LocalDate(2020, 1, 1);
        OneDayCandidateRunner runner = new OneDayCandidateRunner(fromDate, tradeOn);
        Map<String, List<BuyOrderWithPendingSellOrders>> buyOrders = runner.run(tradeOn);
        generateDailyBestBuyOrdersFile(buyOrders);
        System.out.println(runner.runOutput("\n"));
    }

    public void generateDailyBestBuyOrdersFile(Map<String, List<BuyOrderWithPendingSellOrders>> buyOrders) {
        writeToFile(buildBestBuyOrders(buyOrders));

    }
    public List<BuyBracketOrder> buildBestBuyOrders(Map<String, List<BuyOrderWithPendingSellOrders>> buyOrders) {
        List<BuyBracketOrder> ordersToTrade = new ArrayList<>();
        buyOrders.entrySet().stream().forEach(entry -> {
            Optional<BuyOrderWithPendingSellOrders> bestOrder = bestSellOrder(entry.getValue());
            bestOrder.ifPresent(order -> ordersToTrade.add(new BuyBracketOrder(entry.getKey(),
                    order, orderExpectedReturn(order), orderMaxLoss(order))));
        });
        return ordersToTrade;
    }

    public Optional<BuyOrderWithPendingSellOrders> bestSellOrder(List<BuyOrderWithPendingSellOrders> sellOrders) {
        int count = sellOrders.size();
        return sellOrders.stream().filter(ordersFilter()).
                sorted(ordersComparator()).skip(count - 1).findFirst();
    }

    private Predicate<BuyOrderWithPendingSellOrders> ordersFilter() {
        return (order) -> orderExpectedReturn(order).compareTo(BigDecimal.valueOf(MINIMUM_PROFIT_EXPECTED)) > 0;
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
        return orderPercentage(order.getMarketOrder().getPrice(), order.getSellResistancePrice());
    }

    private BigDecimal orderPercentage(BigDecimal dividend, BigDecimal divisor) {
        MathContext mc3 = new MathContext(6, RoundingMode.HALF_EVEN);
        BigDecimal orderMaxLoss = dividend.divide(divisor, mc3).multiply(new BigDecimal(100.0));
        return orderMaxLoss.subtract(BigDecimal.ONE);
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
}
