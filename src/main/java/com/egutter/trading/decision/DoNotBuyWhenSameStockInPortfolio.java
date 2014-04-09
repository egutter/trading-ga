package com.egutter.trading.decision;

import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/12/14.
 */
public class DoNotBuyWhenSameStockInPortfolio implements BuyTradingDecision {


    private Portfolio portfolio;
    private StockPrices stockPrices;
    private BuyTradingDecision wrappedTradingDecision;

    public DoNotBuyWhenSameStockInPortfolio(Portfolio portfolio,
                                            StockPrices stockPrices,
                                            BuyTradingDecision wrappedTradingDecision) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
        this.wrappedTradingDecision = wrappedTradingDecision;
    }

    @Override
    public boolean shouldBuyOn(LocalDate tradingDate) {
        if (portfolio.hasStock(stockPrices.getStockName())) {
            return false;
        }
        return wrappedTradingDecision.shouldBuyOn(tradingDate);
    }

    @Override
    public String toString() {
        return wrappedTradingDecision.toString();
    }
}
