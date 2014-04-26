package com.egutter.trading.decision;

import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MAType;
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
public class MoneyFlowIndex implements BuyTradingDecision, SellTradingDecision {

    private final StockPrices stockPrices;
    private final Range sellThreshold;
    private Map<LocalDate, Double> moneyFlowIndex;

    private Range buyThreshold;
    private int days;

    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarketBuilder().build();
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            MoneyFlowIndex mfi = new MoneyFlowIndex(stockPrices, Range.atLeast(80), Range.atMost(10), 20);
            Map<LocalDate, Double> indexes = mfi.getMoneyFlowIndex();
            maxes.add(Ordering.natural().max(indexes.values()));
            minis.add(Ordering.natural().min(indexes.values()));
        }
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
    }

    public MoneyFlowIndex(StockPrices stockPrices,
                          Range buyThreshold,
                          Range sellThreshold,
                          int days) {
        this.stockPrices = stockPrices;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
        this.days = days;
    }

    @Override
    public boolean shouldBuyOn(LocalDate tradingDate) {
        if (!getMoneyFlowIndex().containsKey(tradingDate)) {
            return false;
        }
        Double mfiAtDay = getMoneyFlowIndex().get(tradingDate);
        return buyThreshold.contains(mfiAtDay);
    }

    @Override
    public boolean shouldSellOn(LocalDate tradingDate) {
        if (!getMoneyFlowIndex().containsKey(tradingDate)) {
            return false;
        }
        Double mfiAtDay = getMoneyFlowIndex().get(tradingDate);
        return sellThreshold.contains(mfiAtDay);
    }

    private synchronized Map<LocalDate, Double> getMoneyFlowIndex() {
        if (this.moneyFlowIndex == null) {
            calculateMoneyFlowIndex();
        }
        return this.moneyFlowIndex;
    }

    private void calculateMoneyFlowIndex() {
        this.moneyFlowIndex = new HashMap<LocalDate, Double>();
        List<Double>closePrices = stockPrices.getAdjustedClosePrices();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outReal[] = new double[closePrices.size()];
        double[] hiPricesArray = Doubles.toArray(closePrices);
        double[] lowPricesArray = Doubles.toArray(closePrices);
        double[] closePricesArray = Doubles.toArray(closePrices);
        double[] volumeArray = Doubles.toArray(closePrices);

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

        for (int i = 0; i < outNBElement.value; i++) {
            int daysOffset = i + days() - 1;
            LocalDate tradingDate = tradingDates.get(daysOffset);
            this.moneyFlowIndex.put(tradingDate, outReal[i]);
        }
    }

    public int days() {
        return this.days;
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
                "MFI days",
                this.days);
    }

}