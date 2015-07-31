package com.egutter.trading.decision.technicalanalysis;

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
        StockMarket stockMarket = new StockMarketBuilder().build(new LocalDate(2013, 1, 1), new LocalDate(2014, 12, 31));
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            if (stockPrices.getDailyQuotes().size() < 28) continue;
            System.out.println("Stock " + stockPrices.getStockName() + " size "+ stockPrices.getLowPrices().size());
            WilliamsR willR = new WilliamsR(stockPrices, Range.atLeast(80), Range.atMost(10), 14);
            Map<LocalDate, Double> indexes = willR.getMomentumOscillatorIndex();
            maxes.add(Ordering.natural().max(indexes.values()));
            minis.add(Ordering.natural().min(indexes.values()));
        }
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
        System.out.println("Max value " + maxes);
        System.out.println("Min value " + minis);
    }

    public WilliamsR(StockPrices stockPrices, Range buyThreshold, Range sellThreshold, Integer days) {
        super(stockPrices, buyThreshold, sellThreshold, days);
    }

    @Override
    protected Map<LocalDate, Double> calculateMomentumOscillatorIndex() {
        Map<LocalDate, Double> williamsR = new HashMap<LocalDate, Double>();
        List<Double>closePrices = stockPrices.getAdjustedClosePrices();
        List<Double>hiPrices = stockPrices.getHighPrices();
        List<Double>lowPrices = stockPrices.getLowPrices();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outReal[] = new double[closePrices.size()];
        double[] hiPricesArray = Doubles.toArray(hiPrices);
        double[] lowPricesArray = Doubles.toArray(lowPrices);
        double[] closePricesArray = Doubles.toArray(closePrices);

        RetCode returnCode = new CoreAnnotated().willR(startIndex(),
                endIndex(closePrices),
                hiPricesArray,
                lowPricesArray,
                closePricesArray,
                days(),
                outBegIdx,
                outNBElement,
                outReal);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating Williams R " + returnCode);
        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();
        int lookBack = new CoreAnnotated().willRLookback(days());
        for (int i = 0; i < outNBElement.value; i++) {
            int daysOffset = i + lookBack;
            LocalDate tradingDate = tradingDates.get(daysOffset);
            williamsR.put(tradingDate, outReal[i]);
        }
        return williamsR;
    }

}
