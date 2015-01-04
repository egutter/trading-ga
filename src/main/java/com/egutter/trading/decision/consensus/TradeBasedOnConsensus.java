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

    private ConsensusCondition consensusCondition;

    public TradeBasedOnConsensus(ConsensusCondition consensusCondition) {
        this.consensusCondition = consensusCondition;
    }

    public static TradeBasedOnConsensus tradeWhenNoOpposition = new TradeBasedOnConsensus(isAnyNoCondition().next(isAnyYesCondition().next(isNeutralCondition())));

    public static TradeBasedOnConsensus tradeWhenYesAgreement = new TradeBasedOnConsensus(isAnyYesCondition().next(isAnyNoCondition().next(isNeutralCondition())));

    public DecisionResult shouldTradeOn(List<TradingDecision> tradingDecisions, Function<TradingDecision, DecisionResult> shouldTrade) {
        return consensusCondition.consentWith(tradingDecisions, shouldTrade);
    }

}
