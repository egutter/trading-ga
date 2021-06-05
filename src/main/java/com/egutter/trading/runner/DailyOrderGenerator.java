package com.egutter.trading.runner;

import com.egutter.trading.finnhub.FinnhubClient;
import com.egutter.trading.order.BuyBracketOrder;
import com.egutter.trading.order.BuyOrderWithPendingSellOrders;
import com.egutter.trading.out.BuyBracketOrdersFileHandler;
import com.studerw.tda.client.HttpTdaClient;
import com.studerw.tda.client.TdaClient;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.util.*;
import java.util.function.Predicate;

public class DailyOrderGenerator {

    private final static Logger logger = LoggerFactory.getLogger(DailyOrderGenerator.class);

    public static final double MINIMUM_PROFIT_EXPECTED = 1.00;
    private LocalDate tradeOn;
    private String baseOrdersPath;

    private final static LocalDate today = LocalDate.now();
    private final static LocalDate yesterday = (today.dayOfWeek().get() == DateTimeConstants.MONDAY) ? today.minusDays(3) : today.minusDays(1);


    public DailyOrderGenerator(TdaClient client, LocalDate tradeOn, String baseOrdersPath) {
        this.tradeOn = tradeOn;
        this.baseOrdersPath = baseOrdersPath;
    }

    public static void main(String[] args) {
        String baseOrdersPath = "";
        if (args.length > 0){
            baseOrdersPath = args[0];
        }
        LocalTime startTime = LocalTime.now();
        LocalDate tradeOn = today;
//        LocalDate tradeOn = new LocalDate(2021, 6, 1);

        TdaClient client = new HttpTdaClient();
        DailyOrderGenerator dailyOrderGenerator = new DailyOrderGenerator(client, tradeOn, baseOrdersPath);

        dailyOrderGenerator.runTraderAndGenerateOrders();
        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    public void runTraderAndGenerateOrders() {
        LocalDate fromDate = new LocalDate(2020, 1, 1);
        OneDayCandidateRunner runner = new OneDayCandidateRunner(fromDate, tradeOn);
        logger.info("Running candidates on trade date [" + tradeOn + "]");
        Map<String, List<BuyOrderWithPendingSellOrders>> buyOrders = runner.run(tradeOn);
        generateDailyBestBuyOrdersFile(buyOrders);
        logger.info(runner.runOutput("\n"));
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
            int moreOrdersWon = Integer.valueOf(orderOne.getStockGroup().getOrdersWon()).compareTo(orderTwo.getStockGroup().getOrdersWon());
            if (moreOrdersWon != 0) return moreOrdersWon;
            int moreReturn = orderExpectedReturn(orderOne).compareTo(orderExpectedReturn(orderTwo));
            return moreReturn;
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
        BigDecimal orderReturn = dividend.divide(divisor, mc3).subtract(BigDecimal.ONE);
        return orderReturn.multiply(new BigDecimal(100.0));
    }

    private void writeToFile(List<BuyBracketOrder> orders) {
        new BuyBracketOrdersFileHandler(tradeOn, baseOrdersPath).toJson(orders);
    }

}
