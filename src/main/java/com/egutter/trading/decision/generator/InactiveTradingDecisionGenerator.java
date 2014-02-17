package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.InactiveTradingDecision;
import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

/**
 * Most significant bit always flags active or inactive
 * Created by egutter on 2/15/14.
 */
public class InactiveTradingDecisionGenerator implements TradingDecisionGenerator {

    private TradingDecisionGenerator wrappedTradingDecisionGenerator;
    private BitString chromosome;

    public InactiveTradingDecisionGenerator(TradingDecisionGenerator wrappedTradingDecisionGenerator, BitString chromosome) {
        this.wrappedTradingDecisionGenerator = wrappedTradingDecisionGenerator;
        this.chromosome = chromosome;
    }

    @Override
    public TradingDecision generate(StockPrices stockPrices) {
        boolean active = chromosome.getBit(chromosome.getLength() - 1);
        if (!active) {
            return new InactiveTradingDecision();
        }
        return wrappedTradingDecisionGenerator.generate(stockPrices);
    }
}
