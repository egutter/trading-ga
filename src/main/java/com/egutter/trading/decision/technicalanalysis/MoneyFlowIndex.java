package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
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
        StockMarket stockMarket = new StockMarketBuilder().build(new LocalDate(2013, 1, 1), new LocalDate(2014, 12, 31));
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            System.out.println("Stock " + stockPrices.getStockName());
            MoneyFlowIndex mfi = new MoneyFlowIndex(stockPrices, Range.atLeast(80), Range.atMost(10), 20);
            Map<LocalDate, Double> indexes = mfi.getMomentumOscillatorIndex();
            maxes.add(Ordering.natural().max(indexes.values()));
            minis.add(Ordering.natural().min(indexes.values()));
        }
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
    }

    public MoneyFlowIndex(StockPrices stockPrices, Range buyThreshold, Range sellThreshold, Integer days) {
        super(stockPrices, buyThreshold, sellThreshold, days);
    }

    public static MoneyFlowIndex empty(StockPrices stockPrices) {
        return new MoneyFlowIndex(stockPrices, Range.atLeast(1.0), Range.atMost(1.0), 14);
    }

    @Override
    protected Map<LocalDate, Double> calculateMomentumOscillatorIndex() {
        Map<LocalDate, Double> moneyFlowIndex = new HashMap<LocalDate, Double>();
        List<Double>closePrices = stockPrices.getAdjustedClosePrices();
        List<Double>hiPrices = stockPrices.getHighPrices();
        List<Double>lowPrices = stockPrices.getLowPrices();
        List<? extends Number> volume = stockPrices.getVolumes();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outReal[] = new double[closePrices.size()];
        double[] hiPricesArray = Doubles.toArray(hiPrices);
        double[] lowPricesArray = Doubles.toArray(lowPrices);
        double[] closePricesArray = Doubles.toArray(closePrices);
        double[] volumeArray = Doubles.toArray(volume);

        RetCode returnCode = new CoreAnnotated().mfi(startIndex(),
                endIndex(closePrices),
                hiPricesArray,
                lowPricesArray,
                closePricesArray,
                volumeArray,
                days(),
                outBegIdx,
                outNBElement,
                outReal);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating Money Flow Index " + returnCode);
        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();
        int lookBack = new CoreAnnotated().mfiLookback(days());
        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
        for (int i = 0; i < outNBElement.value; i++) {
            int daysOffset = i + lookBack;
            LocalDate tradingDate = tradingDates.get(daysOffset);
            moneyFlowIndex.put(tradingDate, outReal[i]);
        }
        return moneyFlowIndex;
    }

}
