package com.egutter.trading.stock;

import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

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


    public double getOpenPrice() {
        return openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getAdjustedClosePrice() {
        return adjustedClosePrice;
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
        return Joiner.on(" ").join(tradingDate, closePrice);
    }

    public boolean isOn(LocalDate tradingDate) {
        return this.tradingDate.equals(tradingDate);
    }

    public static DailyQuote empty() {
        return new DailyQuote(LocalDate.now(), 1, 1, 1, 1, 1, 1);
    }
}
