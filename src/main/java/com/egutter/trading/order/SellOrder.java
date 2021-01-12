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
    private Double priceToSell;
    private DailyQuote nextdailyQuote;
    private final int numberOfSharesFor;
    private final BigDecimal price;
//    private final Optional<DailyQuote> marketQuote;
//    private final int marketNumberOfSharesFor;

    public SellOrder(String stockName, DailyQuote dailyQuote, int numberOfSharesFor) {
        this(stockName, dailyQuote, dailyQuote, numberOfSharesFor, Optional.empty(), 0);
    }

    public SellOrder(String stockName, DailyQuote dailyQuote, int numberOfSharesFor, BigDecimal price) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.numberOfSharesFor = numberOfSharesFor;
        this.price = price;
    }

    public SellOrder(String stockName, DailyQuote dailyQuote, DailyQuote nextdailyQuote, int numberOfSharesFor, Optional<DailyQuote> marketQuote, int marketNumberOfSharesFor) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.nextdailyQuote = nextdailyQuote;
        this.numberOfSharesFor = numberOfSharesFor;
        this.price = BigDecimal.ZERO;
//        this.marketQuote = marketQuote;
//        this.marketNumberOfSharesFor = marketNumberOfSharesFor;
    }

    public SellOrder(String stockName, DailyQuote dailyQuote, DailyQuote nextDayQuote, int sharesToSell, Optional<DailyQuote> marketQuote, int numberOfMarketSharesFor, Double priceToSell) {
        this(stockName, dailyQuote, nextDayQuote, sharesToSell, marketQuote, numberOfMarketSharesFor);
        this.priceToSell = priceToSell;
    }

    @Override
    public void execute(Portfolio portfolio) {
        portfolio.sellStock(stockName, amountEarned(), this);
    }

    public BigDecimal amountEarned() {
        return price.multiply(BigDecimal.valueOf(numberOfSharesFor));
    }
//
//    public BigDecimal marketAmountEarned() {
//        return marketQuote.isPresent()
//                ? BigDecimal.valueOf(marketQuote.get().getAdjustedClosePrice() * marketNumberOfSharesFor) :
//                BigDecimal.ZERO;
//    }

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
        return new SellOrder("N/A", DailyQuote.empty(), 0, BigDecimal.ZERO);
    }

    public BigDecimal getPriceSold() {
        return this.price;
    }

    @Override
    public boolean atDate(LocalDate tradingDate) {
        return dailyQuote.getTradingDate().equals(tradingDate);
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }
}
