package com.egutter.trading.order;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;

import java.math.BigDecimal;

/**
 * Created by egutter on 2/12/14.
 */
public class SellOrder implements MarketOrder {

    private final String stockName;
    private final DailyQuote dailyQuote;
    private final int numberOfSharesFor;

    public SellOrder(String stockName, DailyQuote dailyQuote, int numberOfSharesFor) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.numberOfSharesFor = numberOfSharesFor;
    }

    @Override
    public void execute(Portfolio portfolio) {
        portfolio.sellStock(stockName, amountEarned(), this);
    }

    public BigDecimal amountEarned() {
        return BigDecimal.valueOf(dailyQuote.getClosePrice() * numberOfSharesFor);
    }

    public DailyQuote getDailyQuote() {
        return dailyQuote;
    }
}
