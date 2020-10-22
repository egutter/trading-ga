package com.egutter.trading.stock;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.SellOrder;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egutter on 2/12/14.
 */
public class Portfolio {

    public static final BigDecimal COMMISION = BigDecimal.ZERO; //BigDecimal.valueOf(0.01325071);
    private final BigDecimal initialCash;
    Map<String, PortfolioAsset> stocks = new HashMap<String, PortfolioAsset>();
    BigDecimal cash;
    private PortfolioStats stats = new PortfolioStats();

    public Portfolio() {
        this(new BigDecimal(0.0));
    }

    public static Portfolio empty() {
        return new Portfolio();
    }

    public Portfolio(BigDecimal cashInPortfolio) {
        this.initialCash = cashInPortfolio;
        this.cash = cashInPortfolio;
    }

    public boolean hasStock(String stockName) {
        return stocks.containsKey(stockName);
    }

    public int getNumberOfSharesFor(String stockName) {
        if (!hasStock(stockName)) {
            return 0;
        }
        return getPortFolioAsset(stockName).getNumberOfShares();
    }

    public PortfolioAsset getPortFolioAsset(String stockName) {
        return stocks.get(stockName);
    }

    public int getNumberOfMarketSharesFor(String stockName) {
        if (!hasStock(stockName)) {
            return 0;
        }
        return getPortFolioAsset(stockName).getBuyOrder().getMarketNumberOfShares();
    }

    public BigDecimal getCash() {
        return cash;
    }
    public BigDecimal getProfit() {
        return cash.subtract(initialCash);
    }

    public LocalDate getBoughtDateForStock(String stockName) {
        if (!hasStock(stockName)) {
            throw new IllegalArgumentException("Stock ["+stockName+"] not found");
        }

        return getPortFolioAsset(stockName).getBuyOrder().getTradingDate();
    }

    public void buyStock(String stockName, BigDecimal amount, BuyOrder order) {
        this.removeCash(amount.add(buyOrSellCommision(amount)));
        PortfolioAsset portfolioAsset = new PortfolioAsset(order.getNumberOfShares(), order);
        this.addStock(stockName, portfolioAsset);
    }

    public void sellStock(String stockName, BigDecimal amountEarned, SellOrder sellOrder) {
        this.addCash(amountEarned.subtract(buyOrSellCommision(amountEarned)));
        PortfolioAsset portfolioAsset = getPortFolioAsset(stockName);
        this.removeStock(stockName, portfolioAsset, sellOrder);
        stats.addStatsFor(portfolioAsset, sellOrder);
    }

    private void removeStock(String stockName, PortfolioAsset portfolioAsset, SellOrder sellOrder) {
        portfolioAsset.decreaseShares(sellOrder.getNumberOfShares());
        if (portfolioAsset.soldAllShares()) stocks.remove(stockName);
    }

    public boolean soldAllSharesOf(String stockName) {
        return !stocks.containsKey(stockName);
    }

    private void addStock(String stockName, PortfolioAsset portfolioAsset) {
        stocks.put(stockName, portfolioAsset);
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
        return COMMISION.multiply(amount);
    }

    public Map<String, PortfolioAsset> getStocks() {
        return stocks;
    }

    @Override
    public String toString() {
        return Joiner.on(" ").join("CASH:", cash, "STOCKS:", stocks);
    }

    public boolean isLostAbove(BigDecimal lostPercentage) {
        boolean lostAbove = this.cash.divide(initialCash).compareTo(lostPercentage) <= 0;
        if (lostAbove) System.out.println("Stopping all trader");
        return lostAbove;
    }
}
