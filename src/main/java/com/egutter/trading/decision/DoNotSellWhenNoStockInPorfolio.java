package com.egutter.trading.decision;

import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/12/14.
 */
public class DoNotSellWhenNoStockInPorfolio implements SellTradingDecision {


    private Portfolio portfolio;
    private StockPrices stockPrices;
    private SellTradingDecision wrappedTradingDecision;

    public DoNotSellWhenNoStockInPorfolio(Portfolio portfolio,
                                            StockPrices stockPrices,
                                            SellTradingDecision wrappedTradingDecision) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
        this.wrappedTradingDecision = wrappedTradingDecision;
    }

    @Override
    public boolean shouldSellOn(LocalDate tradingDate) {
        if (!portfolio.hasStock(stockPrices.getStockName())) {
            return false;
        }
        return wrappedTradingDecision.shouldSellOn(tradingDate);
    }

    @Override
    public String toString() {
        return wrappedTradingDecision.toString();
    }
}
