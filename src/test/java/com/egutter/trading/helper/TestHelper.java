package com.egutter.trading.helper;

import com.egutter.trading.order.*;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
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
        return "AAPL";
    }

    public static BuyConditionalOrder aBuyConditionalOrder() {
        return aBuyConditionalOrder(monday());
    }

    public static BuyConditionalOrder aBuyConditionalOrder(LocalDate tradingDate) {
        ConditionalSellOrderFactory conditionalSellOrderFactory = new TargetResistancePriceConditionalSellOrderFactory(aStockName(), BigDecimal.ONE, BigDecimal.ONE);
        return new BuyConditionalOrder(aStockName(), aDailyQuote(tradingDate), oneThousandPesos(), conditionalSellOrderFactory);
    }
    public static LocalDate monday() {
        return new LocalDate(2020, 9, 14);
    }

    public static LocalDate tuesday() {
        return monday().plusDays(1);
    }

    public static BigDecimal oneThousandPesos() {
        return BigDecimal.valueOf(1000.00);
    }

    public static BigDecimal zeroPesos() {
        return BigDecimal.valueOf(0.00);
    }

    public static BigDecimal oneHundredPesos() {
        return BigDecimal.valueOf(100.00);
    }

    public static BigDecimal nineHundredPesos() {
        return BigDecimal.valueOf(900.00);
    }

    public static BigDecimal twoThousandPesos() {
        return BigDecimal.valueOf(2000.00);
    }

    public static DailyQuote aDailyQuote() {
        return aDailyQuote(aTradingDate());
    }

    public static TimeFrameQuote aTimeFrameQuote() {
        return new TimeFrameQuote(aDailyQuote(), aDailyQuote(), aDailyQuote());
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
