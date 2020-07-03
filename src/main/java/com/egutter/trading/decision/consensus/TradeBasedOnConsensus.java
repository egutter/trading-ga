package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.TradingDecision;
import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            decisionResults.add(result);
        }
        if (decisionResults.contains(primaryDecisionResult)) {
            return getDecisionResultWithExtraInfo(decisionResults, primaryDecisionResult);
        }
        if (decisionResults.contains(secondaryDecisionResult)) {
            return getDecisionResultWithExtraInfo(decisionResults, secondaryDecisionResult);
        }
        return DecisionResult.NEUTRAL;
    }

    private DecisionResult getDecisionResultWithExtraInfo(List<DecisionResult> decisionResults, DecisionResult defaultDecisionResult) {
        Optional<DecisionResult> resultWithExtraInfo = decisionResults.stream().filter((dr) -> dr.equals(defaultDecisionResult) && dr.hasExtraInfo()).findFirst();
        return resultWithExtraInfo.orElse(defaultDecisionResult);
    }

}
