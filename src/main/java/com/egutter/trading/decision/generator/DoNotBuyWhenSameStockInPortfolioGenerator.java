package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.constraint.DoNotBuyWhenSameStockInPortfolio;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;

/**
 * Created by egutter on 5/25/14.
 */
public class DoNotBuyWhenSameStockInPortfolioGenerator implements BuyTradingDecisionGenerator {

    private Portfolio portfolio;

    public DoNotBuyWhenSameStockInPortfolioGenerator(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return new DoNotBuyWhenSameStockInPortfolio(portfolio, stockPrices);
    }
}
