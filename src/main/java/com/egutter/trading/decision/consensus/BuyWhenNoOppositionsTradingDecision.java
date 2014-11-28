package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.TradingDecision;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static com.egutter.trading.decision.consensus.TradeBasedOnConsensus.tradeWhenNoOpposition;

/**
 * Created by egutter on 2/16/14.
 */
public class BuyWhenNoOppositionsTradingDecision implements BuyTradingDecision {

    private List<TradingDecision> buyTradingDecisionList = new ArrayList<TradingDecision>();

    public BuyWhenNoOppositionsTradingDecision() {
    }

    public void addBuyTradingDecision(BuyTradingDecision tradingDecision) {
        this.buyTradingDecisionList.add(tradingDecision);
    }

    @Override
    public DecisionResult shouldBuyOn(final LocalDate tradingDate) {
        return tradeWhenNoOpposition(buyTradingDecisionList).shouldTradeOn(new Function<TradingDecision, DecisionResult>() {
            @Override
            public DecisionResult apply(TradingDecision tradingDecision) {
                return ((BuyTradingDecision) tradingDecision).shouldBuyOn(tradingDate);
            }
        });
    }

    @Override
    public String toString() {
        return Joiner.on(" AND ").skipNulls().join(buyTradingDecisionList);
    }

}
