package com.egutter.trading.order;

import com.egutter.trading.stock.DailyQuote;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

public class OrderExtraInfo {
    private List<Pair<Double, Double>> triggerSellPrices = new ArrayList<Pair<Double, Double>>();
    private Optional<Integer> numberOfShares = Optional.empty();
    private double buyPriceTrigger;
    private DailyQuote highQuote;
    private DailyQuote lowQuote;
    private Optional<Double> sellPrice = Optional.empty();

    public void addTriggerSellPrice(double closePrice, double percentage) {
        this.triggerSellPrices.add(new Pair<Double, Double>(closePrice, percentage));
    }

    public Optional<Pair<Double, Double>> getNextTriggerSellPrice() {
        return triggerSellPrices.stream().findFirst();
    }

    public void addNumberOfShares(int count) {
        this.numberOfShares = Optional.of(count);
    }

    public Optional<Integer> getNumberOfShares() {
        return numberOfShares;
    }

    public void removeFirstTriggerSellPrice() {
        this.triggerSellPrices.remove(0);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("Sell prices:");
        triggerSellPrices.stream().forEach(price -> buffer.append(" Sell price: ").append(price.getFirst()).append(" Percentage: ").append(price.getSecond()));
        return new StringJoiner(" ", "OrderExtraInfo ", "").
                add("shares:").
                add(String.valueOf(numberOfShares)).
                add(buffer).
                add("Buy price trigger").
                add(String.valueOf(buyPriceTrigger)).
                add("High quote").
                add(String.valueOf(highQuote.highQuoteToString())).
                add("Low quote").
                add(String.valueOf(lowQuote.lowQuoteToString())).
                toString();
    }

    public void setBuyPriceTrigger(double buyPriceTrigger) {
        this.buyPriceTrigger = buyPriceTrigger;
    }

    public double getBuyPriceTrigger() {
        return buyPriceTrigger;
    }

    public void setHighQuote(DailyQuote highQuote) {
        this.highQuote = highQuote;
    }

    public void setLowQuote(DailyQuote lowQuote) {
        this.lowQuote = lowQuote;
    }

    public void addSellPrice(double price) {
        this.sellPrice = Optional.of(price);
    }

    public Optional<Double> getSellPrice() {
        return sellPrice;
    }
}
