package com.egutter.trading.stock;

import com.google.common.base.Function;
import org.joda.time.LocalDate;

import java.util.List;

import static com.google.common.collect.Lists.transform;

/**
 * Created by egutter on 2/12/14.
 */
public class StockPrices {

    private String stockName;

    private List<DailyPrice> dailyPrices;

    public StockPrices(String stockName, List<DailyPrice> dailyPrices) {
        this.stockName = stockName;
        this.dailyPrices = dailyPrices;
    }

    public List<Double> getAdjustedClosePrices() {
        return transform(dailyPrices, collectAdjustedClosePrice());
    }

    public List<LocalDate> getTradingDates() {
        return transform(dailyPrices, collectTradingDates());
    }

    private Function<DailyPrice, LocalDate> collectTradingDates() {
        return new Function<DailyPrice, LocalDate>() {
            @Override
            public LocalDate apply(DailyPrice dailyPrice) {
                return dailyPrice.getTradingDate();
            }
        };
    }

    private Function<DailyPrice, Double> collectAdjustedClosePrice() {
        return new Function<DailyPrice, Double>() {
            @Override
            public Double apply(DailyPrice dailyPrice) {
                return dailyPrice.getAdjustedClosePrice();
            }
        };
    }

    public String getStockName() {
        return stockName;
    }

    public List<DailyPrice> getDailyPrices() {
        return dailyPrices;
    }

    public void forEachDailyPrice(Function<DailyPrice, Object> function) {
        for (DailyPrice dailyPrice : dailyPrices) {
            function.apply(dailyPrice);
        }
    }
}
