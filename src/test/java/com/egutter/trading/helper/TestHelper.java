package com.egutter.trading.helper;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.SellOrder;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by egutter on 2/13/14.
 */
public class TestHelper {

    public static Portfolio aStockPortfolio() {
        return new Portfolio();
    }

    public static StockPrices aStockPrices() {
        return new StockPrices(aStockName(), aListOfDailyQuotes());
    }

    public static BuyOrder buyOneHundredShares() {
        return buyOneHundredSharesOn(aTradingDate());
    }

    public static SellOrder sellOneHundredShares() {
        return new SellOrder(aStockName(), aDailyQuote(), 100);
    }

    public static BuyOrder buyOneHundredSharesOn(LocalDate tradingDate) {
        return new BuyOrder(aStockName(), aDailyQuote(tradingDate), oneThousandPesos());
    }

    public static String aStockName() {
        return "YPF";
    }

    public static BigDecimal oneThousandPesos() {
        return BigDecimal.valueOf(1000.00);
    }

    public static BigDecimal twoThousandPesos() {
        return BigDecimal.valueOf(2000.00);
    }

    public static DailyQuote aDailyQuote() {
        return aDailyQuote(aTradingDate());
    }

    public static DailyQuote aDailyQuote(LocalDate tradingDate) {
        return aDailyQuote(200, tradingDate);
    }

    public static DailyQuote aDailyQuote(double closePrice) {
        return aDailyQuote(closePrice, aTradingDate());
    }

    public static DailyQuote aDailyQuote(double closePrice, LocalDate tradingDate) {
        return new DailyQuote(tradingDate, 100,
                closePrice,
                closePrice,
                50,
                300,
                1000);
    }

    public static LocalDate aTradingDate() {
        return LocalDate.now();
    }
    public static LocalDate aTradingDate(int days) {
        return aTradingDate().plusDays(days);
    }

    public static List<DailyQuote> aListOfDailyQuotes() {
        return Arrays.asList(aDailyQuote(), aDailyQuote());
    }

    public static BuySellOperation buySellOperation(double buyPrice, LocalDate buyDate, double sellPrice, LocalDate sellDate) {
        BuyOrder buyOrder = new BuyOrder(aStockName(), aDailyQuote(buyPrice, buyDate), oneThousandPesos());
        SellOrder sellOrder = new SellOrder(aStockName(), aDailyQuote(sellPrice, sellDate), buyOrder.getNumberOfShares());
        return new BuySellOperation(buyOrder, sellOrder);
    }

}
