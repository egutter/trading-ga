package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.SellAfterAFixedNumberOFDays;
import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

 /**
 * Bits
 * 0 => Active (1) or Inactive (0)
 * 1-5 => Number of days to hold stock and sell it
 * Created by egutter on 2/16/14.
 */
public class SellAfterAFixedNumberOfDaysGenerator implements TradingDecisionGenerator {

    private final int numberOfDays;
    private Portfolio portfolio;

    public SellAfterAFixedNumberOfDaysGenerator(BitString sellAfterDaysChromosome, Portfolio portfolio) {
        this.portfolio = portfolio;
        this.numberOfDays = sellAfterDaysChromosome.toNumber().intValue() + 1;
    }

    @Override
    public TradingDecision generate(StockPrices stockPrices) {
        return new SellAfterAFixedNumberOFDays(portfolio, stockPrices, numberOfDays);
    }
}
