package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

public class SellLastDayOfMarket implements SellTradingDecision {

    private final Portfolio portfolio;
    private final StockPrices stockPrices;

    public SellLastDayOfMarket(Portfolio portfolio, StockPrices stockPrices) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        if (!portfolio.hasStock(stockPrices.getStockName())) {
            return DecisionResult.NO;
        }
        if (tradingDate.equals(this.stockPrices.getLastTradingDate())) {
            return DecisionResult.YES;
        }
        return DecisionResult.NEUTRAL;
    }

    @Override
    public String sellDecisionToString() {
        return this.getClass().getSimpleName();
    }
}
