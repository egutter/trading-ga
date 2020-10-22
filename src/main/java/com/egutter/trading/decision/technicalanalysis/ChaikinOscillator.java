package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stats.MetricsRecorder;
import com.egutter.trading.stats.MetricsRecorderFactory;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import com.google.common.base.Joiner;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ChaikinOscillator extends CrossOverOscillator implements Function<TimeFrameQuote, Boolean>  {

    private final int fastPeriod;
    private final int slowPeriod;
    private Map<LocalDate, Double> oscValues = new HashMap<LocalDate, Double>();

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2020, 1, 01);
        StockMarket stockMarket = new StockMarketBuilder().buildInMemory(fromDate, new String[] {"MA"});
        StockPrices stock = stockMarket.getStockPricesFor("MA");
        ChaikinOscillator oscillator = new ChaikinOscillator(stock,
                3, 10);
        new PrintResult().printIndexValues(stockMarket, oscillator);

    }

    public ChaikinOscillator(StockPrices stockPrices,
                             int fastDays,
                             int slowDays) {
        super(stockPrices);
        this.fastPeriod = fastDays;
        this.slowPeriod = slowDays;
        calculateOscillator();
    }


    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        boolean shouldBuy = this.shouldBuyOn(timeFrameQuote.getQuoteAtDay().getTradingDate()).equals(DecisionResult.YES);
        if (shouldBuy) {
            MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.CHAIKIN_DECISION_YES);
        } else {
            MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.CHAIKIN_DECISION_NO);
        }
        return shouldBuy;
    }

    @Override
    public Map<LocalDate, Double> getIndexValues() {
        return this.oscValues;
    }

    @Override
    public Optional<Double> getSignalValue(LocalDate day) {
        return Optional.of(Double.valueOf(0));
    }

    private void calculateOscillator() {
        List<Double> closePrices = stockPrices.getClosePrices();
        List<Double> hiPrices = stockPrices.getHighPrices();
        List<Double> lowPrices = stockPrices.getLowPrices();
        List<Long> volumes = stockPrices.getVolumes();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outReal[] = new double[closePrices.size()];
        double[] hiPricesArray = Doubles.toArray(hiPrices);
        double[] lowPricesArray = Doubles.toArray(lowPrices);
        double[] closePricesArray = Doubles.toArray(closePrices);
        double[] volumeArray = Doubles.toArray(volumes);

        CoreAnnotated coreAnnotated = new CoreAnnotated();
        RetCode returnCode = coreAnnotated.adOsc(
                startIndex(),
                endIndex(closePrices),
                hiPricesArray,
                lowPricesArray,
                closePricesArray,
                volumeArray,
                this.fastPeriod,
                this.slowPeriod,
                outBegIdx,
                outNBElement,
                outReal);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating Chaikin Oscillator " + returnCode + " with decision: " + this.sellDecisionToString() + " " + this.buyDecisionToString() );
        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();
        int lookBack = coreAnnotated.adOscLookback(this.fastPeriod, this.slowPeriod);
        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
        for (int i = 0; i < outNBElement.value; i++) {
            int offset = i + lookBack;
            LocalDate tradingDate = tradingDates.get(offset);
            this.oscValues.put(tradingDate, outReal[i]);
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
        return decisionToString();
    }

    @Override
    public String sellDecisionToString() {
        return decisionToString();
    }

    private String decisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "Fast days",
                this.fastPeriod,
                "Slow days",
                this.slowPeriod);
    }

}
