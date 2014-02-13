package com.egutter.trading.decision;

import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/12/14.
 */
public interface TradingDecision {
    boolean shouldBuyOn(LocalDate tradingDate);

    boolean shouldSellOn(LocalDate tradingDate);
}
