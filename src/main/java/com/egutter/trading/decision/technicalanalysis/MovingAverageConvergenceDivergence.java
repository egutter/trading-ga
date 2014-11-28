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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by egutter on 2/10/14.
 */
public class MovingAverageConvergenceDivergence implements BuyTradingDecision, SellTradingDecision {

    private final StockPrices stockPrices;
    private final Range sellThreshold;
    private Map<LocalDate, Double> macd;

    private Range buyThreshold;
    private int fastPeriod;

    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarketBuilder().build(new LocalDate(2013, 1, 1), new LocalDate(2014, 12, 31));
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            System.out.println("Stock " + stockPrices.getStockName());
            MovingAverageConvergenceDivergence bb = new MovingAverageConvergenceDivergence(stockPrices, Range.atLeast(1.0), Range.atMost(0.0), 12);
            Map<LocalDate, Double> indexes = bb.getMacd();
            maxes.add(Ordering.natural().max(indexes.values()));
            minis.add(Ordering.natural().min(indexes.values()));
        }
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
    }

    public MovingAverageConvergenceDivergence(StockPrices stockPrices,
                                              Range buyThreshold,
                                              Range sellThreshold,
                                              int fastPeriod) {
        this.stockPrices = stockPrices;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
        this.fastPeriod = fastPeriod;
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
        if (!getMacd().containsKey(tradingDate)) {
            return DecisionResult.NEUTRAL;
        }
        Double percentageBAtDay = getMacd().get(tradingDate);
        if (tradeThreshold.contains(percentageBAtDay)) {
            return DecisionResult.YES;
        }
        return DecisionResult.NO;
    }

    private synchronized Map<LocalDate, Double> getMacd() {
        if (this.macd == null) {
            calculateMacd();
        }
        return this.macd;
    }

    private void calculateMacd() {
        this.macd = new HashMap<LocalDate, Double>();
        List<Double>closePrices = stockPrices.getAdjustedClosePrices();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outMacd[] = new double[closePrices.size()];
        double outMacdSignal[] = new double[closePrices.size()];
        double outMacdHist[] = new double[closePrices.size()];
        double[] closePricesArray = Doubles.toArray(closePrices);
        RetCode returnCode = new CoreAnnotated().macd(startIndex(),
                endIndex(closePrices),
                closePricesArray,
                fastPeriod(),
                slowPeriod(),
                signalPeriod(),
                outBegIdx,
                outNBElement,
                outMacd,
                outMacdSignal,
                outMacdHist);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating MACD " + returnCode);
        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();

        for (int i = 0; i < outNBElement.value; i++) {
            int daysOffset = i + slowPeriod() - 1;
            LocalDate tradingDate = tradingDates.get(daysOffset);
            this.macd.put(tradingDate, outMacd[i]);
            System.out.println("Date "+tradingDate+"MACD-SIGNAL "+ (outMacd[i]-outMacdSignal[i])+" MACD " + outMacd[i] + " MACD Signal "+outMacdSignal[i] + "MACD HIST " + outMacdHist[i] + " PRICE " + closePricesArray[i]);
        }
    }

    private int signalPeriod() {
        return 9;
    }

    private int slowPeriod() {
        return 26;
    }

    public int fastPeriod() {
        return 12;
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

    @Override
    public String toString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "buy threshold",
                this.getBuyThreshold(),
                "sell threshold",
                this.getSellThreshold(),
                "moving avg days",
                this.fastPeriod);
    }

}
