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
    private DailyQuote nextDailyQuote;
    private Optional<DailyQuote> marketQuote;
    private Optional<OrderExtraInfo> orderExtraInfo = Optional.empty();
    private int numberOfShares;
    private final int originalNumberOfShares;
    private BigDecimal price;

    public BuyOrder(String stockName, DailyQuote dailyQuote, BigDecimal amountToInvest) {
        this(stockName, dailyQuote, dailyQuote, amountToInvest, Optional.empty(), Optional.empty());
    }

    public BuyOrder(String stockName, DailyQuote dailyQuote, int numberOfShares, BigDecimal price) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.nextDailyQuote = dailyQuote;
        this.numberOfShares = numberOfShares;
        this.originalNumberOfShares = numberOfShares;
        this.price = price;
        this.marketNumberOfShares = 0;
    }

    public BuyOrder(String stockName, DailyQuote dailyQuote, DailyQuote nextDailyQuote,  BigDecimal amountToInvest, Optional<DailyQuote> marketQuote, Optional<OrderExtraInfo> orderExtraInfo) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.nextDailyQuote = nextDailyQuote;
        this.marketQuote = marketQuote;
        this.orderExtraInfo = orderExtraInfo;
        this.numberOfShares = amountToInvest.divide(BigDecimal.valueOf(dailyQuote.getAdjustedClosePrice()), RoundingMode.DOWN).intValue();
        this.originalNumberOfShares = numberOfShares;
        this.marketNumberOfShares = marketQuote.isPresent()
                ? amountToInvest.divide(BigDecimal.valueOf(marketQuote.get().getAdjustedClosePrice()), RoundingMode.DOWN).intValue()
                : 0;
    }

    public BuyOrder(String stockName, DailyQuote dailyQuote, DailyQuote nextDailyQuote, int numberOfShares) {
        this.stockName = stockName;
        this.dailyQuote = dailyQuote;
        this.nextDailyQuote = dailyQuote;
        this.nextDailyQuote = nextDailyQuote;
        this.numberOfShares = numberOfShares;
        this.originalNumberOfShares = numberOfShares;
        this.marketNumberOfShares = 0;
    }

    @Override
    public void execute(Portfolio portfolio) {
        portfolio.buyStock(stockName, amountPaid(), this);
    }

    public BigDecimal amountPaid() {
//        if (dailyQuote.getTradingDate().equals(nextDailyQuote.getTradingDate()))
//            return BigDecimal.valueOf(dailyQuote.getAdjustedClosePrice() * numberOfShares);

//        return BigDecimal.valueOf(getPricePaid() * numberOfShares);
        return this.price.multiply(BigDecimal.valueOf(numberOfShares));
    }

    public BigDecimal getPricePaid() {
//        return nextDailyQuote.getAverageOpenLowHighPrice();
        return this.price;
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

    public DailyQuote getNextDailyQuote() {
        return nextDailyQuote;
    }

    public LocalDate getTradingDate() {
        return dailyQuote.getTradingDate();
    }

    public String getStockName() {
        return stockName;
    }

    @Override
    public String toString() {
        String orderExtraInfoString = orderExtraInfo.isPresent() ? orderExtraInfo.get().toString() : "";
        return Joiner.on(" ").join("BUY", stockName,
                "Day", dailyQuote.getTradingDate(),
                "Shares", numberOfShares,
                "Price", price,
                "Amount", amountPaid());
    }

    public static BuyOrder empty() {
        return new BuyOrder("N/A", DailyQuote.empty(), 0, BigDecimal.ZERO);
    }

    public BigDecimal marketAmountPaid() {
        return marketQuote.isPresent()
                ? BigDecimal.valueOf(marketQuote.get().getAdjustedClosePrice() * marketNumberOfShares)
                : BigDecimal.ZERO;
    }

    public boolean hasExtraInfo() {
        return this.orderExtraInfo.isPresent();
    }

    public OrderExtraInfo getOrderExtraInfo() {
        return orderExtraInfo.get();
    }

    public void updateRemaningShares(int sharesToSell) {
        this.numberOfShares = this.numberOfShares - sharesToSell;
    }

    public int getOriginalNumberOfShares() {
        return originalNumberOfShares;
    }

    public boolean hasSoldAllShares(SellOrder sellOrder) {
        return this.numberOfShares == 0 || this.originalNumberOfShares == sellOrder.getNumberOfShares();
    }

    public void setNumberOfShares(int numberOfShares) {
        this.numberOfShares = numberOfShares;
    }
}
