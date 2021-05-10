package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.order.ConditionalOrder;
import com.egutter.trading.stock.TimeFrameQuote;

import java.util.Optional;

public interface TriggerBuyConditionalOrderDecision {
    Optional<ConditionalOrder> generateOrder(TimeFrameQuote timeFrameQuote);

    String buyDecisionToString();
}
