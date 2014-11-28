package com.egutter.trading.order;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by egutter on 2/12/14.
 */
public class BuyOrder implements MarketOrder {
    private final String stockName;
    private final DailyQuote dailyQuote;
    private final int numberOfShares;

    public BuyOrder(String stockName, DailyQuote dailyQuote, BigDecimal amountToInvest) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.numberOfShares = amountToInvest.divide(BigDecimal.valueOf(dailyQuote.getClosePrice()), RoundingMode.DOWN).intValue();
    }

    @Override
    public void execute(Portfolio portfolio) {
        portfolio.buyStock(stockName, amountPaid(), this);
    }

    public BigDecimal amountPaid() {
        return BigDecimal.valueOf(dailyQuote.getClosePrice() * numberOfShares);
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public DailyQuote getDailyQuote() {
        return dailyQuote;
    }

    public LocalDate getTradingDate() {
        return dailyQuote.getTradingDate();
    }

    public String getStockName() {
        return stockName;
    }
}
