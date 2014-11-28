package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.constraint.DoNotBuyInTheLastBuyTradingDays;
import com.egutter.trading.stock.StockPrices;

/**
 * Created by egutter on 5/25/14.
 */
public class DoNotBuyInTheLastBuyTradingDaysGenerator implements BuyTradingDecisionGenerator {

    private SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator;

    public DoNotBuyInTheLastBuyTradingDaysGenerator(SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator) {
        this.sellAfterAFixedNumberOfDaysGenerator = sellAfterAFixedNumberOfDaysGenerator;
    }

    @Override
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return new DoNotBuyInTheLastBuyTradingDays(stockPrices, sellAfterAFixedNumberOfDaysGenerator);
    }
}
