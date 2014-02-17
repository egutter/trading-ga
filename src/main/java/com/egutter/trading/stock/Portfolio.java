package com.egutter.trading.stock;

import com.egutter.trading.order.BuyOrder;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egutter on 2/12/14.
 */
public class Portfolio {

    Map<String, BuyOrder> stocks = new HashMap<String, BuyOrder>();
    BigDecimal cash;

    public Portfolio() {
        this(new BigDecimal(0.0));
    }

    public Portfolio(BigDecimal cashInPortfolio) {
        this.cash = cashInPortfolio;
    }

    public void addCash(BigDecimal cash) {
        this.cash.add(cash);
    }

    public void removeCash(BigDecimal cash) {
        this.cash.subtract(cash);
    }
    public boolean hasStock(String stockName) {
        return stocks.containsKey(stockName);
    }

    public void addStock(String stockName, BuyOrder order) {
        stocks.put(stockName, order);
    }

    public void removeStock(String stockName) {
        stocks.remove(stockName);
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
}
