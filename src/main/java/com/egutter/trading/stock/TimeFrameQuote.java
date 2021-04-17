package com.egutter.trading.stock;

import java.math.BigDecimal;

public class TimeFrameQuote {

    private final DailyQuote quoteAtDay;
    private final DailyQuote quoteAtPreviousDay;
    private final DailyQuote quoteAtNextDay;

    public TimeFrameQuote(DailyQuote quoteAtDay, DailyQuote quoteAtPreviousDay, DailyQuote quoteAtNextDay) {
        this.quoteAtDay = quoteAtDay;
        this.quoteAtPreviousDay = quoteAtPreviousDay;
        this.quoteAtNextDay = quoteAtNextDay;
    }

    public DailyQuote getQuoteAtPreviousDay() {
        return quoteAtPreviousDay;
    }

    public DailyQuote getQuoteAtDay() {
        return quoteAtDay;
    }

    public DailyQuote getQuoteAtNextDay() {
        return quoteAtNextDay;
    }

    public boolean openNextDayAboveCloseAtDay() {
        BigDecimal openNextDay = BigDecimal.valueOf(getQuoteAtDay().getOpenPrice());
        BigDecimal closeAtDay = BigDecimal.valueOf(getQuoteAtDay().getClosePrice());
        return openNextDay.compareTo(closeAtDay) >= 0;
    }
}
