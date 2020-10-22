package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stats.MetricsRecorder;
import com.egutter.trading.stats.MetricsRecorderFactory;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import com.google.common.base.Joiner;
import com.google.common.collect.Range;
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

/**
 * Created by egutter on 3/1/15.
 */
public abstract class MomentumOscillator implements BuyTradingDecision, SellTradingDecision, TechnicalAnalysisIndicator, Function<TimeFrameQuote, Boolean> {

    protected final StockPrices stockPrices;
    protected final Range sellThreshold;
    protected Map<LocalDate, Double> momentumOscillatorIndex;

    protected Range buyThreshold;
    protected int days;
    protected LocalDate startOnDate = LocalDate.now();

    public MomentumOscillator(StockPrices stockPrices,
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

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        return shouldTradeOn(tradingDate, buyThreshold);
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        return shouldTradeOn(tradingDate, sellThreshold);
    }

    private DecisionResult shouldTradeOn(LocalDate tradingDate, Range tradeThreshold) {
        if (!getMomentumOscillatorIndex().containsKey(tradingDate)) {
            return DecisionResult.NEUTRAL;
        }
        Double indexAtDay = getIndexAtDate(tradingDate).get();
        if (tradeThreshold.contains(indexAtDay)) {
            return DecisionResult.YES;
        }
        return DecisionResult.NO;
    }

    public Optional<Double> getIndexAtDate(LocalDate tradingDate) {
        return Optional.ofNullable(getMomentumOscillatorIndex().get(tradingDate));
    }

    public synchronized Map<LocalDate, Double> getMomentumOscillatorIndex() {
        if (this.momentumOscillatorIndex == null) {
            this.momentumOscillatorIndex = calculateMomentumOscillatorIndex();
        }
        return this.momentumOscillatorIndex;
    }

    protected Map<LocalDate, Double> calculateMomentumOscillatorIndex() {
        Map<LocalDate, Double> oscillatorIndex = new HashMap<LocalDate, Double>();
        List<Double>closePrices = stockPrices.getClosePrices();
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

        CoreAnnotated coreAnnotated = new CoreAnnotated();
        RetCode returnCode = calculateOscillatorValues(closePrices, outBegIdx, outNBElement, outReal, hiPricesArray, lowPricesArray, closePricesArray, volumeArray, coreAnnotated);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating Money Flow Index " + returnCode);
        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();
        int lookBack = calculateOscillatorLookback(coreAnnotated);
        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
        for (int i = 0; i < outNBElement.value; i++) {
            int daysOffset = i + lookBack;
            LocalDate tradingDate = tradingDates.get(daysOffset);
            oscillatorIndex.put(tradingDate, outReal[i]);
        }
        return oscillatorIndex;
    }

    protected abstract int calculateOscillatorLookback(CoreAnnotated coreAnnotated);

    protected abstract RetCode calculateOscillatorValues(List<Double> closePrices, MInteger outBegIdx, MInteger outNBElement, double[] outReal, double[] hiPricesArray, double[] lowPricesArray, double[] closePricesArray, double[] volumeArray, CoreAnnotated coreAnnotated);

    @Override
    public String buyDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "buy threshold",
                this.getBuyThreshold(),
                "days",
                this.days);
    }


    @Override
    public String sellDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "sell threshold",
                this.getSellThreshold(),
                "days",
                this.days);
    }

    public int days() {
        return this.days;
    }

    public Range<Double> getBuyThreshold() {
        return buyThreshold;
    }

    public Range<Double> getSellThreshold() {
        return sellThreshold;
    }

    protected int endIndex(List<Double> closePrices) {
        return closePrices.size() - 1;
    }

    protected int startIndex() {
        return 0;
    }

    @Override
    public LocalDate startOn() {
        return this.startOnDate;
    }
}
