package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.InactiveTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

/**
 * Most significant bit always flags active or inactive
 * Created by egutter on 2/15/14.
 */
public class InactiveTradingDecisionGenerator implements BuyTradingDecisionGenerator, SellTradingDecisionGenerator {

    private TradingDecisionGenerator wrappedTradingDecisionGenerator;
    private BitString chromosome;

    public InactiveTradingDecisionGenerator(TradingDecisionGenerator wrappedTradingDecisionGenerator, BitString chromosome) {
        this.wrappedTradingDecisionGenerator = wrappedTradingDecisionGenerator;
        this.chromosome = chromosome;
    }

    @Override
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        boolean active = chromosome.getBit(chromosome.getLength() - 1);
        if (!active) {
            return new InactiveTradingDecision();
        }
        return ((BuyTradingDecisionGenerator) wrappedTradingDecisionGenerator).generateBuyDecision(stockPrices);
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        boolean active = chromosome.getBit(chromosome.getLength() - 1);
        if (!active) {
            return new InactiveTradingDecision();
        }
        return ((SellTradingDecisionGenerator) wrappedTradingDecisionGenerator).generateSellDecision(stockPrices);
    }
}
