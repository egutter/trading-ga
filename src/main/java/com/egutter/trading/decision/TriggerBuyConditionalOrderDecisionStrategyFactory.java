package com.egutter.trading.decision;

import com.egutter.trading.decision.technicalanalysis.TriggerBuyConditionalOrderDecision;
import com.egutter.trading.stock.StockPrices;

public interface TriggerBuyConditionalOrderDecisionStrategyFactory {
    TriggerBuyConditionalOrderDecision generateBuyDecision(StockPrices stockPrices);
}
