package com.egutter.trading.stock;

import com.google.common.base.Function;
import org.joda.time.LocalDate;

import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * Created by egutter on 2/12/14.
 */
public class StockMarket {

    private List<StockPrices> stockPrices;

    private StockPrices marketIndexPrices;

    public StockMarket(List<StockPrices> stockPrices, StockPrices marketIndexPrices) {
        this.stockPrices = stockPrices;
        this.marketIndexPrices = marketIndexPrices;
    }

    public List<LocalDate> getTradingDays() {
        return marketIndexPrices.getTradingDates();
    }

    public List<StockPrices> getStockPrices() {
        return stockPrices;
    }

    public StockPrices getMarketIndexPrices() {
        return marketIndexPrices;
    }

}
