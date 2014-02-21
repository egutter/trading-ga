package com.egutter.trading.decision;

import org.joda.time.LocalDate;

/**
 * Do not buy a stock in the last trading days.
 * Trading Date must be equal or smaller to Last Trading Day minus SellAfterAFixNumberOfDays#numberOfDays
 * Created by egutter on 2/19/14.
 */
public class DoNotBuyInTheLastTradingDays implements TradingDecision {

    @Override
    public boolean shouldBuyOn(LocalDate tradingDate) {
        return false;
    }

    @Override
    public boolean shouldSellOn(LocalDate tradingDate) {
        return true;
    }
}
