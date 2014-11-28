package com.egutter.trading.decision;

import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/13/14.
 */
public class InactiveTradingDecision implements BuyTradingDecision, SellTradingDecision {

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        return DecisionResult.NEUTRAL;
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        return DecisionResult.NEUTRAL;
    }

    @Override
    public String toString() {
        return null;
    }
}
