package com.egutter.trading.order.condition;

import com.egutter.trading.stats.MetricsRecorder;
import com.egutter.trading.stats.MetricsRecorderFactory;
import com.egutter.trading.stock.TimeFrameQuote;
import com.google.common.collect.Range;

import java.math.BigDecimal;
import java.util.function.Function;

public class DoNotBuyWhenNextDayIsOutsideRange implements Function<TimeFrameQuote, Boolean> {

    private Range<BigDecimal> range;

    public DoNotBuyWhenNextDayIsOutsideRange(Range<BigDecimal> range) {
        this.range = range;
    }

    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        boolean withinRange = range.contains(BigDecimal.valueOf(timeFrameQuote.getQuoteAtNextDay().getOpenPrice()));
        if (withinRange) {
            MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.WITHIN_NEXT_DAY_RANGE);
        } else {
            MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.OUTSIDE_NEXT_DAY_RANGE);
        }
        return withinRange;
    }
}
