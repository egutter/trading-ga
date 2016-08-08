package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.TradingDecision;
import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egutter on 5/25/14.
 */
public class TradeBasedOnConsensus {

    private DecisionResult primaryDecisionResult;
    private DecisionResult secondaryDecisionResult;
    private List<TradingDecision> tradingDecisionList;

    public TradeBasedOnConsensus(List<TradingDecision> tradingDecisionList, DecisionResult primaryDecisionResult, DecisionResult secondaryDecisionResult) {
        this.tradingDecisionList = tradingDecisionList;
        this.primaryDecisionResult = primaryDecisionResult;
        this.secondaryDecisionResult = secondaryDecisionResult;
    }

    public DecisionResult shouldTradeOn(Function<TradingDecision, DecisionResult> decisionFunction) {
        List<DecisionResult> decisionResults = new ArrayList<>();
        for (TradingDecision tradingDecision: tradingDecisionList) {
            DecisionResult result = decisionFunction.apply(tradingDecision);
            if (primaryDecisionResult.equals(result)) return primaryDecisionResult;

            decisionResults.add(result);
        }
        if (decisionResults.contains(secondaryDecisionResult)) return secondaryDecisionResult;
        return DecisionResult.NEUTRAL;
    }

}
