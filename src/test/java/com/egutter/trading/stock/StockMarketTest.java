package com.egutter.trading.stock;

import org.junit.Test;

import java.util.Arrays;

import static com.egutter.trading.stock.StockMarket.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StockMarketTest {

    @Test
    public void should_be_a_merval25_stock() throws Exception {
        String[] merval25StockSymbols = merval25StockSymbols();
        Arrays.stream(merval25StockSymbols).forEach(stockName -> {
            assertThat(isMerval25(stockName), is(true));
        });
    }

    @Test
    public void should_not_be_a_merval25_stock() throws Exception {
        String[] otherStockSymbols = altStockSymbols();
        Arrays.stream(otherStockSymbols).forEach(stockName -> {
            assertThat(stockName + " is not merval25", isMerval25(stockName), is(false));
        });
    }
}