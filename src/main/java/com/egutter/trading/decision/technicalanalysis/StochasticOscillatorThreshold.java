package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.google.common.base.Joiner;
import com.google.common.collect.Range;
import org.joda.time.LocalDate;

import java.util.Map;

public class StochasticOscillatorThreshold implements BuyTradingDecision {

    private StochasticOscillator oscillator;
    protected Range buyThreshold;

    public StochasticOscillatorThreshold(StochasticOscillator oscillator, Range buyThreshold) {
        this.oscillator = oscillator;
        this.buyThreshold = buyThreshold;
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        if (!getIndexValues().containsKey(tradingDate)) {
            return DecisionResult.NEUTRAL;
        }
        Double indexAtDay = getIndexAtDate(tradingDate);
        if (buyThreshold.contains(indexAtDay)) {
            return DecisionResult.YES;
        }
        return DecisionResult.NO;
    }

    private Double getIndexAtDate(LocalDate tradingDate) {
        return oscillator.getIndexValues().get(tradingDate);
    }

    private Map<LocalDate, Double> getIndexValues() {
        return oscillator.getIndexValues();
    }

    @Override
    public String buyDecisionToString() {
        return Joiner.on(": ").
                join(this.getClass().getSimpleName(),
                        oscillator.buyDecisionToString(),"Buy threshold", buyThreshold);
    }

    @Override
    public LocalDate startOn() {
        return oscillator.startOn();
    }
}
