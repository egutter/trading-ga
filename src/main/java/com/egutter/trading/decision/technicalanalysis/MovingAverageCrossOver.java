package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.*;
import com.google.common.base.Joiner;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.function.Function;

public class MovingAverageCrossOver implements BuyTradingDecision, SellTradingDecision, Function<TimeFrameQuote, Boolean> {

    private final StockPrices stockPrices;
    private final int fastMovingAverageDays;
    private final int slowMovingAverageDays;
    private final MAType fastMovingAverageType;
    private final MAType slowMovingAverageType;
    private LocalDate startOnDate;
    private Map<LocalDate, Double> fastMovingAverage = new HashMap<LocalDate, Double>();;
    private Map<LocalDate, Double> slowMovingAverage = new HashMap<LocalDate, Double>();;

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2020, 1, 01);
        StockMarket market = new StockMarketBuilder().buildInMemory(fromDate);
        MovingAverageCrossOver movingAverage = new MovingAverageCrossOver(market.getStockPricesFor("AAPL"), 9, 50, MAType.Sma, MAType.Sma);
//        movingAverage.printValues();
        market.getStockPrices().get(0).getDailyQuotes().forEach(quote -> {
            DecisionResult resultBuy = movingAverage.shouldBuyOn(quote.getTradingDate());
            DecisionResult resultSell = movingAverage.shouldSellOn(quote.getTradingDate());
            if (resultBuy.equals(DecisionResult.YES)) System.out.println(quote.getTradingDate() + " = BUY " + resultBuy);
            if (resultSell.equals(DecisionResult.YES)) System.out.println(quote.getTradingDate() + " = SELL " + resultSell);
        });
    }

    private void printValues() {
        System.out.println("FAST MOVING AVERAGE");
        System.out.println(fastMovingAverage);
        System.out.println("SLOW MOVING AVERAGE");
        System.out.println(slowMovingAverage);
        System.out.println("Start on date "+ this.startOnDate);
    }

    public MovingAverageCrossOver(StockPrices stockPrices,
                                  int fastMovingAverageDays,
                                  int slowMovingAverageDays,
                                  MAType fastMovingAverageType,
                                  MAType slowMovingAverageType) {
        this.stockPrices = stockPrices;
        this.fastMovingAverageDays = fastMovingAverageDays;
        this.slowMovingAverageDays = slowMovingAverageDays;
        this.fastMovingAverageType = fastMovingAverageType;
        this.slowMovingAverageType = slowMovingAverageType;
        this.startOnDate = stockPrices.getFirstTradingDate();
        calculateMovingAverages();
    }


    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        return shouldBuyOn(timeFrameQuote.getQuoteAtDay().getTradingDate()).equals(DecisionResult.YES);
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        // Fast moving average cross up slow moving average
        return shouldTradeOn(tradingDate, (yesterdayDiff) -> yesterdayDiff < 0, (todayDiff) -> todayDiff > 0);
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        // Slow moving average cross up fast moving average
        return shouldTradeOn(tradingDate, (yesterdayDiff) -> yesterdayDiff > 0, (todayDiff) -> todayDiff < 0);
    }

    private DecisionResult shouldTradeOn(LocalDate tradingDate,
                                         Function<Double, Boolean> calcYesterdayDiff,
                                         Function<Double, Boolean> calcTodayDiff) {
        Optional<DailyQuote> previousQuote = this.stockPrices.dailyPriceBefore(tradingDate, 1);
        if (!previousQuote.isPresent()) {
            return DecisionResult.NEUTRAL;
        }
        LocalDate yesterday = previousQuote.get().getTradingDate();
        Optional<Double> yesterdaySlowMA = getIndexAtDate(slowMovingAverage, yesterday);
        if (!yesterdaySlowMA.isPresent()) {
            return DecisionResult.NEUTRAL;
        }

        Optional<Double> todaySlowMA = getIndexAtDate(slowMovingAverage, tradingDate);
        Optional<Double> yesterdayFastMA = getIndexAtDate(fastMovingAverage, yesterday);
        Optional<Double> todayFastMA = getIndexAtDate(fastMovingAverage, tradingDate);

        boolean yesterdayDiff = calcYesterdayDiff.apply(yesterdayFastMA.get() - yesterdaySlowMA.get());
        boolean todayDiff = calcTodayDiff.apply(todayFastMA.get() - todaySlowMA.get());
        return (yesterdayDiff && todayDiff) ? DecisionResult.YES : DecisionResult.NEUTRAL;
    }

    public Optional<Double> getIndexAtDate(Map<LocalDate, Double> movingAverage, LocalDate tradingDate) {
        return Optional.ofNullable(movingAverage.get(tradingDate));
    }

    @Override
    public String buyDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "Fast MA days",
                this.fastMovingAverageDays,
                "Fast MA Type",
                this.fastMovingAverageType,
                "Slow MA days",
                this.slowMovingAverageDays,
                "Slow MA Type",
                this.slowMovingAverageType);
    }

    @Override
    public LocalDate startOn() {
        return this.startOnDate;
    }

    private void calculateMovingAverages() {
        List<Double>closePrices = stockPrices.getClosePrices();
//        System.out.println("stock prices");
//        System.out.println(stockPrices.getDailyQuotes());
        calculateOneMovingAverage(closePrices, this.fastMovingAverageDays, this.fastMovingAverageType, this.fastMovingAverage);
        calculateOneMovingAverage(closePrices, this.slowMovingAverageDays, this.slowMovingAverageType, this.slowMovingAverage);
    }

    private void calculateOneMovingAverage(List<Double> closePrices, int movingAverageDays, MAType movingAverageType, Map<LocalDate, Double> movingAverage) {
        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outRealFastMovingAverage[] = new double[closePrices.size()];
        double[] closePricesArray = Doubles.toArray(closePrices);

        RetCode returnCode = new CoreAnnotated().movingAverage(startIndex(),
                endIndex(closePrices),
                closePricesArray,
                movingAverageDays,
                movingAverageType,
                outBegIdx,
                outNBElement,
                outRealFastMovingAverage);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating Moving Average " + returnCode + " with decision: " + this.sellDecisionToString() + " " + this.buyDecisionToString() );
        }
//        System.out.println("averages \n");
//        for (double num : outRealFastMovingAverage) {
//            System.out.println(num);
//        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();
        int lookBack = new CoreAnnotated().movingAverageLookback(movingAverageDays, movingAverageType);
        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
        for (int i = 0; i < outNBElement.value; i++) {
            int movingAverageDaysOffset = i + lookBack;
            LocalDate tradingDate = tradingDates.get(movingAverageDaysOffset);
            movingAverage.put(tradingDate, outRealFastMovingAverage[i]);
        }
    }

    private int endIndex(List<Double> closePrices) {
        return closePrices.size() - 1;
    }

    private int startIndex() {
        return 0;
    }

    @Override
    public String sellDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "Fast MA days",
                this.fastMovingAverageDays,
                "Fast MA Type",
                this.fastMovingAverageType,
                "Slow MA days",
                this.slowMovingAverageDays,
                "Slow MA Type",
                this.slowMovingAverageType);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MovingAverageCrossOver.class.getSimpleName() + "[", "]")
                .add("stockPrices=" + stockPrices)
                .add("fastMovingAverageDays=" + fastMovingAverageDays)
                .add("slowMovingAverageDays=" + slowMovingAverageDays)
                .add("fastMovingAverageType=" + fastMovingAverageType)
                .add("slowMovingAverageType=" + slowMovingAverageType)
                .add("startOnDate=" + startOnDate)
                .add("fastMovingAverage=" + fastMovingAverage)
                .add("slowMovingAverage=" + slowMovingAverage)
                .toString();
    }
}
