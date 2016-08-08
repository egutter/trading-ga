package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/12/14.
 */
public class DoNotBuyWhenSameStockInPortfolio implements BuyTradingDecision {


    private Portfolio portfolio;
    private StockPrices stockPrices;

    public DoNotBuyWhenSameStockInPortfolio(Portfolio portfolio,
                                            StockPrices stockPrices) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        if (portfolio.hasStock(stockPrices.getStockName())) {
            return DecisionResult.NO;
        }
        return DecisionResult.NEUTRAL;
    }

    @Override
    public String buyDecisionToString() {
        return null;
    }

    @Override
    public LocalDate startOn() {
        return LocalDate.now();
    }

}
