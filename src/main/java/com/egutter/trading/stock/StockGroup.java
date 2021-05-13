package com.egutter.trading.stock;

import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.*;

public class StockGroup {
    private final String name;
    private final String description;
    private BigDecimal percentageOfOrdersWon = BigDecimal.ZERO;
    private Integer ordersWon = 0;
    private Integer ordersLost = 0;
    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final List<String> stockSymbols;

    public StockGroup(String name, String[] stockSymbols) {
        this(name, "", stockSymbols);
    }

    public StockGroup(String name, String description, String[] sectors) {
        this(name, description, sectors, BigDecimal.ZERO, 0, 0, LocalDate.now(), LocalDate.now());
    }

    public StockGroup(String name, String description, String[] stockSymbols, BigDecimal percentageOfOrdersWon, int ordersWon, int ordersLost, LocalDate fromDate, LocalDate toDate) {
        this.name = name;
        this.stockSymbols = Arrays.asList(stockSymbols);
        this.description = description;
        this.percentageOfOrdersWon = percentageOfOrdersWon;
        this.ordersWon = ordersWon;
        this.ordersLost = ordersLost;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public StockGroup(String name, String stockSymbol, LocalDate fromDate, LocalDate toDate) {
        this.name = name;
        this.stockSymbols = new ArrayList<>();
        this.stockSymbols.add(stockSymbol);
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.description = "";
        this.percentageOfOrdersWon = null;
        this.ordersWon = null;
        this.ordersLost = null;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String[] getStockSymbols() {
        return stockSymbols.toArray(new String[stockSymbols.size()]);
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
        return percentageOfOrdersWon == null ? BigDecimal.ZERO : percentageOfOrdersWon;
    }

    public int getOrdersWon() {
        return ordersWon == null ? 0 : ordersWon;
    }

    public int getOrdersLost() {
        return ordersLost == null ? 0 : ordersLost;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public boolean containsStockSymbol(String stockSymbol) {
        return stockSymbols.contains(stockSymbol);
    }

    public void addStockSymbol(String stockSymbol) {
        this.stockSymbols.add(stockSymbol);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StockGroup.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("percentageOfOrdersWon=" + percentageOfOrdersWon)
                .add("ordersWon=" + ordersWon)
                .add("ordersLost=" + ordersLost)
                .add("fromDate=" + fromDate)
                .add("toDate=" + toDate)
                .add("stockSymbols=" + stockSymbols)
                .toString();
    }
}
