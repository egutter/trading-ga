package com.egutter.trading.order;

import com.egutter.trading.stock.TimeFrameQuote;

import java.math.BigDecimal;

public interface SellPriceResolver {
    BigDecimal resolveSellPrice(TimeFrameQuote timeFrameQuote);
}
