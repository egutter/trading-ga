package com.egutter.trading.order;

import com.egutter.trading.stock.DailyPrice;
import com.egutter.trading.stock.StockPortfolio;

import java.math.BigDecimal;

/**
 * Created by egutter on 2/12/14.
 */
public class SellOrder implements MarketOrder {

    private final String stockName;
    private final DailyPrice dailyPrice;
    private final int numberOfSharesFor;

    public SellOrder(String stockName, DailyPrice dailyPrice, int numberOfSharesFor) {
        this.stockName = stockName;
        this.dailyPrice = dailyPrice;
        this.numberOfSharesFor = numberOfSharesFor;
    }

    @Override
    public void execute(StockPortfolio stockPortfolio) {
        BigDecimal priceToCollect = BigDecimal.valueOf(dailyPrice.getAdjustedClosePrice() * numberOfSharesFor);

        stockPortfolio.addCash(priceToCollect);
        stockPortfolio.removeStock(stockName);
    }

}
