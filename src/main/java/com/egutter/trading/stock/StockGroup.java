package com.egutter.trading.stock;

import java.util.Objects;

public class StockGroup {
    private final String name;
    private final String description;
    private final String[] stockSymbols;

    public StockGroup(String name, String[] stockSymbols) {
        this.name = name;
        this.stockSymbols = stockSymbols;
        this.description = "";
    }

    public StockGroup(String name, String description, String[] sectors) {
        this.name = name;
        this.stockSymbols = sectors;
        this.description = description;
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
}
