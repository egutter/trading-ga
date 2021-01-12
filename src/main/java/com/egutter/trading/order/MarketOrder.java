package com.egutter.trading.order;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

/**
 * Created by egutter on 2/12/14.
 */
public interface MarketOrder {
    void execute(Portfolio portfolio);

    DailyQuote getDailyQuote();

    String getStockName();

    int getNumberOfShares();

    boolean atDate(LocalDate tradingDate);

    BigDecimal getPrice();
}
