package com.egutter.trading.decision;

import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/16/14.
 */
public class SellAllAtLastDay implements TradingDecision {

    private final LocalDate lastTradingDate;

    public SellAllAtLastDay(StockPrices closePrices) {
        this.lastTradingDate = closePrices.getLastTradingDate();
    }

    @Override
    public boolean shouldBuyOn(LocalDate tradingDate) {
        return false;
    }

    @Override
    public boolean shouldSellOn(LocalDate tradingDate) {
        return tradingDate.equals(lastTradingDate);
    }
}
