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
 * Created by egutter on 6/4/14.
 */
public class AroonOscilator implements BuyTradingDecision, SellTradingDecision {

    private HashMap<LocalDate, Double> aroonOscilator;
    private final StockPrices stockPrices;
    private final Range buyThreshold;
    private final Range sellThreshold;
    private final int days;

    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarketBuilder().build(new LocalDate(2013, 1, 1), new LocalDate(2014, 12, 31));
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            System.out.println("Stock " + stockPrices.getStockName());
            AroonOscilator adx = new AroonOscilator(stockPrices, Range.atLeast(1.0), Range.atMost(-1.0), 14);
            adx.calculateAroonOscilator();
            Map<LocalDate, Double> indexes = adx.getAroonOscilator();
            maxes.add(Ordering.natural().max(indexes.values()));
            minis.add(Ordering.natural().min(indexes.values()));
        }
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
    }

    public AroonOscilator(StockPrices stockPrices,
                          Range buyThreshold,
                          Range sellThreshold,
                          int days) {
        this.stockPrices = stockPrices;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
        this.days = days;
    }

    private synchronized HashMap<LocalDate, Double> getAroonOscilator() {
        if (this.aroonOscilator == null) {
            calculateAroonOscilator();
        }
        return this.aroonOscilator;
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
        if (!getAroonOscilator().containsKey(tradingDate)) {
            return DecisionResult.NEUTRAL;
        }
        Double mfiAtDay = getAroonOscilator().get(tradingDate);
        if (tradeThreshold.contains(mfiAtDay)) {
            return DecisionResult.YES;
        }
        return DecisionResult.NO;
    }

    private void calculateAroonOscilator() {
        this.aroonOscilator = new HashMap<LocalDate, Double>();
        List<Double> closePrices = stockPrices.getAdjustedClosePrices();
        List<Double>hiPrices = stockPrices.getHighPrices();
        List<Double>lowPrices = stockPrices.getLowPrices();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outReal[] = new double[closePrices.size()];
        double[] hiPricesArray = Doubles.toArray(hiPrices);
        double[] lowPricesArray = Doubles.toArray(lowPrices);
        double[] closePricesArray = Doubles.toArray(closePrices);

        RetCode returnCode = new CoreAnnotated().aroonOsc(startIndex(),
                endIndex(closePrices),
                hiPricesArray,
                lowPricesArray,
                days,
                outBegIdx,
                outNBElement,
                outReal);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating Aroon Oscilator" + returnCode);
        }
        List<LocalDate> tradingDates = stockPrices.getTradingDates();

        for (int i = 0; i < outNBElement.value; i++) {
            int daysOffset = i + days - 1;
            LocalDate tradingDate = tradingDates.get(daysOffset);
            this.aroonOscilator.put(tradingDate, outReal[i]);
        }
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
                "days",
                this.days);
    }


    public Range getBuyThreshold() {
        return buyThreshold;
    }

    public Range getSellThreshold() {
        return sellThreshold;
    }
}
