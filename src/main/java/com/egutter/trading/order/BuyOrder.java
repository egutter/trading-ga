package com.egutter.trading.order;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * Created by egutter on 2/12/14.
 */
public class BuyOrder implements MarketOrder {
    private final String stockName;
    private final DailyQuote dailyQuote;
    private final int numberOfShares;

    public BuyOrder(String stockName, DailyQuote dailyQuote, int numberOfShares) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.numberOfShares = numberOfShares;
    }

    @Override
    public void execute(Portfolio portfolio) {
        BigDecimal priceToPay = BigDecimal.valueOf(dailyQuote.getAdjustedClosePrice() * numberOfShares);

        portfolio.removeCash(priceToPay);
        portfolio.addStock(stockName, this);
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
}
