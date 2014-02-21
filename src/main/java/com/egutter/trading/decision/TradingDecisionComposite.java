package com.egutter.trading.decision;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Iterables.all;

/**
 * Created by egutter on 2/16/14.
 */
public class TradingDecisionComposite implements TradingDecision{

    private List<TradingDecision> tradingDecisionSet = new ArrayList<TradingDecision>();

    public TradingDecisionComposite() {
    }

    public void add(TradingDecision generate) {
        this.tradingDecisionSet.add(generate);
    }

    @Override
    public boolean shouldBuyOn(final LocalDate tradingDate) {
        return all(tradingDecisionSet, new Predicate<TradingDecision>() {
            @Override
            public boolean apply(TradingDecision tradingDecision) {
                return tradingDecision.shouldBuyOn(tradingDate);
            }
        });
    }

    @Override
    public boolean shouldSellOn(final LocalDate tradingDate) {
        return all(tradingDecisionSet, new Predicate<TradingDecision>() {
            @Override
            public boolean apply(TradingDecision tradingDecision) {
                return tradingDecision.shouldSellOn(tradingDate);
            }
        });
    }

    @Override
    public String toString() {
        return Joiner.on(" |*| ").join(Arrays.asList(tradingDecisionSet));
    }
}
