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
public class BuyTradingDecisionComposite implements BuyTradingDecision {

    private List<BuyTradingDecision> buyTradingDecisionSet = new ArrayList<BuyTradingDecision>();

    public BuyTradingDecisionComposite() {
    }

    public void addBuyTradingDecision(BuyTradingDecision tradingDecision) {
        this.buyTradingDecisionSet.add(tradingDecision);
    }

    @Override
    public boolean shouldBuyOn(final LocalDate tradingDate) {
        return all(buyTradingDecisionSet, new Predicate<BuyTradingDecision>() {
            @Override
            public boolean apply(BuyTradingDecision tradingDecision) {
                return tradingDecision.shouldBuyOn(tradingDate);
            }
        });
    }

    @Override
    public String toString() {
        return Joiner.on(" |*| Buy Trading Decisions: ").join(buyTradingDecisionSet);
    }
}
