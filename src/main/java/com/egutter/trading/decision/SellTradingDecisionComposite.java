package com.egutter.trading.decision;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.all;

/**
 * Created by egutter on 2/16/14.
 */
public class SellTradingDecisionComposite implements SellTradingDecision {

    private List<SellTradingDecision> sellTradingDecisionSet = new ArrayList<SellTradingDecision>();

    public void addSellTradingDecision(SellTradingDecision tradingDecision) {
        this.sellTradingDecisionSet.add(tradingDecision);
    }

    @Override
    public boolean shouldSellOn(final LocalDate tradingDate) {
        return all(sellTradingDecisionSet, new Predicate<SellTradingDecision>() {
            @Override
            public boolean apply(SellTradingDecision tradingDecision) {
                return tradingDecision.shouldSellOn(tradingDate);
            }
        });
    }

    @Override
    public String toString() {
        return Joiner.on(" |*| Sell Trading Decisions: ").join(sellTradingDecisionSet);
    }
}
