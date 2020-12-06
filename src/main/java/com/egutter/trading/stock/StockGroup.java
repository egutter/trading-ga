package com.egutter.trading.stock;

import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.Objects;

public class StockGroup {
    private final String name;
    private final String description;
    private final BigDecimal percentageOfOrdersWon;
    private final int ordersWon;
    private final int ordersLost;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final String[] stockSymbols;

    public StockGroup(String name, String[] stockSymbols) {
        this(name, "", stockSymbols);
    }

    public StockGroup(String name, String description, String[] sectors) {
        this(name, description, sectors, BigDecimal.ZERO, 0, 0, LocalDate.now(), LocalDate.now());
    }

    public StockGroup(String name, String description, String[] stockSymbols, BigDecimal percentageOfOrdersWon, int ordersWon, int ordersLost, LocalDate fromDate, LocalDate toDate) {
        this.name = name;
        this.stockSymbols = stockSymbols;
        this.description = description;
        this.percentageOfOrdersWon = percentageOfOrdersWon;
        this.ordersWon = ordersWon;
        this.ordersLost = ordersLost;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String[] getStockSymbols() {
        return stockSymbols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockGroup that = (StockGroup) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getFullName() {
        return this.name + " " + this.description;
    }

    public BigDecimal getPercentageOfOrdersWon() {
        return percentageOfOrdersWon;
    }

    public int getOrdersWon() {
        return ordersWon;
    }

    public int getOrdersLost() {
        return ordersLost;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }
}
