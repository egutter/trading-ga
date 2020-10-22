package com.egutter.trading.helper;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

import java.util.Arrays;

public class TestStockPrices extends StockPrices {

    private DailyQuote lowQuote;
    private DailyQuote highQuote;

    public TestStockPrices(String stockName) {
        super(stockName, Arrays.asList(DailyQuote.empty()));
    }

    public static TestStockPrices lowDateAfterHighDate(String aStockName) {
        TestStockPrices testStockPrices = new TestStockPrices(aStockName);
        testStockPrices.setLowQuote(DailyQuote.emptyAt(today()));
        testStockPrices.setHighQuote(DailyQuote.emptyAt(yesterday()));
        return testStockPrices;
    }

    public static TestStockPrices lowDateBeforeHighDate(String aStockName) {
        TestStockPrices testStockPrices = new TestStockPrices(aStockName);
        testStockPrices.setLowQuote(DailyQuote.emptyAt(yesterday()));
        testStockPrices.setHighQuote(DailyQuote.emptyAt(today()));
        return testStockPrices;
    }

    public static StockPrices withHighOverLow(String aStockName, double priceDiff) {
        TestStockPrices testStockPrices = new TestStockPrices(aStockName);
        testStockPrices.setLowQuote(DailyQuote.withLowPriceAt(yesterday(), priceDiff));
        testStockPrices.setHighQuote(DailyQuote.withHighPriceAt(today(), priceDiff * 2));
        return testStockPrices;
    }

    private static LocalDate today() {
        return LocalDate.now();
    }

    private static LocalDate yesterday() {
        return today().minusDays(1);
    }

    public void setHighQuote(DailyQuote highQuote) {
        this.highQuote = highQuote;
    }

    public void setLowQuote(DailyQuote lowQuote) {
        this.lowQuote = lowQuote;
    }

    @Override
    public DailyQuote getLastLowFrom(LocalDate tradingDate, int lowLookback) {
        return lowQuote;
    }

    @Override
    public DailyQuote getLastHighFrom(LocalDate tradingDate, int highLookback) {
        return highQuote;
    }
}
