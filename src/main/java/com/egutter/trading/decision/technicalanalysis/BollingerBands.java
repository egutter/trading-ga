package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import com.google.common.base.Joiner;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.function.Function;

/**
 * Created by egutter on 2/10/14.
 */
public class BollingerBands implements BuyTradingDecision, SellTradingDecision, TechnicalAnalysisIndicator, Function<TimeFrameQuote, Boolean> {

    private final StockPrices stockPrices;
    private final Range sellThreshold;
    private Map<LocalDate, Double> percentageB;

    private Range buyThreshold;
    private int movingAverageDays;
    private MAType movingAverageType;
    private LocalDate startOnDate = LocalDate.now();

    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarketBuilder().build(new LocalDate(2014, 1, 1), LocalDate.now());
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
        System.out.println(stockPrices.getAdjustedClosePrices());
            BollingerBands bb = new BollingerBands(stockPrices, Range.atLeast(1.0), Range.atMost(0.0), 22, MAType.Ema);
            Map<LocalDate, Double> indexes = bb.getPercentageB();
            maxes.add(Ordering.natural().max(indexes.values()));
            minis.add(Ordering.natural().min(indexes.values()));
        }
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
    }

    public BollingerBands(StockPrices stockPrices,
                          Range buyThreshold,
                          Range sellThreshold,
                          int movingAverageDays,
                          MAType movingAverageType) {
        this.stockPrices = stockPrices;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
        this.movingAverageDays = movingAverageDays;
        this.movingAverageType = movingAverageType;
    }

    public static BollingerBands empty(StockPrices stockPrices) {
        return new BollingerBands(stockPrices, Range.atLeast(1.0), Range.atMost(1.0), 20, MAType.Sma);
    }

    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        return this.shouldBuyOn(timeFrameQuote.getQuoteAtDay().getTradingDate()).equals(DecisionResult.YES);
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        return shouldTradeOn(tradingDate, buyThreshold);
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        return shouldTradeOn(tradingDate, sellThreshold);
    }

    private DecisionResult shouldTradeOn(LocalDate tradingDate, Range tradeThreshold) {
        if (!getPercentageB().containsKey(tradingDate)) {
            return DecisionResult.NEUTRAL;
        }
        Double percentageBAtDay = getIndexAtDate(tradingDate).get();
        if (tradeThreshold.contains(percentageBAtDay)) {
            return DecisionResult.YES;
        }
        return DecisionResult.NO;
    }

    public Optional<Double> getIndexAtDate(LocalDate tradingDate) {
        return Optional.ofNullable(getPercentageB().get(tradingDate));
    }

    private synchronized Map<LocalDate, Double> getPercentageB() {
        if (this.percentageB == null) {
            calculateBollingerBands();
        }
        return this.percentageB;
    }

    private void calculateBollingerBands() {
        this.percentageB = new HashMap<LocalDate, Double>();
        List<Double>closePrices = stockPrices.getAdjustedClosePrices();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outRealUpperBand[] = new double[closePrices.size()];
        double outRealMiddleBand[] = new double[closePrices.size()];
        double outRealLowerBand[] = new double[closePrices.size()];
        double[] closePricesArray = Doubles.toArray(closePrices);
        RetCode returnCode = new CoreAnnotated().bbands(startIndex(),
                endIndex(closePrices),
                closePricesArray,
                movingAverageDays(),
                upperNumberOfStdDev(),
                lowerNumberOfStdDev(),
                movingAverageType(),
                outBegIdx,
                outNBElement,
                outRealUpperBand,
                outRealMiddleBand,
                outRealLowerBand);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating Bollinger Bands " + returnCode);
        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();
        int lookBack = new CoreAnnotated().bbandsLookback(movingAverageDays(), upperNumberOfStdDev(), lowerNumberOfStdDev(), movingAverageType());
        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
        for (int i = 0; i < outNBElement.value; i++) {
            int movingAverageDaysOffset = i + lookBack;
            double pctB = (closePricesArray[movingAverageDaysOffset]-outRealLowerBand[i])/(outRealUpperBand[i]-outRealLowerBand[i]);
            LocalDate tradingDate = tradingDates.get(movingAverageDaysOffset);
            this.percentageB.put(tradingDate, pctB);
        }
    }

    public MAType movingAverageType() {
        return this.movingAverageType;
    }

    private double lowerNumberOfStdDev() {
        return 2;
    }

    private double upperNumberOfStdDev() {
        return 2;
    }

    public int movingAverageDays() {
        return this.movingAverageDays;
    }

    public Range<Double> getBuyThreshold() {
        return buyThreshold;
    }

    public Range<Double> getSellThreshold() {
        return sellThreshold;
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
                "buy threshold",
                this.getBuyThreshold(),
                "moving avg days",
                this.movingAverageDays,
                "moving avg type",
                this.movingAverageType);
    }

    @Override
    public LocalDate startOn() {
        return this.startOnDate;
    }


    @Override
    public String sellDecisionToString() {
         return Joiner.on(": ").join(this.getClass().getSimpleName(),
                 "sell threshold",
                 this.getSellThreshold(),
                "moving avg days",
                this.movingAverageDays,
                "moving avg type",
                this.movingAverageType);
    }
}
