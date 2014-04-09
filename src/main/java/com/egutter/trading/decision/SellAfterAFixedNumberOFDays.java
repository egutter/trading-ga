package com.egutter.trading.decision;

import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/13/14.
 */
public class SellAfterAFixedNumberOFDays implements SellTradingDecision {

    private Portfolio portfolio;
    private StockPrices stockPrices;
    private int numberOfDays;

    public SellAfterAFixedNumberOFDays(Portfolio portfolio,
                                       StockPrices stockPrices,
                                       int numberOfDays) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
        this.numberOfDays = numberOfDays;
    }

    @Override
    public boolean shouldSellOn(LocalDate tradingDate) {
        return tradingDate.isAfter(dayStockWasBought().plusDays(getNumberOfDays()));
    }

    private LocalDate dayStockWasBought() {
        return portfolio.getBoughtDateForStock(stockPrices.getStockName());
    }

    private int getNumberOfDays() {
        return numberOfDays;
    }

    @Override
    public String toString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(), this.getNumberOfDays());
    }
}
