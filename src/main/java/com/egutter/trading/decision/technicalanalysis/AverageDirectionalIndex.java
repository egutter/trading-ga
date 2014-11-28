package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Ordering;
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
 * Created by egutter on 6/4/14.
 */
public class AverageDirectionalIndex implements BuyTradingDecision, SellTradingDecision {

    private final HashMap<LocalDate, Double> averageDirectionalIndex = new HashMap<LocalDate, Double>();

    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarketBuilder().build(new LocalDate(2013, 1, 1), new LocalDate(2014, 12, 31));
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            System.out.println("Stock " + stockPrices.getStockName());
            AverageDirectionalIndex adx = new AverageDirectionalIndex(stockPrices, 14);
            Map<LocalDate, Double> indexes = adx.getAverageDirectionalIndex();
            maxes.add(Ordering.natural().max(indexes.values()));
            minis.add(Ordering.natural().min(indexes.values()));
        }
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
    }

    public AverageDirectionalIndex(StockPrices stockPrices, int days) {
        List<Double>closePrices = stockPrices.getAdjustedClosePrices();
        List<Double>hiPrices = stockPrices.getHighPrices();
        List<Double>lowPrices = stockPrices.getLowPrices();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outReal[] = new double[closePrices.size()];
        double[] hiPricesArray = Doubles.toArray(hiPrices);
        double[] lowPricesArray = Doubles.toArray(lowPrices);
        double[] closePricesArray = Doubles.toArray(closePrices);

        RetCode returnCode = new CoreAnnotated().adx(startIndex(),
                endIndex(closePrices),
                hiPricesArray,
                lowPricesArray,
                closePricesArray,
                days,
                outBegIdx,
                outNBElement,
                outReal);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating Average Directional Index " + returnCode);
        }
        List<LocalDate> tradingDates = stockPrices.getTradingDates();

        for (int i = 0; i < outNBElement.value; i++) {
            int daysOffset = i + days - 1;
            LocalDate tradingDate = tradingDates.get(daysOffset);
            this.averageDirectionalIndex.put(tradingDate, outReal[i]);
            System.out.println("Day ["+tradingDate+"] ADX ["+outReal[i]+"]");
        }
    }

    public HashMap<LocalDate, Double> getAverageDirectionalIndex() {
        return averageDirectionalIndex;
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        return null;
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        return null;
    }

    private int endIndex(List<Double> closePrices) {
        return closePrices.size() - 1;
    }

    private int startIndex() {
        return 0;
    }

}
