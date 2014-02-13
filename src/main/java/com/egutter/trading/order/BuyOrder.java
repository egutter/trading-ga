package com.egutter.trading.order;

import com.egutter.trading.stock.DailyPrice;
import com.egutter.trading.stock.StockPortfolio;

import java.math.BigDecimal;

/**
 * Created by egutter on 2/12/14.
 */
public class BuyOrder implements MarketOrder {
    private final String stockName;
    private final DailyPrice dailyPrice;
    private final int numberOfShares;

    public BuyOrder(String stockName, DailyPrice dailyPrice, int numberOfShares) {
        this.stockName = stockName;
        this.dailyPrice = dailyPrice;
        this.numberOfShares = numberOfShares;
    }

    @Override
    public void execute(StockPortfolio stockPortfolio) {
        BigDecimal priceToPay = BigDecimal.valueOf(dailyPrice.getAdjustedClosePrice() * numberOfShares);

        stockPortfolio.removeCash(priceToPay);
        stockPortfolio.addStock(stockName, numberOfShares);
    }
}
