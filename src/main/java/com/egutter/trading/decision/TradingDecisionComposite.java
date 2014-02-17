package com.egutter.trading.decision;

import com.egutter.trading.decision.generator.TradingDecisionGenerator;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Predicate;
import org.joda.time.LocalDate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.all;

/**
 * Created by egutter on 2/16/14.
 */
public class TradingDecisionComposite implements TradingDecision{

    private Set<TradingDecision> tradingDecisionSet = new HashSet<TradingDecision>();
    private SellAllAtLastDay sellAtLastDayDecision;

    public TradingDecisionComposite(Portfolio portfolio, StockPrices stockPrices) {
        sellAtLastDayDecision = new SellAllAtLastDay(stockPrices);
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
        boolean allDecisionsApply = all(tradingDecisionSet, new Predicate<TradingDecision>() {
            @Override
            public boolean apply(TradingDecision tradingDecision) {
                return tradingDecision.shouldSellOn(tradingDate);
            }
        });
        return allDecisionsApply || sellOnLastTradingDay(tradingDate);
    }

    private boolean sellOnLastTradingDay(LocalDate tradingDate) {
        return sellAtLastDayDecision.shouldSellOn(tradingDate);
    }
}
