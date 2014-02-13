package com.egutter.trading.decision;

import com.egutter.trading.stock.StockPortfolio;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/12/14.
 */
public class DoNotBuyWhenSameStockInPortfolio implements TradingDecision {


    private StockPortfolio portfolio;
    private StockPrices stockPrices;
    private TradingDecision wrappedTradingDecision;

    public DoNotBuyWhenSameStockInPortfolio(StockPortfolio portfolio,
                                            StockPrices stockPrices,
                                            TradingDecision wrappedTradingDecision) {
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
    public boolean shouldSellOn(LocalDate tradingDate) {
        return wrappedTradingDecision.shouldSellOn(tradingDate);
    }
}
