package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import com.google.common.base.Joiner;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.function.Function;

/**
 * Created by egutter on 6/4/14.
 */
public class AroonOscilator implements BuyTradingDecision, SellTradingDecision, TechnicalAnalysisIndicator, Function<TimeFrameQuote, Boolean> {

    private HashMap<LocalDate, Double> aroonOscilator;
    private final StockPrices stockPrices;
    private final Range buyThreshold;
    private final Range sellThreshold;
    private final int days;
    private LocalDate startOnDate = LocalDate.now();

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

    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        return this.shouldBuyOn(timeFrameQuote.getQuoteAtDay().getTradingDate()).equals(DecisionResult.YES);
    }

    public static AroonOscilator empty(StockPrices stockPrices) {
        return new AroonOscilator(stockPrices, Range.atLeast(1.0), Range.atMost(1.0), 14);
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
        Double aroonAtDay = getIndexAtDate(tradingDate).get();
        if (tradeThreshold.contains(aroonAtDay)) {
            return DecisionResult.YES;
        }
        return DecisionResult.NO;
    }

    public Optional<Double> getIndexAtDate(LocalDate tradingDate) {
        return Optional.ofNullable(getAroonOscilator().get(tradingDate));
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
        int lookBack = new CoreAnnotated().aroonLookback(days());
        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
        for (int i = 0; i < outNBElement.value; i++) {
            int daysOffset = i + lookBack;
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
    public String buyDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "buy threshold",
                this.getBuyThreshold(),
                "days",
                this.days);
    }

    @Override
    public LocalDate startOn() {
        return this.startOnDate;
    }


    @Override
    public String sellDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "sell threshold",
                this.getSellThreshold(),
                "days",
                this.days);
    }
    public Range<Double> getBuyThreshold() {
        return buyThreshold;
    }

    public Range<Double> getSellThreshold() {
        return sellThreshold;
    }

    public int days() {
        return days;
    }

}
