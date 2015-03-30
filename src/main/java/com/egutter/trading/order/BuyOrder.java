package com.egutter.trading.order;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Created by egutter on 2/12/14.
 */
public class BuyOrder implements MarketOrder {
    private final String stockName;
    private final DailyQuote dailyQuote;
    private final int marketNumberOfShares;
    private Optional<DailyQuote> marketQuote;
    private final int numberOfShares;

    public BuyOrder(String stockName, DailyQuote dailyQuote, BigDecimal amountToInvest) {
        this(stockName, dailyQuote, amountToInvest, Optional.empty());
    }

    public BuyOrder(String stockName, DailyQuote dailyQuote, int numberOfShares) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.numberOfShares = numberOfShares;
        this.marketNumberOfShares = 0;
    }

    public BuyOrder(String stockName, DailyQuote dailyQuote, BigDecimal amountToInvest, Optional<DailyQuote> marketQuote) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.marketQuote = marketQuote;
        this.numberOfShares = amountToInvest.divide(BigDecimal.valueOf(dailyQuote.getAdjustedClosePrice()), RoundingMode.DOWN).intValue();
        this.marketNumberOfShares = marketQuote.isPresent()
                ? amountToInvest.divide(BigDecimal.valueOf(marketQuote.get().getAdjustedClosePrice()), RoundingMode.DOWN).intValue()
                : 0;
    }

    @Override
    public void execute(Portfolio portfolio) {
        portfolio.buyStock(stockName, amountPaid(), this);
    }

    public BigDecimal amountPaid() {
        return BigDecimal.valueOf(dailyQuote.getAdjustedClosePrice() * numberOfShares);
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }
    public int getMarketNumberOfShares() {
        return marketNumberOfShares;
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

    @Override
    public String toString() {
        return Joiner.on(" ").join("BUY", stockName, numberOfShares, dailyQuote);
    }

    public static BuyOrder empty() {
        return new BuyOrder("N/A", DailyQuote.empty(), BigDecimal.ZERO);
    }

    public BigDecimal marketAmountPaid() {
        return marketQuote.isPresent()
                ? BigDecimal.valueOf(marketQuote.get().getAdjustedClosePrice() * marketNumberOfShares)
                : BigDecimal.ZERO;
    }
}
