package com.egutter.trading.order.condition;

import com.egutter.trading.order.SellPriceResolver;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.TimeFrameQuote;

import java.math.BigDecimal;
import java.util.function.Function;

public class SellWhenPriceAboveTarget implements Function<TimeFrameQuote, Boolean>, SellPriceResolver {

    private BigDecimal sellTargetPrice;

    public SellWhenPriceAboveTarget(BigDecimal sellTargetPrice) {
        this.sellTargetPrice = sellTargetPrice;
    }

    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        double highPrice = timeFrameQuote.getQuoteAtDay().getHighPrice();
        return targetPriceBellow(highPrice);
    }

    @Override
    public BigDecimal resolveSellPrice(TimeFrameQuote timeFrameQuote) {
        double openPrice = timeFrameQuote.getQuoteAtDay().getOpenPrice();
        if (targetPriceBellow(openPrice)) {
            return BigDecimal.valueOf(openPrice);
        }
        return this.sellTargetPrice;
    }

    private boolean targetPriceBellow(double highPrice) {
        return this.sellTargetPrice.compareTo(BigDecimal.valueOf(highPrice)) <= 0;
    }

    @Override
    public String toString() {
        return "SellWhenPriceAboveTarget{" +
                "sellTargetPrice=" + sellTargetPrice +
                '}';
    }
}
