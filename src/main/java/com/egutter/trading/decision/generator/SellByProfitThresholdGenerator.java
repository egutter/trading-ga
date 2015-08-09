package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.constraint.SellByProfitThreshold;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;

/**
 * Bits
 * 0-3 => Number of days to hold stock and sell it
 * Created by egutter on 8/2/15.
 */
public class SellByProfitThresholdGenerator implements SellTradingDecisionGenerator {

    private final BigDecimal profitThreshold;
    private Portfolio portfolio;

    public SellByProfitThresholdGenerator(BitString sellAfterDaysChromosome, Portfolio portfolio) {
        this.portfolio = portfolio;
        this.profitThreshold = BigDecimal.valueOf(10);
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return new SellByProfitThreshold(portfolio, stockPrices, profitThreshold);
    }

}
