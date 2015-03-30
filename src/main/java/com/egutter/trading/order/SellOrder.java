package com.egutter.trading.order;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by egutter on 2/12/14.
 */
public class SellOrder implements MarketOrder {

    private final String stockName;
    private final DailyQuote dailyQuote;
    private final int numberOfSharesFor;
    private final Optional<DailyQuote> marketQuote;
    private final int marketNumberOfSharesFor;

    public SellOrder(String stockName, DailyQuote dailyQuote, int numberOfSharesFor) {
        this(stockName, dailyQuote, numberOfSharesFor, Optional.empty(), 0);
    }

    public SellOrder(String stockName, DailyQuote dailyQuote, int numberOfSharesFor, Optional<DailyQuote> marketQuote, int marketNumberOfSharesFor) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.numberOfSharesFor = numberOfSharesFor;
        this.marketQuote = marketQuote;
        this.marketNumberOfSharesFor = marketNumberOfSharesFor;
    }

    @Override
    public void execute(Portfolio portfolio) {
        portfolio.sellStock(stockName, amountEarned(), this);
    }

    public BigDecimal amountEarned() {
        return BigDecimal.valueOf(dailyQuote.getAdjustedClosePrice() * numberOfSharesFor);
    }

    public BigDecimal marketAmountEarned() {
        return marketQuote.isPresent()
                ? BigDecimal.valueOf(marketQuote.get().getAdjustedClosePrice() * marketNumberOfSharesFor) :
                BigDecimal.ZERO;
    }

    public DailyQuote getDailyQuote() {
        return dailyQuote;
    }

    @Override
    public String getStockName() {
        return this.stockName;
    }

    @Override
    public int getNumberOfShares() {
        return this.numberOfSharesFor;
    }

    public LocalDate getTradingDate() {
        return dailyQuote.getTradingDate();
    }

    @Override
    public String toString() {
        return Joiner.on(" ").join("SELL", stockName, numberOfSharesFor, dailyQuote);
    }

    public static SellOrder empty() {
        return new SellOrder("N/A", DailyQuote.empty(), 0);
    }

}
