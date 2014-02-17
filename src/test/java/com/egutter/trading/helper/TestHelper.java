package com.egutter.trading.helper;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by egutter on 2/13/14.
 */
public class TestHelper {

    public static StockPrices aStockPrices() {
        return new StockPrices(aStockName(), aListOfDailyQuotes());
    }

    public static BuyOrder buyOneHundredShares() {
        return buyOneHundredSharesOn(aTradingDate());
    }

    public static BuyOrder buyOneHundredSharesOn(LocalDate tradingDate) {
        return new BuyOrder(aStockName(), aDailyQuote(tradingDate), 100);
    }

    public static String aStockName() {
        return "YPF";
    }

    public static DailyQuote aDailyQuote() {
        return aDailyQuote(aTradingDate());
    }

    public static DailyQuote aDailyQuote(LocalDate tradingDate) {
        return new DailyQuote(tradingDate, 100,
                200,
                200,
                50,
                300,
                1000);
    }

    public static LocalDate aTradingDate() {
        return LocalDate.now();
    }

    public static List<DailyQuote> aListOfDailyQuotes() {
        return Arrays.asList(aDailyQuote(), aDailyQuote());
    }
}
