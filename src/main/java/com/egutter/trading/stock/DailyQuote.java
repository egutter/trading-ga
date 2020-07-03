package com.egutter.trading.stock;

import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * Created by egutter on 2/12/14.
 */
public class DailyQuote {

    private LocalDate tradingDate;
    private double openPrice;
    private double closePrice;
    private double adjustedClosePrice;
    private double lowPrice;
    private double highPrice;
    private long volume;

    public DailyQuote(LocalDate tradingDate,
                      double openPrice,
                      double closePrice,
                      double adjustedClosePrice,
                      double lowPrice,
                      double highPrice,
                      long volume) {
        this.tradingDate = tradingDate;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.adjustedClosePrice = adjustedClosePrice;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.volume = volume;
    }

    public DailyQuote(LocalDate tradingDate,
                      BigDecimal openPrice,
                      BigDecimal closePrice,
                      BigDecimal adjustedClosePrice,
                      BigDecimal lowPrice,
                      BigDecimal highPrice,
                      long volume) {
        this.tradingDate = tradingDate;
        this.openPrice = openPrice.doubleValue();
        this.closePrice = closePrice.doubleValue();
        this.adjustedClosePrice = adjustedClosePrice.doubleValue();
        this.lowPrice = lowPrice.doubleValue();
        this.highPrice = highPrice.doubleValue();
        this.volume = volume;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getAdjustedClosePrice() {
        return closePrice;
//        For stats we need to close price. Maybe
//        return adjustedClosePrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public long getVolume() {
        return volume;
    }

    public LocalDate getTradingDate() {
        return tradingDate;
    }

    @Override
    public String toString() {
        return Joiner.on("=").join(tradingDate, closePrice);
    }

    public boolean isOn(LocalDate tradingDate) {
        return this.tradingDate.equals(tradingDate);
    }

    public boolean isAfter(LocalDate tradingDate) {
        return this.tradingDate.isAfter(tradingDate);
    }

    public static DailyQuote empty() {
        return new DailyQuote(LocalDate.now(), 1, 1, 1, 1, 1, 1);
    }
    public static DailyQuote withClosePrice(double closePrice) {
        return new DailyQuote(LocalDate.now(), 1, closePrice, 1, 1, 1, 1);
    }

    public double getAverageOpenLowHighPrice() {
        return (this.openPrice * 0.5) + (this.lowPrice * 0.25) + (this.highPrice * 0.25);
    }
}
