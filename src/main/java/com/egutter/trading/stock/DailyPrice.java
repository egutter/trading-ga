package com.egutter.trading.stock;

import org.joda.time.LocalDate;

/**
 * Created by egutter on 2/12/14.
 */
public class DailyPrice {

    private LocalDate tradingDate;
    private double openPrice;
    private double closePrice;
    private double adjustedClosePrice;
    private double lowPrice;
    private double highPrice;
    private double volume;

    public DailyPrice(LocalDate tradingDate,
                      double openPrice,
                      double closePrice,
                      double adjustedClosePrice,
                      double lowPrice,
                      double highPrice,
                      double volume) {
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

    public double getVolume() {
        return volume;
    }

    public LocalDate getTradingDate() {
        return tradingDate;
    }
}
