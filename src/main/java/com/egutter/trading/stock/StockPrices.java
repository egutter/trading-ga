package com.egutter.trading.stock;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.joda.time.LocalDate;

import java.util.List;

import static com.google.common.collect.Iterables.getLast;
import static com.google.common.collect.Lists.transform;

/**
 * Created by egutter on 2/12/14.
 */
public class StockPrices {

    private String stockName;

    private List<DailyQuote> dailyQuotes;

    public StockPrices(String stockName, List<DailyQuote> dailyQuotes) {
        this.stockName = stockName;
        this.dailyQuotes = dailyQuotes;
    }

    public List<Double> getAdjustedClosePrices() {
        return transform(dailyQuotes, collectAdjustedClosePrice());
    }

    public List<LocalDate> getTradingDates() {
        return transform(dailyQuotes, collectTradingDates());
    }

    private Function<DailyQuote, LocalDate> collectTradingDates() {
        return new Function<DailyQuote, LocalDate>() {
            @Override
            public LocalDate apply(DailyQuote dailyQuote) {
                return dailyQuote.getTradingDate();
            }
        };
    }

    private Function<DailyQuote, Double> collectAdjustedClosePrice() {
        return new Function<DailyQuote, Double>() {
            @Override
            public Double apply(DailyQuote dailyQuote) {
                return dailyQuote.getAdjustedClosePrice();
            }
        };
    }

    public String getStockName() {
        return stockName;
    }

    public void forEachDailyPrice(Function<DailyQuote, Object> function) {
        for (DailyQuote dailyQuote : dailyQuotes) {
            function.apply(dailyQuote);
        }
    }

    public LocalDate getLastTradingDate() {
        return getLast(dailyQuotes).getTradingDate();
    }
}
