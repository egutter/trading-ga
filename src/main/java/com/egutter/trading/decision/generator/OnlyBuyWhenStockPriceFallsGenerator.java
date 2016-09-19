package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.constraint.DoNotBuyWhenSameStockInPortfolio;
import com.egutter.trading.decision.constraint.OnlyBuyWhenStockPriceFalls;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;

/**
 * Created by egutter on 5/25/14.
 */
public class OnlyBuyWhenStockPriceFallsGenerator implements BuyTradingDecisionGenerator {

    @Override
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return new OnlyBuyWhenStockPriceFalls(stockPrices);
    }
}
