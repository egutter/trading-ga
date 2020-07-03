package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.ShouldCrearDecision;
import com.egutter.trading.decision.TradingDecision;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;

/**
 * Created by egutter on 2/16/14.
 */
public class BuyWhenNoOppositionsTradingDecision implements BuyTradingDecision, ShouldCrearDecision {

    private List<TradingDecision> buyTradingDecisionList = new ArrayList<TradingDecision>();

    public BuyWhenNoOppositionsTradingDecision() {
    }

    public void addBuyTradingDecision(BuyTradingDecision tradingDecision) {
        this.buyTradingDecisionList.add(tradingDecision);
    }

    @Override
    public DecisionResult shouldBuyOn(final LocalDate tradingDate) {
        TradeBasedOnConsensus tradeBasedOnConsensus = new TradeBasedOnConsensus(buyTradingDecisionList, DecisionResult.NO, DecisionResult.YES);
        return tradeBasedOnConsensus.shouldTradeOn(tradingDecision -> ((BuyTradingDecision) tradingDecision).shouldBuyOn(tradingDate));
    }

    @Override
    public String buyDecisionToString() {
        return toString();
    }

    @Override
    public LocalDate startOn() {
        Comparator<? super LocalDate> dateComparator = (date1, date2) -> date1.compareTo(date2);
        return buyTradingDecisionList.stream().map(decision -> ((BuyTradingDecision) decision).startOn()).min(dateComparator).get();
    }

    @Override
    public String toString() {
        return Joiner.on(" AND ").skipNulls().join(from(buyTradingDecisionList).transform(decisionsToString()));
    }

    private Function<TradingDecision, String> decisionsToString() {
        return new Function<TradingDecision, String>() {
            @Override
            public String apply(TradingDecision tradingDecision) {
                return ((BuyTradingDecision)tradingDecision).buyDecisionToString();
            }
        };
    }

    public List<TradingDecision> getBuyTradingDecisionList() {
        return buyTradingDecisionList;
    }

    @Override
    public void clear() {
        this.buyTradingDecisionList.stream().forEach(decision -> {
            if (ShouldCrearDecision.class.isAssignableFrom(decision.getClass())) {
                ((ShouldCrearDecision) decision).clear();
            }
        });
    }
}
