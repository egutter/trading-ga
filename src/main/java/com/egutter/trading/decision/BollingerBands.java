package com.egutter.trading.decision;

import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Range;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by egutter on 2/10/14.
 */
public class BollingerBands implements TradingDecision {

    private final StockPrices closePrices;
    private final Range sellThreshold;
    private Map<LocalDate, Double> percentageB = new HashMap<LocalDate, Double>();

    private Range buyThreshold;
    private int movingAverageDays;
    private MAType movingAverageType;

    public BollingerBands(StockPrices stockPrices,
                          Range buyThreshold,
                          Range sellThreshold,
                          int movingAverageDays,
                          MAType movingAverageType) {
        this.closePrices = stockPrices;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
        this.movingAverageDays = movingAverageDays;
        this.movingAverageType = movingAverageType;
        calculateBollingerBands(stockPrices);
    }

    @Override
    public boolean shouldBuyOn(LocalDate tradingDate) {
        if (!percentageB.containsKey(tradingDate)) {
            return false;
        }
        Double percentageBAtDay = percentageB.get(tradingDate);
        return buyThreshold.contains(percentageBAtDay);
    }

    @Override
    public boolean shouldSellOn(LocalDate tradingDate) {
        if (!percentageB.containsKey(tradingDate)) {
            return false;
        }
        Double percentageBAtDay = percentageB.get(tradingDate);
        return sellThreshold.contains(percentageBAtDay);
    }

    private void calculateBollingerBands(StockPrices stockPrices) {
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

        for (int i = 0; i < outNBElement.value; i++) {
            int movingAverageDaysOffset = i + movingAverageDays() - 1;
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

    public Range getBuyThreshold() {
        return buyThreshold;
    }

    public Range getSellThreshold() {
        return sellThreshold;
    }

    private int endIndex(List<Double> closePrices) {
        return closePrices.size() - 1;
    }

    private int startIndex() {
        return 0;
    }
}
