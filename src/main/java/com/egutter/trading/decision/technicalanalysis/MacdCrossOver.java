package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.*;
import com.google.common.base.Joiner;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.function.Function;

public class MacdCrossOver implements BuyTradingDecision, SellTradingDecision, Function<TimeFrameQuote, Boolean> {

    private final StockPrices stockPrices;
    private final int fastMovingAverageDays;
    private final int slowMovingAverageDays;
    private int signalDays;
    private LocalDate startOnDate;
    private Map<LocalDate, Double> macdValues = new HashMap<LocalDate, Double>();;
    private Map<LocalDate, Double> signalValues = new HashMap<LocalDate, Double>();;

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2020, 1, 01);
        StockMarket market = new StockMarketBuilder().buildInMemory(fromDate);
        MacdCrossOver movingAverage = new MacdCrossOver(market.getStockPricesFor("AAPL"),
                12, 26, 9);
        market.getStockPrices().get(0).getDailyQuotes().forEach(quote -> {
            LocalDate tradingDate = quote.getTradingDate();
            movingAverage.printValues(tradingDate);
            DecisionResult buyResult = movingAverage.shouldBuyOn(quote.getTradingDate());
            if (buyResult.equals(DecisionResult.YES)) System.out.println(quote.getTradingDate() + " = BUY");
            DecisionResult sellResult = movingAverage.shouldSellOn(quote.getTradingDate());
            if (sellResult.equals(DecisionResult.YES)) System.out.println(quote.getTradingDate() + " = SELL");
        });
    }

    private void printValues(LocalDate tradingDate) {
        System.out.println("Date = " + tradingDate + " MACD " + getIndexAtDate(macdValues, tradingDate) + " SIGNAL " + getIndexAtDate(signalValues, tradingDate));
//        System.out.println(macdValues);
//        System.out.println("MACD");
//        System.out.println("SIGNAL");
//        System.out.println(signalValues);
//        System.out.println("Start on date "+ this.startOnDate);
    }

    public MacdCrossOver(StockPrices stockPrices,
                         int fastMovingAverageDays,
                         int slowMovingAverageDays,
                         int signalDays) {
        this.stockPrices = stockPrices;
        this.fastMovingAverageDays = fastMovingAverageDays;
        this.slowMovingAverageDays = slowMovingAverageDays;
        this.signalDays = signalDays;
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
        Optional<Double> yesterdaySignal = getIndexAtDate(signalValues, yesterday);
        if (!yesterdaySignal.isPresent()) {
            return DecisionResult.NEUTRAL;
        }

        Optional<Double> todaySignal = getIndexAtDate(signalValues, tradingDate);
        Optional<Double> yesterdayMacd = getIndexAtDate(macdValues, yesterday);
        Optional<Double> todayMacd = getIndexAtDate(macdValues, tradingDate);

        boolean yesterdayDiff = calcYesterdayDiff.apply(yesterdayMacd.get() - yesterdaySignal.get());
        boolean todayDiff = calcTodayDiff.apply(todayMacd.get() - todaySignal.get());
        return (yesterdayDiff && todayDiff) ? DecisionResult.YES : DecisionResult.NO;
    }

    public Optional<Double> getIndexAtDate(Map<LocalDate, Double> movingAverage, LocalDate tradingDate) {
        return Optional.ofNullable(movingAverage.get(tradingDate));
    }

    @Override
    public LocalDate startOn() {
        return this.startOnDate;
    }

    private void calculateMovingAverages() {
        List<Double>closePrices = stockPrices.getClosePrices();
//        System.out.println("stock prices");
//        System.out.println(stockPrices.getDailyQuotes());

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outMacd[] = new double[closePrices.size()];
        double outMACDSignal[] = new double[closePrices.size()];
        double outMACDHist[] = new double[closePrices.size()];
        double[] closePricesArray = Doubles.toArray(closePrices);

        CoreAnnotated coreAnnotated = new CoreAnnotated();
        RetCode returnCode = coreAnnotated.macd(startIndex(),
                endIndex(closePrices),
                closePricesArray,
                this.fastMovingAverageDays,
                this.slowMovingAverageDays,
                this.signalDays,
                outBegIdx,
                outNBElement,
                outMacd,
                outMACDSignal,
                outMACDHist);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating MACD " + returnCode + " with decision: " + this.sellDecisionToString() + " " + this.buyDecisionToString() );
        }
//        System.out.println("averages \n");
//        for (double num : outMacd) {
//            System.out.println(num);
//        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();
        int lookBack = coreAnnotated.macdLookback(this.fastMovingAverageDays, this.slowMovingAverageDays, this.signalDays);
        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
        for (int i = 0; i < outNBElement.value; i++) {
            int movingAverageDaysOffset = i + lookBack;
            LocalDate tradingDate = tradingDates.get(movingAverageDaysOffset);
            this.macdValues.put(tradingDate, outMacd[i]);
            this.signalValues.put(tradingDate, outMACDSignal[i]);
        }
    }

    private int endIndex(List<Double> closePrices) {
        return closePrices.size() - 1;
    }

    private int startIndex() {
        return 0;
    }

    @Override
    public String buyDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "Fast days",
                this.fastMovingAverageDays,
                "Slow days",
                this.slowMovingAverageDays,
                "Signal",
                this.signalDays);
    }

    @Override
    public String sellDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "Fast days",
                this.fastMovingAverageDays,
                "Slow days",
                this.slowMovingAverageDays,
                "Signal days",
                this.signalDays);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MacdCrossOver.class.getSimpleName() + "[", "]")
                .add("stockPrices=" + stockPrices)
                .add("fastMovingAverageDays=" + fastMovingAverageDays)
                .add("slowMovingAverageDays=" + slowMovingAverageDays)
                .add("signalDays=" + signalDays)
                .add("startOnDate=" + startOnDate)
                .add("macdValues=" + macdValues)
                .add("signalValues=" + signalValues)
                .toString();
    }
}
