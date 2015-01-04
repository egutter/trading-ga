package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.TradingDecision;
import com.google.common.base.Function;

import java.util.List;

/**
 * Created by egutter on 11/30/14.
 */
public class AlwaysNeutralConsensus extends ConsensusCondition {

    public AlwaysNeutralConsensus() {
        super(DecisionResult.NEUTRAL);
    }

    @Override
    public DecisionResult consentWith(List<TradingDecision> tradingDecisions, Function<TradingDecision, DecisionResult> shouldTrade) {
        return DecisionResult.NEUTRAL;
    }
}
