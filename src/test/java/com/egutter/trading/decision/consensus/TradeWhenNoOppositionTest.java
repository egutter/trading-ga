package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.decision.consensus.TradeBasedOnConsensus;
import com.google.common.base.Function;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TradeWhenNoOppositionTest {

    private TradingDecision neutralDecision = new BuyTradingDecision() {
        @Override
        public DecisionResult shouldBuyOn(LocalDate tradingDate) {
            return DecisionResult.NEUTRAL;
        }

        @Override
        public String buyDecisionToString() {
            return null;
        }

        @Override
        public LocalDate startOn() {
            return LocalDate.now();
        }
    };
    private TradingDecision yesDecision = new BuyTradingDecision() {
        @Override
        public DecisionResult shouldBuyOn(LocalDate tradingDate) {
            return DecisionResult.YES;
        }

        @Override
        public String buyDecisionToString() {
            return null;
        }

        @Override
        public LocalDate startOn() {
            return LocalDate.now();
        }
    };;
    private TradingDecision noDecision = new BuyTradingDecision() {
        @Override
        public DecisionResult shouldBuyOn(LocalDate tradingDate) {
            return DecisionResult.NO;
        }

        @Override
        public String buyDecisionToString() {
            return null;
        }

        @Override
        public LocalDate startOn() {
            return LocalDate.now();
        }
    };;
    private Function<TradingDecision, DecisionResult> buyFunction = new Function<TradingDecision, DecisionResult>() {
        @Override
        public DecisionResult apply(TradingDecision tradingDecision) {
            return ((BuyTradingDecision)tradingDecision).shouldBuyOn(LocalDate.now());
        }
    };

    @Test
    public void tradeWhenNoOpposition_should_not_trade_when_at_least_one_decision_is_no() throws Exception {
        List<TradingDecision> decisions = asList(neutralDecision, yesDecision, noDecision);
        TradeBasedOnConsensus tradeWhenNoOpposition = new TradeBasedOnConsensus(decisions, DecisionResult.NO, DecisionResult.YES);

        assertThat(tradeWhenNoOpposition.shouldTradeOn(buyFunction), equalTo(DecisionResult.NO));
    }

    @Test
    public void tradeWhenNoOpposition_should_trade_when_there_arent_no_decisions_and_least_one_decision_is_yes() throws Exception {
        List<TradingDecision> decisions = asList(neutralDecision, yesDecision);
        TradeBasedOnConsensus tradeWhenNoOpposition = new TradeBasedOnConsensus(decisions, DecisionResult.NO, DecisionResult.YES);

        assertThat(tradeWhenNoOpposition.shouldTradeOn(buyFunction), equalTo(DecisionResult.YES));
    }

    @Test
    public void tradeWhenNoOpposition_should_be_neutral_when_there_arent_any_neither_no_nor_yes_decisions() throws Exception {
        List<TradingDecision> decisions = asList(neutralDecision);
        TradeBasedOnConsensus tradeWhenNoOpposition = new TradeBasedOnConsensus(decisions, DecisionResult.NO, DecisionResult.YES);

        assertThat(tradeWhenNoOpposition.shouldTradeOn(buyFunction), equalTo(DecisionResult.NEUTRAL));
    }

    @Test
    public void tradeWhenYesAgreement_should_trade_when_at_least_one_decision_is_yes() throws Exception {
        List<TradingDecision> decisions = asList(neutralDecision, yesDecision, noDecision);
        TradeBasedOnConsensus tradeWhenNoOpposition = new TradeBasedOnConsensus(decisions, DecisionResult.YES, DecisionResult.NO);

        assertThat(tradeWhenNoOpposition.shouldTradeOn(buyFunction), equalTo(DecisionResult.YES));
    }

    @Test
    public void tradeWhenYesAgreement_should_not_trade_when_there_arent_yes_decisions_and_least_one_decision_is_no() throws Exception {
        List<TradingDecision> decisions = asList(neutralDecision, noDecision);
        TradeBasedOnConsensus tradeWhenNoOpposition = new TradeBasedOnConsensus(decisions, DecisionResult.YES, DecisionResult.NO);

        assertThat(tradeWhenNoOpposition.shouldTradeOn(buyFunction), equalTo(DecisionResult.NO));
    }

    @Test
    public void tradeWhenYesAgreement_should_be_neutral_when_there_arent_any_neither_no_nor_yes_decisions() throws Exception {
        List<TradingDecision> decisions = asList(neutralDecision);
        TradeBasedOnConsensus tradeWhenNoOpposition = new TradeBasedOnConsensus(decisions, DecisionResult.YES, DecisionResult.NO);

        assertThat(tradeWhenNoOpposition.shouldTradeOn(buyFunction), equalTo(DecisionResult.NEUTRAL));
    }

}