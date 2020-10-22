package com.egutter.trading.order.condition;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.generator.TradingDecisionGenerator;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;

import java.util.function.Function;

/**
 * Created by egutter on 2/15/14.
 */
public interface ConditionalOrderConditionGenerator {

    Function<TimeFrameQuote, Boolean> generateCondition(StockPrices stockPrices);
}
