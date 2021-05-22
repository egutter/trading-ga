package com.egutter.trading.decision.constraint;

import com.egutter.trading.helper.TestHelper;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.TimeFrameQuote;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static com.egutter.trading.helper.TestHelper.aTradingDate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TrailingStopSellDecisionTest {

    private TrailingStopSellDecision trailingStop;
    BigDecimal stopLoss = BigDecimal.TEN;
    BigDecimal trainingLoss = BigDecimal.TEN;
    private MathContext mc = new MathContext(2, RoundingMode.HALF_EVEN);
    private BigDecimal ONE_HUNDRED = new BigDecimal(100.00, mc);

    @Test
    public void stop_loss_price_is_correctly_calculated() {

        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss);
        assertThat(trailingStop.getStopLossPrice(), equalTo(new BigDecimal(90.00, mc)));
    }

    @Test
    public void target_win_price_is_correctly_calculated() {

        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss);
        assertThat(trailingStop.getTargetWinPrice(), equalTo(new BigDecimal(110.00)));
    }

    @Test
    public void when_stock_falls_bellow_stop_loss_after_bought_return_true() {
        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss);
        TimeFrameQuote timeFrameQuote = aTimeFrameQuote(89.00, 100.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(true));
    }

    @Test
    public void when_stock_rise_above_target_price_after_bought_return_true() {
        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss, true);
        TimeFrameQuote timeFrameQuote = aTimeFrameQuote(100.00, 110.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(true));
    }

    @Test
    public void when_stock_do_not_rise_above_target_price_after_bought_return_true() {
        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss, true);
        TimeFrameQuote timeFrameQuote = aTimeFrameQuote(100.00, 109.99, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));
    }

    @Test
    public void when_stock_do_not_falls_bellow_stop_loss_after_bought_return_false() {

        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss);
        TimeFrameQuote timeFrameQuote = aTimeFrameQuote(90.00, 100.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));
    }

    @Test
    public void when_stock_goes_up_and_then_falls_bellow_stop_loss_after_bought_return_true() {

        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss);
        TimeFrameQuote timeFrameQuote = aTimeFrameQuote(90.00, 110.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));

        timeFrameQuote = aTimeFrameQuote(89.00, 100.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(true));
    }

    @Test
    public void when_stock_goes_down_and_then_rise_above_target_price_after_bought_return_true() {

        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss, true);
        TimeFrameQuote timeFrameQuote = aTimeFrameQuote(90.00, 100.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));

        timeFrameQuote = aTimeFrameQuote(100.00, 110.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(true));
    }

    @Test
    public void when_stock_reach_new_high_and_falls_bellow_trailing_loss_return_true() {

        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss);

        TimeFrameQuote timeFrameQuote = aTimeFrameQuote(100.0, 120.00, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));

        timeFrameQuote = aTimeFrameQuote(107.0, 120.00, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(true));
    }

    @Test
    public void trailing_loss_price_is_correctly_calculated() {

        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss);
        assertThat(trailingStop.getTrailingLossPrice(), equalTo(new BigDecimal(90.00, mc)));

        TimeFrameQuote timeFrameQuote = aTimeFrameQuote(100.0, 120.00, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));
        assertThat(trailingStop.getTrailingLossPrice().setScale(2), equalTo(new BigDecimal(108).setScale(2)));

        timeFrameQuote = aTimeFrameQuote(108.0, 110.00, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));
        assertThat(trailingStop.getTrailingLossPrice().setScale(2), equalTo(new BigDecimal(99).setScale(2)));
    }

    @Test
    public void when_stock_reach_new_high_and_do_not_falls_bellow_trailing_loss_return_true() {

        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss);

        TimeFrameQuote timeFrameQuote = aTimeFrameQuote(100.0, 120.00, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));

        timeFrameQuote = aTimeFrameQuote(108.0, 120.00, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));
    }

    @Test
    public void when_stock_goes_up_and_down_and_finally_falls_bellow_trailing_loss_return_true() {

        trailingStop = new TrailingStopSellDecision(ONE_HUNDRED, stopLoss, trainingLoss);

        // 100 - 99
        TimeFrameQuote timeFrameQuote = aTimeFrameQuote(99.0, 100.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));
        // 110 - 100
        timeFrameQuote = aTimeFrameQuote(90.0, 110.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));
        // 105 - 100
        timeFrameQuote = aTimeFrameQuote(99.0, 105.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));
        // 120 - 108
        timeFrameQuote = aTimeFrameQuote(95.0, 120.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(false));
        // 110 - 107 -> Sell
        timeFrameQuote = aTimeFrameQuote(107.0, 130.0, aTradingDate());
        assertThat(trailingStop.apply(timeFrameQuote), equalTo(true));
    }

    public TimeFrameQuote aTimeFrameQuote(double lowPrice, double highPrice, LocalDate tradingDate) {
        return new TimeFrameQuote(aDailyQuote(lowPrice, highPrice, tradingDate), TestHelper.aDailyQuote(), TestHelper.aDailyQuote());
    }

    public DailyQuote aDailyQuote(double lowPrice, double highPrice, LocalDate tradingDate) {
        return new DailyQuote(tradingDate, 100,
                ((highPrice - lowPrice) / 2) + lowPrice,
                ((highPrice - lowPrice) / 2) + lowPrice,
                lowPrice,
                highPrice,
                1000);
    }
}