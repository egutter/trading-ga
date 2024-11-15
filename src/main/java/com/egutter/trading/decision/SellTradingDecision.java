package com.egutter.trading.decision;

import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/12/14.
 */
public interface SellTradingDecision extends TradingDecision {
    DecisionResult shouldSellOn(LocalDate tradingDate);

    String sellDecisionToString();
}
