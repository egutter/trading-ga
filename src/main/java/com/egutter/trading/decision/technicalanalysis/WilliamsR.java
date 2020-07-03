package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by egutter on 2/10/14.
 */
public class WilliamsR extends MomentumOscillator {

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2016, 1, 01);
        StockMarket stockMarket = new StockMarketBuilder().buildInMemory(fromDate, StockMarket.aapl());
        StockPrices stock = stockMarket.getStockPricesFor("AAPL");
        WilliamsR rsi = new WilliamsR(stock, Range.atMost(-80.0), Range.atLeast(-20.0), 14);
        new PrintResult().printIndexValues(stockMarket, rsi);
    }

    public WilliamsR(StockPrices stockPrices, Range buyThreshold, Range sellThreshold, Integer days) {
        super(stockPrices, buyThreshold, sellThreshold, days);
    }

    public static WilliamsR empty(StockPrices stockPrices) {
        return new WilliamsR(stockPrices, Range.atLeast(1.0), Range.atMost(1.0), 14);
    }

    @Override
    protected int calculateOscillatorLookback(CoreAnnotated coreAnnotated) {
        return coreAnnotated.willRLookback(days());
    }

    @Override
    protected RetCode calculateOscillatorValues(List<Double> closePrices, MInteger outBegIdx, MInteger outNBElement, double[] outReal, double[] hiPricesArray, double[] lowPricesArray, double[] closePricesArray, double[] volumeArray, CoreAnnotated coreAnnotated) {
        return coreAnnotated.willR(startIndex(),
                endIndex(closePrices),
                hiPricesArray,
                lowPricesArray,
                closePricesArray,
                days(),
                outBegIdx,
                outNBElement,
                outReal);
    }

//    @Override
//    protected Map<LocalDate, Double> calculateMomentumOscillatorIndex() {
//        Map<LocalDate, Double> williamsR = new HashMap<LocalDate, Double>();
//        List<Double>closePrices = stockPrices.getAdjustedClosePrices();
//        List<Double>hiPrices = stockPrices.getHighPrices();
//        List<Double>lowPrices = stockPrices.getLowPrices();
//
//        MInteger outBegIdx = new MInteger();
//        MInteger outNBElement = new MInteger();
//        double outReal[] = new double[closePrices.size()];
//        double[] hiPricesArray = Doubles.toArray(hiPrices);
//        double[] lowPricesArray = Doubles.toArray(lowPrices);
//        double[] closePricesArray = Doubles.toArray(closePrices);
//
//        RetCode returnCode = new CoreAnnotated().willR(startIndex(),
//                endIndex(closePrices),
//                hiPricesArray,
//                lowPricesArray,
//                closePricesArray,
//                days(),
//                outBegIdx,
//                outNBElement,
//                outReal);
//
//        if (!returnCode.equals(RetCode.Success)) {
//            throw new RuntimeException("Error calculating Williams R " + returnCode);
//        }
//
//        List<LocalDate> tradingDates = stockPrices.getTradingDates();
//        int lookBack = new CoreAnnotated().willRLookback(days());
//        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
//        for (int i = 0; i < outNBElement.value; i++) {
//            int daysOffset = i + lookBack;
//            LocalDate tradingDate = tradingDates.get(daysOffset);
//            williamsR.put(tradingDate, outReal[i]);
//        }
//        return williamsR;
//    }

}
