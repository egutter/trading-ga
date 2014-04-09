package com.egutter.trading.decision;

import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/13/14.
 */
public class InactiveTradingDecision implements BuyTradingDecision, SellTradingDecision {

    @Override
    public boolean shouldBuyOn(LocalDate tradingDate) {
        return false;
    }

    @Override
    public boolean shouldSellOn(LocalDate tradingDate) {
        return false;
    }

    @Override
    public String toString() {
        return "Inactive Trading Decision";
    }
}
