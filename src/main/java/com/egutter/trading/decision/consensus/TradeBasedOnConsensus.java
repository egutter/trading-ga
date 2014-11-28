package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.TradingDecision;
import com.google.common.base.Function;

import java.util.List;

import static com.egutter.trading.decision.consensus.ConsensusCondition.isAnyNoCondition;
import static com.egutter.trading.decision.consensus.ConsensusCondition.isAnyYesCondition;
import static com.egutter.trading.decision.consensus.ConsensusCondition.isNeutralCondition;

/**
 * Created by egutter on 5/25/14.
 */
public class TradeBasedOnConsensus {

    private List<TradingDecision> tradingDecisions;
    private ConsensusCondition consensusCondition;

    public TradeBasedOnConsensus(List<TradingDecision> tradingDecisions, ConsensusCondition consensusCondition) {
        this.tradingDecisions = tradingDecisions;
        this.consensusCondition = consensusCondition;
    }

    public static TradeBasedOnConsensus tradeWhenNoOpposition(List<TradingDecision> tradingDecisions) {
        ConsensusCondition consensusCondition = isAnyNoCondition().next(isAnyYesCondition().next(isNeutralCondition()));
        return new TradeBasedOnConsensus(tradingDecisions, consensusCondition);
    }

    public static TradeBasedOnConsensus tradeWhenYesAgreement(List<TradingDecision> tradingDecisions) {
        ConsensusCondition consensusCondition = isAnyYesCondition().next(isAnyNoCondition().next(isNeutralCondition()));
        return new TradeBasedOnConsensus(tradingDecisions, consensusCondition);
    }

    public DecisionResult shouldTradeOn(final Function<TradingDecision, DecisionResult> shouldTrade) {
        return consensusCondition.consentWith(this.tradingDecisions, shouldTrade);
    }

}
