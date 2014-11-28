package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/12/14.
 */
public class DoNotSellWhenNoStockInPorfolio implements SellTradingDecision {


    private Portfolio portfolio;
    private StockPrices stockPrices;

    public DoNotSellWhenNoStockInPorfolio(Portfolio portfolio,
                                            StockPrices stockPrices) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        if (!portfolio.hasStock(stockPrices.getStockName())) {
            return DecisionResult.NO;
        }
        return DecisionResult.NEUTRAL;
    }

    @Override
    public String toString() {
        return null;
    }
}
