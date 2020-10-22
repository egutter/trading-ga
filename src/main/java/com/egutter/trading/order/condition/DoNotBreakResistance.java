package com.egutter.trading.order.condition;

import com.egutter.trading.stats.MetricsRecorder;
import com.egutter.trading.stats.MetricsRecorderFactory;
import com.egutter.trading.stock.TimeFrameQuote;

import java.math.BigDecimal;
import java.util.function.Function;

public class DoNotBreakResistance implements Function<TimeFrameQuote, Boolean> {

    private boolean resistanceBroken;
    private BigDecimal resistancePrice;

    public DoNotBreakResistance(BigDecimal resistancePrice) {
        this.resistancePrice = resistancePrice;
        this.resistanceBroken = false;
    }

    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        if (resistanceBroken) return false;
        this.resistanceBroken = hasBrokeResistance(timeFrameQuote);
        if (resistanceBroken) {
            MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.HAS_BROKEN_RESISTANCE);
        } else {
            MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.HAS_NOT_BROKEN_RESISTANCE);
        }
        return !this.resistanceBroken;
    }

    private boolean hasBrokeResistance(TimeFrameQuote timeFrameQuote) {
        return BigDecimal.valueOf(timeFrameQuote.getQuoteAtDay().getLowPrice()).compareTo(this.resistancePrice) < 0;
    }
}
