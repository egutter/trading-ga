package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

/**
 * Created by egutter on 2/15/14.
 */
public interface TradingDecisionGenerator {

    TradingDecision generate(StockPrices stockPrices);
}
