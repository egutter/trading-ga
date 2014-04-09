package com.egutter.trading.decision;

import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/12/14.
 */
public interface SellTradingDecision {
    boolean shouldSellOn(LocalDate tradingDate);
}
