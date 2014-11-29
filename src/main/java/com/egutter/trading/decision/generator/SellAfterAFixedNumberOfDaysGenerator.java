package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.constraint.SellAfterAFixedNumberOFDays;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

/**
 * Bits
 * 0-4 => Number of days to hold stock and sell it
 * Created by egutter on 2/16/14.
 */
public class SellAfterAFixedNumberOfDaysGenerator implements SellTradingDecisionGenerator {

    private final int numberOfDays;
    private Portfolio portfolio;

    public SellAfterAFixedNumberOfDaysGenerator(BitString sellAfterDaysChromosome, Portfolio portfolio) {
        this.portfolio = portfolio;
        this.numberOfDays = sellAfterDaysChromosome.toNumber().intValue() + 5;
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return new SellAfterAFixedNumberOFDays(portfolio, stockPrices, numberOfDays);
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

}
