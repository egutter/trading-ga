package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.TradingDecision;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;

/**
 * Created by egutter on 2/16/14.
 */
public class SellWhenAnyAgreeTradingDecision implements SellTradingDecision {

    private List<TradingDecision> sellTradingDecisionList = new ArrayList<TradingDecision>();

    public void addSellTradingDecision(SellTradingDecision tradingDecision) {
        this.sellTradingDecisionList.add(tradingDecision);
    }

    @Override
    public DecisionResult shouldSellOn(final LocalDate tradingDate) {
        TradeBasedOnConsensus tradeBasedOnConsensus = new TradeBasedOnConsensus(sellTradingDecisionList, DecisionResult.YES, DecisionResult.NO);
        return tradeBasedOnConsensus.shouldTradeOn(tradingDecision -> ((SellTradingDecision) tradingDecision).shouldSellOn(tradingDate));
    }

    @Override
    public String sellDecisionToString() {
        return toString();
    }

    @Override
    public String toString() {
        return Joiner.on(" OR ").skipNulls().join(from(sellTradingDecisionList).transform(decisionsToString()));
    }

    private Function<TradingDecision, String> decisionsToString() {
        return new Function<TradingDecision, String>() {
            @Override
            public String apply(TradingDecision tradingDecision) {
                return ((SellTradingDecision) tradingDecision).sellDecisionToString();
            }
        };
    }

    public List<TradingDecision> getSellTradingDecisionList() {
        return sellTradingDecisionList;
    }

}
