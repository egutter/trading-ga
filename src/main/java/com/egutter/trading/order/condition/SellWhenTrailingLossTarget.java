package com.egutter.trading.order.condition;

import com.egutter.trading.order.SellPriceResolver;
import com.egutter.trading.stock.TimeFrameQuote;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public class SellWhenTrailingLossTarget implements Function<TimeFrameQuote, Boolean>, SellPriceResolver {

    private final BigDecimal trailingStopPercentage;
    private BigDecimal maxHighPrice;
    private BigDecimal sellResistancePrice;

    public SellWhenTrailingLossTarget(BigDecimal pricePaid, BigDecimal priceSold) {
        this.trailingStopPercentage = pricePaid.divide(priceSold, RoundingMode.HALF_EVEN);
        this.maxHighPrice = priceSold;
        this.sellResistancePrice = trailingStopPercentage.multiply(maxHighPrice);
    }

    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        if (isOpenPriceDropUnderThreshold(sellResistancePrice, timeFrameQuote)
                || isLowPriceDropUnderThreshold(sellResistancePrice, timeFrameQuote)) {
            return true;
        }

        BigDecimal newHighPrice = BigDecimal.valueOf(timeFrameQuote.getQuoteAtDay().getHighPrice());
        if (newHighPrice.compareTo(maxHighPrice) > 0) {
            this.maxHighPrice = newHighPrice;
            this.sellResistancePrice = this.trailingStopPercentage.multiply(maxHighPrice);
        }
        return false;
    }

    @Override
    public BigDecimal resolveSellPrice(TimeFrameQuote timeFrameQuote) {
        if (isOpenPriceDropUnderThreshold(sellResistancePrice, timeFrameQuote))
            return getOpenPrice(timeFrameQuote);
        if (isLowPriceDropUnderThreshold(sellResistancePrice, timeFrameQuote))
            return sellResistancePrice;

        throw new RuntimeException("Something went wrong, cannot resolve price");
    }

    private boolean isOpenPriceDropUnderThreshold(BigDecimal sellResistancePrice, TimeFrameQuote timeFrameQuote) {
        return sellResistancePrice.compareTo(getOpenPrice(timeFrameQuote)) > 0;
    }

    private boolean isLowPriceDropUnderThreshold(BigDecimal sellResistancePrice, TimeFrameQuote timeFrameQuote) {
        return sellResistancePrice.compareTo(getLowPrice(timeFrameQuote)) > 0;
    }

    private BigDecimal getLowPrice(TimeFrameQuote timeFrameQuote) {
        return BigDecimal.valueOf(timeFrameQuote.getQuoteAtDay().getLowPrice());
    }

    private static BigDecimal getOpenPrice(TimeFrameQuote timeFrameQuote) {
        return BigDecimal.valueOf(timeFrameQuote.getQuoteAtDay().getOpenPrice());
    }
}
