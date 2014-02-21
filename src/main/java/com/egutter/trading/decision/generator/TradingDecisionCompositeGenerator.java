package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

/**
 * Created by egutter on 2/18/14.
 */
public class TradingDecisionCompositeGenerator implements TradingDecisionGenerator {

    public TradingDecisionCompositeGenerator(BitString chromosome) {

    }

    @Override
    public TradingDecision generate(StockPrices stockPrices) {
        return null;
    }
}
