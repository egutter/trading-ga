package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.TradingDecision;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

import java.util.List;

import static com.google.common.collect.Iterables.any;

/**
* Created by egutter on 11/27/14.
*/
public class ConsensusCondition {

    private DecisionResult decisionResult;
    private ConsensusCondition nextCondition;

    public static ConsensusCondition isAnyNoCondition() {
        return new ConsensusCondition(DecisionResult.NO);
    }
    public static ConsensusCondition isAnyYesCondition() {
        return new ConsensusCondition(DecisionResult.YES);
    }
    public static ConsensusCondition isNeutralCondition() {
        return new ConsensusCondition(DecisionResult.NEUTRAL) {
            @Override
            public DecisionResult consentWith(List<TradingDecision> tradingDecisions, Function<TradingDecision, DecisionResult> shouldTrade) {
                return DecisionResult.NEUTRAL;
            }
        };
    }
    public ConsensusCondition(DecisionResult decisionResult) {
        this.decisionResult = decisionResult;
    }

    public DecisionResult consentWith(List<TradingDecision> tradingDecisions, Function<TradingDecision, DecisionResult> shouldTrade) {
        if (isAnyDecision(tradingDecisions, shouldTrade)) {
            return this.decisionResult;
        }
        return nextCondition.consentWith(tradingDecisions, shouldTrade);
    }

    public ConsensusCondition next(ConsensusCondition nextCondition) {
        this.nextCondition = nextCondition;
        return this;
    }

    private boolean isAnyDecision(final List<TradingDecision> tradingDecisions, final Function<TradingDecision, DecisionResult> shouldTrade) {
        return any(tradingDecisions, new Predicate<TradingDecision>() {
            @Override
            public boolean apply(TradingDecision tradingDecision) {
                return decisionResult.equals(shouldTrade.apply(tradingDecision));
            }
        });
    }
}
