package com.egutter.trading.stock;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class DailyQuoteTest {

    @Test
    public void is_on_same_date() throws Exception {
        LocalDate today = LocalDate.now();
        DailyQuote quoteOne = new DailyQuote(today,
                100, 100, 100, 100, 100, 100);

        assertThat(quoteOne.isOn(today), equalTo(true));
        assertThat(quoteOne.isOn(new LocalDate(today.getYear(), today.getMonthOfYear(), today.getDayOfMonth())), equalTo(true));
    }

    @Test
    public void is_on_different_date() throws Exception {
        DailyQuote quoteOne = new DailyQuote(LocalDate.now().minusDays(1),
                100, 100, 100, 100, 100, 100);
        LocalDate today = LocalDate.now();
        assertThat(quoteOne.isOn(today), equalTo(false));
        assertThat(quoteOne.isOn(new LocalDate(today.getYear()-1, today.getMonthOfYear(), today.getDayOfMonth())), equalTo(false));
    }
}