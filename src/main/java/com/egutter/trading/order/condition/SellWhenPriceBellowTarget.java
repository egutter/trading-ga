package com.egutter.trading.order.condition;

import com.egutter.trading.order.SellPriceResolver;
import com.egutter.trading.stock.TimeFrameQuote;

import java.math.BigDecimal;
import java.util.function.Function;

public class SellWhenPriceBellowTarget implements Function<TimeFrameQuote, Boolean>, SellPriceResolver {
    private BigDecimal resistancePrice;

    public SellWhenPriceBellowTarget(BigDecimal resistancePrice) {
        this.resistancePrice = resistancePrice;
    }

    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        double lowPrice = timeFrameQuote.getQuoteAtDay().getLowPrice();
        return resistancePriceAbove(lowPrice);
    }

    @Override
    public BigDecimal resolveSellPrice(TimeFrameQuote timeFrameQuote) {
        double openPrice = timeFrameQuote.getQuoteAtDay().getOpenPrice();
        if (resistancePriceAbove(openPrice)) {
            return BigDecimal.valueOf(openPrice);
        }
        return this.resistancePrice;
    }

    private boolean resistancePriceAbove(double highPrice) {
        return this.resistancePrice.compareTo(BigDecimal.valueOf(highPrice)) > 0;
    }

    @Override
    public String toString() {
        return "SellWhenPriceBellowTarget{" +
                "resistancePrice=" + resistancePrice +
                '}';
    }
}
