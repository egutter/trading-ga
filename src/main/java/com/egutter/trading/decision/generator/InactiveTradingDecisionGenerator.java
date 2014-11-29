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

    private final BitString chromosome;

    public InactiveTradingDecisionGenerator(BitString chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return new InactiveTradingDecision();
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return new InactiveTradingDecision();
    }
}
