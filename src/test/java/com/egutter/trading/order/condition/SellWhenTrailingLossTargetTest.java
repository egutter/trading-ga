package com.egutter.trading.order.condition;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.TimeFrameQuote;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.math.BigDecimal;

import static com.egutter.trading.helper.TestHelper.aDailyQuote;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SellWhenTrailingLossTargetTest {

    private BigDecimal pricePaid = BigDecimal.valueOf(90.00);
    private BigDecimal priceSold = BigDecimal.valueOf(100.00);
    private SellWhenTrailingLossTarget trailingLoss = new SellWhenTrailingLossTarget(pricePaid, priceSold);

    @Test
    public void doesNotApply_whenPriceAboveThreshold() {
        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aQuoteWithOpenLowHigh(100.00, 91.00, 110),
                aDailyQuote(), aDailyQuote());

        boolean applies = trailingLoss.apply(timeFrameQuote);
        assertThat(applies, equalTo(false));
    }

    @Test
    public void apply_whenPriceRisesAndNextTimeBellowThreshold() {

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aQuoteWithOpenLowHigh(95.00, 91.00, 110),
                aDailyQuote(), aDailyQuote());

        boolean applies = trailingLoss.apply(timeFrameQuote);
        assertThat(applies, equalTo(false));

        applies = trailingLoss.apply(timeFrameQuote);
        assertThat(applies, equalTo(true));
    }

    @Test
    public void resolvePrice_whenPriceRisesAndNextTimeBellowThreshold_returnsOpenPrice() {

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aQuoteWithOpenLowHigh(95.00, 91.00, 110),
                aDailyQuote(), aDailyQuote());

        trailingLoss.apply(timeFrameQuote);

        BigDecimal price = trailingLoss.resolveSellPrice(timeFrameQuote);
        assertThat(price, equalTo(BigDecimal.valueOf(95.00)));
    }

    @Test
    public void resolvePrice_whenPriceRisesAndNextTimeBellowThreshold_returnsThresholdPrice() {

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aQuoteWithOpenLowHigh(100.00, 91.00, 110.00),
                aDailyQuote(), aDailyQuote());

        trailingLoss.apply(timeFrameQuote);

        BigDecimal price = trailingLoss.resolveSellPrice(timeFrameQuote);
        assertThat(price, equalTo(BigDecimal.valueOf(9900, 2)));
    }

    @Test
    public void apply_whenOpenPriceBellowThreshold() {
        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aQuoteWithOpenLowHigh(89.00, 89.00, 110),
                aDailyQuote(), aDailyQuote());

        boolean applies = trailingLoss.apply(timeFrameQuote);
        assertThat(applies, equalTo(true));
    }

    @Test
    public void apply_whenLowPriceBellowThreshold() {
        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aQuoteWithOpenLowHigh(91.00, 89.00, 110),
                aDailyQuote(), aDailyQuote());

        boolean applies = trailingLoss.apply(timeFrameQuote);
        assertThat(applies, equalTo(true));
    }

    private DailyQuote aQuoteWithOpenLowHigh(double open, double low, double high) {
        DailyQuote quoteAtDay = new DailyQuote(LocalDate.now(),
                open,
                100,
                100.00,
                low,
                high,
                1000);
        ;
        return quoteAtDay;
    }
}