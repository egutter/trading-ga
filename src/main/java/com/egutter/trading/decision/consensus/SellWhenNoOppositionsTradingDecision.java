package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.decision.consensus.TradeBasedOnConsensus;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static com.egutter.trading.decision.consensus.TradeBasedOnConsensus.tradeWhenNoOpposition;
import static com.google.common.collect.FluentIterable.from;

/**
 * Created by egutter on 2/16/14.
 */
public class SellWhenNoOppositionsTradingDecision implements SellTradingDecision {

    private List<TradingDecision> sellTradingDecisionList = new ArrayList<TradingDecision>();

    public void addSellTradingDecision(SellTradingDecision tradingDecision) {
        this.sellTradingDecisionList.add(tradingDecision);
    }

    @Override
    public DecisionResult shouldSellOn(final LocalDate tradingDate) {
        return tradeWhenNoOpposition(sellTradingDecisionList).shouldTradeOn(new Function<TradingDecision, DecisionResult>() {
            @Override
            public DecisionResult apply(TradingDecision tradingDecision) {
                return ((SellTradingDecision) tradingDecision).shouldSellOn(tradingDate);
            }
        });
    }

    @Override
    public String sellDecisionToString() {
        return toString();
    }

    @Override
    public String toString() {
        return Joiner.on(" AND ").skipNulls().join(from(sellTradingDecisionList).transform(decisionsToString()));
    }

    private Function<TradingDecision, String> decisionsToString() {
        return new Function<TradingDecision, String>() {
            @Override
            public String apply(TradingDecision tradingDecision) {
                return ((SellTradingDecision) tradingDecision).sellDecisionToString();
            }
        };
    }

}
