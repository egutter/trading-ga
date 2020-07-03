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
public class UltimateOscillator extends MomentumOscillator {

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2016, 1, 01);
        StockMarket stockMarket = new StockMarketBuilder().buildInMemory(fromDate, StockMarket.aapl());
        StockPrices stock = stockMarket.getStockPricesFor("AAPL");
        UltimateOscillator ultOsc = new UltimateOscillator(stock, Range.atMost(30.0), Range.atLeast(70.0), 7);
        new PrintResult().printIndexValues(stockMarket, ultOsc);
    }

    public UltimateOscillator(StockPrices stockPrices, Range buyThreshold, Range sellThreshold, Integer days) {
        super(stockPrices, buyThreshold, sellThreshold, days);
    }

    @Override
    protected int calculateOscillatorLookback(CoreAnnotated coreAnnotated) {
        return coreAnnotated.ultOscLookback(days(), days2(), days4());
    }

    @Override
    protected RetCode calculateOscillatorValues(List<Double> closePrices, MInteger outBegIdx, MInteger outNBElement, double[] outReal, double[] hiPricesArray, double[] lowPricesArray, double[] closePricesArray, double[] volumeArray, CoreAnnotated coreAnnotated) {
        return coreAnnotated.ultOsc(startIndex(),
                endIndex(closePrices),
                hiPricesArray,
                lowPricesArray,
                closePricesArray,
                days(),
                days2(),
                days4(),
                outBegIdx,
                outNBElement,
                outReal);
    }

//    @Override
//    protected Map<LocalDate, Double> calculateMomentumOscillatorIndex() {
//        Map<LocalDate, Double> ultimateOscillator = new HashMap<LocalDate, Double>();
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
//        RetCode returnCode = new CoreAnnotated().ultOsc(startIndex(),
//                endIndex(closePrices),
//                hiPricesArray,
//                lowPricesArray,
//                closePricesArray,
//                days(),
//                days2(),
//                days4(),
//                outBegIdx,
//                outNBElement,
//                outReal);
//
//        if (!returnCode.equals(RetCode.Success)) {
//            throw new RuntimeException("Error calculating Ultimate Oscillator " + returnCode);
//        }
//
//        List<LocalDate> tradingDates = stockPrices.getTradingDates();
//        int lookBack = new CoreAnnotated().ultOscLookback(days(), days2(), days4());
//        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
//        for (int i = 0; i < outNBElement.value; i++) {
//            int daysOffset = i + lookBack;
//            LocalDate tradingDate = tradingDates.get(daysOffset);
//            ultimateOscillator.put(tradingDate, outReal[i]);
//        }
//        return ultimateOscillator;
//    }

    private int days4() {
        return days() * 4;
    }

    private int days2() {
        return days() * 2;
    }

}
