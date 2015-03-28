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
import com.google.common.math.DoubleMath;
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
public class RelativeStrengthIndex extends MomentumOscillator {

    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarketBuilder().build(new LocalDate(2012, 1, 1), new LocalDate(2014, 12, 31));
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        List<Double> means = new ArrayList<Double>();
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            System.out.println("Stock " + stockPrices.getStockName());
            RelativeStrengthIndex mfi = new RelativeStrengthIndex(stockPrices, Range.atLeast(80), Range.atMost(10), 14);
            Map<LocalDate, Double> indexes = mfi.getMomentumOscillatorIndex();
            maxes.add(Ordering.natural().max(indexes.values()));
            minis.add(Ordering.natural().min(indexes.values()));
            means.add(DoubleMath.mean(indexes.values()));
        }
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
        System.out.println("Mean value " + DoubleMath.mean(means));
    }

    public RelativeStrengthIndex(StockPrices stockPrices, Range buyThreshold, Range sellThreshold, Integer days) {
        super(stockPrices, buyThreshold, sellThreshold, days);
    }

    @Override
    protected Map<LocalDate, Double> calculateMomentumOscillatorIndex() {
        Map<LocalDate, Double> relativeStrengthIndex = new HashMap<LocalDate, Double>();
        List<Double>closePrices = stockPrices.getAdjustedClosePrices();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outReal[] = new double[closePrices.size()];
        double[] closePricesArray = Doubles.toArray(closePrices);

        RetCode returnCode = new CoreAnnotated().rsi(startIndex(),
                endIndex(closePrices),
                closePricesArray,
                days(),
                outBegIdx,
                outNBElement,
                outReal);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating Relative Strength Index " + returnCode);
        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();
        int lookBack = new CoreAnnotated().rsiLookback(days());
        for (int i = 0; i < outNBElement.value; i++) {
            int daysOffset = i + lookBack;
            LocalDate tradingDate = tradingDates.get(daysOffset);
            relativeStrengthIndex.put(tradingDate, outReal[i]);
        }
        return relativeStrengthIndex;
    }


}
