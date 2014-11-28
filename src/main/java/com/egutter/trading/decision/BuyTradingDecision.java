package com.egutter.trading.decision;

import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/12/14.
 */
public interface BuyTradingDecision extends TradingDecision {
    DecisionResult shouldBuyOn(LocalDate tradingDate);
}
