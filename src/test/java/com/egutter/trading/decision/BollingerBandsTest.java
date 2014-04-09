package com.egutter.trading.decision;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Range;
import com.tictactec.ta.lib.MAType;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 2/11/14.
 */
public class BollingerBandsTest {

    @Test
    public void should_sell_when_percentage_b_is_above_threshold() {

        // GIVEN SMA for first 3 days is 20.0 AND 4th Day price is 21.0
        List<DailyQuote> dailyQuotes = Arrays.asList(dailyPrice(1, 10.0),
                dailyPrice(2, 10.0),
                dailyPrice(3, 10.0),
                dailyPrice(4, 10.0),
                dailyPrice(5, 10.0),
                dailyPrice(6, 10.0),
                dailyPrice(7, 10.0),
                dailyPrice(8, 10.0),
                dailyPrice(9, 10.0),
                dailyPrice(10, 20.0));

        // WHEN BUY THRESHOLD is
        Range<Double> buyThreshold = Range.atMost(0.1);
        Range<Double> sellThreshold = Range.atLeast(1.0);

        // AND SMA days
        int movingAverageDays = 10;

        StockPrices stockPrices = new StockPrices("YPF", dailyQuotes);
        SellTradingDecision tradingDecision = new BollingerBands(stockPrices,
                buyThreshold,
                sellThreshold,
                movingAverageDays, MAType.Sma);

        LocalDate lastTradingDate = new LocalDate(2014, 1, 10);
        assertThat(tradingDecision.shouldSellOn(lastTradingDate), equalTo(true));
    }

    @Test
    public void should_buy_when_percentage_b_is_under_threshold() {
        List<DailyQuote> dailyQuotes = Arrays.asList(dailyPrice(1, 10.0),
                dailyPrice(2, 20.0),
                dailyPrice(3, 20.0),
                dailyPrice(4, 20.0),
                dailyPrice(5, 20.0),
                dailyPrice(6, 20.0),
                dailyPrice(7, 20.0),
                dailyPrice(8, 20.0),
                dailyPrice(9, 20.0),
                dailyPrice(10, 10.0));

        // WHEN BUY THRESHOLD is
        Range<Double> buyThreshold = Range.atMost(0.1);
        Range<Double> sellThreshold = Range.atLeast(1.0);

        // AND SMA days
        int movingAverageDays = 10;

        StockPrices stockPrices = new StockPrices("YPF", dailyQuotes);
        BuyTradingDecision tradingDecision = new BollingerBands(stockPrices,
                buyThreshold,
                sellThreshold,
                movingAverageDays, MAType.Sma);

        LocalDate lastTradingDate = new LocalDate(2014, 1, 10);
        assertThat(tradingDecision.shouldBuyOn(lastTradingDate), equalTo(true));
    }

    @Test
    public void should_not_buy_nor_sell_when_percentage_b_is_between_threshold() {
        // GIVEN SMA for first 3 days is 20.0 AND 4th Day price is 21.0
        List<DailyQuote> dailyQuotes = Arrays.asList(dailyPrice(1, 10.0),
                dailyPrice(2, 20.0),
                dailyPrice(3, 20.0),
                dailyPrice(4, 20.0),
                dailyPrice(5, 20.0),
                dailyPrice(6, 20.0),
                dailyPrice(7, 20.0),
                dailyPrice(8, 20.0),
                dailyPrice(9, 20.0),
                dailyPrice(10, 21.0));


        // WHEN BUY THRESHOLD is
        Range<Double> buyThreshold = Range.atMost(0.1);
        Range<Double> sellThreshold = Range.atLeast(1.0);

        // AND SMA days
        int movingAverageDays = 10;

        StockPrices stockPrices = new StockPrices("YPF", dailyQuotes);
        TradingDecision tradingDecision = new BollingerBands(stockPrices,
                buyThreshold,
                sellThreshold,
                movingAverageDays, MAType.Sma);

        LocalDate lastTradingDate = new LocalDate(2014, 1, 10);
        assertThat(((SellTradingDecision)tradingDecision).shouldSellOn(lastTradingDate), equalTo(false));
        assertThat(((BuyTradingDecision)tradingDecision).shouldBuyOn(lastTradingDate), equalTo(false));
    }

    private DailyQuote dailyPrice(int day, double adjustedClosePrice) {
        return new DailyQuote(new LocalDate(2014, 1, day), 0,0, adjustedClosePrice, 0, 0, 0);
    }

}
