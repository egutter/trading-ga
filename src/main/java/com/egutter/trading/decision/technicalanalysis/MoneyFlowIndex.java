package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.*;

/**
 * Created by egutter on 2/10/14.
 */
public class MoneyFlowIndex extends MomentumOscillator {

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2016, 1, 01);
        StockMarket stockMarket = new StockMarketBuilder().buildInMemory(fromDate, StockMarket.aapl());
        StockPrices stock = stockMarket.getStockPricesFor("AAPL");
        MoneyFlowIndex mfi = new MoneyFlowIndex(stock, Range.atMost(25.0), Range.atLeast(80.0), 14);
        new PrintResult().printIndexValues(stockMarket, mfi);

    }

    public MoneyFlowIndex(StockPrices stockPrices, Range buyThreshold, Range sellThreshold, Integer days) {
        super(stockPrices, buyThreshold, sellThreshold, days);
    }

    public static MoneyFlowIndex empty(StockPrices stockPrices) {
        return new MoneyFlowIndex(stockPrices, Range.atLeast(1.0), Range.atMost(1.0), 14);
    }

    @Override
    protected int calculateOscillatorLookback(CoreAnnotated coreAnnotated) {
        return coreAnnotated.mfiLookback(days());
    }

    @Override
    protected RetCode calculateOscillatorValues(List<Double> closePrices, MInteger outBegIdx, MInteger outNBElement, double[] outReal, double[] hiPricesArray, double[] lowPricesArray, double[] closePricesArray, double[] volumeArray, CoreAnnotated coreAnnotated) {
        return coreAnnotated.mfi(startIndex(),
                endIndex(closePrices),
                hiPricesArray,
                lowPricesArray,
                closePricesArray,
                volumeArray,
                days(),
                outBegIdx,
                outNBElement,
                outReal);
    }

//    @Override
//    protected Map<LocalDate, Double> calculateMomentumOscillatorIndex() {
//        Map<LocalDate, Double> moneyFlowIndex = new HashMap<LocalDate, Double>();
//        List<Double>closePrices = stockPrices.getClosePrices();
//        List<Double>hiPrices = stockPrices.getHighPrices();
//        List<Double>lowPrices = stockPrices.getLowPrices();
//        List<? extends Number> volume = stockPrices.getVolumes();
//
//        MInteger outBegIdx = new MInteger();
//        MInteger outNBElement = new MInteger();
//        double outReal[] = new double[closePrices.size()];
//        double[] hiPricesArray = Doubles.toArray(hiPrices);
//        double[] lowPricesArray = Doubles.toArray(lowPrices);
//        double[] closePricesArray = Doubles.toArray(closePrices);
//        double[] volumeArray = Doubles.toArray(volume);
//
//        RetCode returnCode = new CoreAnnotated().mfi(startIndex(),
//                endIndex(closePrices),
//                hiPricesArray,
//                lowPricesArray,
//                closePricesArray,
//                volumeArray,
//                days(),
//                outBegIdx,
//                outNBElement,
//                outReal);
//
//        if (!returnCode.equals(RetCode.Success)) {
//            throw new RuntimeException("Error calculating Money Flow Index " + returnCode);
//        }
//
//        List<LocalDate> tradingDates = stockPrices.getTradingDates();
//        int lookBack = new CoreAnnotated().mfiLookback(days());
//        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
//        for (int i = 0; i < outNBElement.value; i++) {
//            int daysOffset = i + lookBack;
//            LocalDate tradingDate = tradingDates.get(daysOffset);
//            moneyFlowIndex.put(tradingDate, outReal[i]);
//        }
//        return moneyFlowIndex;
//    }

}
