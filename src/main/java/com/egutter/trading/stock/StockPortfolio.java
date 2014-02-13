package com.egutter.trading.stock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by egutter on 2/12/14.
 */
public class StockPortfolio {

    Map<String, Integer> stocksInPortfolio = new HashMap<String, Integer>();
    BigDecimal cashInPortfolio;

    public StockPortfolio() {
        this(new BigDecimal(0.0));
    }

    public StockPortfolio(BigDecimal cashInPortfolio) {
        this.cashInPortfolio = cashInPortfolio;
    }

    public void addCash(BigDecimal cash) {
        cashInPortfolio.add(cash);
    }

    public void removeCash(BigDecimal cash) {
        cashInPortfolio.subtract(cash);
    }
    public boolean hasStock(String stockName) {
        return stocksInPortfolio.containsKey(stockName);
    }

    public void addStock(String stockName, int shares) {
        stocksInPortfolio.put(stockName, shares);
    }

    public void removeStock(String stockName) {
        stocksInPortfolio.remove(stockName);
    }

    public int getNumberOfSharesFor(String stockName) {
        if (!hasStock(stockName)) {
            return 0;
        }
        return stocksInPortfolio.get(stockName);
    }

    public BigDecimal getCash() {
        return cashInPortfolio;
    }
}
