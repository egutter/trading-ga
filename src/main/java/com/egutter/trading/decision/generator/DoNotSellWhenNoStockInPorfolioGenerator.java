package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.constraint.DoNotSellWhenNoStockInPorfolio;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;

/**
 * Created by egutter on 5/25/14.
 */
public class DoNotSellWhenNoStockInPorfolioGenerator implements SellTradingDecisionGenerator {

    private Portfolio portfolio;

    public DoNotSellWhenNoStockInPorfolioGenerator(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices);
    }
}
