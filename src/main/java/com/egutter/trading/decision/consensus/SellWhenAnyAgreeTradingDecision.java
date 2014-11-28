package com.egutter.trading.decision.consensus;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.TradingDecision;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static com.egutter.trading.decision.consensus.TradeBasedOnConsensus.tradeWhenYesAgreement;

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
        return tradeWhenYesAgreement(sellTradingDecisionList).shouldTradeOn(new Function<TradingDecision, DecisionResult>() {
            @Override
            public DecisionResult apply(TradingDecision tradingDecision) {
                return ((SellTradingDecision) tradingDecision).shouldSellOn(tradingDate);
            }
        });
    }

    @Override
    public String toString() {
        return Joiner.on(" OR ").skipNulls().join(sellTradingDecisionList);
    }
}
