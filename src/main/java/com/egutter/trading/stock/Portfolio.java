package com.egutter.trading.stock;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.SellOrder;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egutter on 2/12/14.
 */
public class Portfolio {

    public static final double COMMISION = 0.010951;
    Map<String, BuyOrder> stocks = new HashMap<String, BuyOrder>();
    BigDecimal cash;
    private PortfolioStats stats = new PortfolioStats();

    public Portfolio() {
        this(new BigDecimal(0.0));
    }

    public Portfolio(BigDecimal cashInPortfolio) {
        this.cash = cashInPortfolio;
    }

    public boolean hasStock(String stockName) {
        return stocks.containsKey(stockName);
    }

    public int getNumberOfSharesFor(String stockName) {
        if (!hasStock(stockName)) {
            return 0;
        }
        return stocks.get(stockName).getNumberOfShares();
    }

    public BigDecimal getCash() {
        return cash;
    }

    public LocalDate getBoughtDateForStock(String stockName) {
        if (!hasStock(stockName)) {
            throw new IllegalArgumentException("Stock ["+stockName+"] not found");
        }

        return stocks.get(stockName).getTradingDate();
    }

    public void buyStock(String stockName, BigDecimal amount, BuyOrder order) {
        this.removeCash(amount.add(buyOrSellCommision(amount)));
        this.addStock(stockName, order);
    }

    public void sellStock(String stockName, BigDecimal amountEarned, SellOrder sellOrder) {
        this.addCash(amountEarned.subtract(buyOrSellCommision(amountEarned)));
        BuyOrder buyOrder = this.removeStock(stockName);
        stats.addStatsFor(buyOrder, sellOrder);

    }

    private void addStock(String stockName, BuyOrder order) {
        stocks.put(stockName, order);
    }

    private BuyOrder removeStock(String stockName) {
        return stocks.remove(stockName);
    }

    private void addCash(BigDecimal anAmount) {
        this.cash = this.cash.add(anAmount);
    }

    private void removeCash(BigDecimal anAmount) {
        this.cash = this.cash.subtract(anAmount);
    }

    public PortfolioStats getStats() {
        return stats;
    }

    private BigDecimal buyOrSellCommision(BigDecimal amount) {
        return BigDecimal.valueOf(COMMISION).multiply(amount);
    }

}
