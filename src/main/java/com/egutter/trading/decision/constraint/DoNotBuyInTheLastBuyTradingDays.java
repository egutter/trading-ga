package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.generator.SellAfterAFixedNumberOfDaysGenerator;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

/**
 * Do not buy a stock in the last trading days.
 * Trading Date must be equal or smaller to Last Trading Day minus SellAfterAFixNumberOfDays#numberOfDays
 * Created by egutter on 2/19/14.
 */
public class DoNotBuyInTheLastBuyTradingDays implements BuyTradingDecision {

    private StockPrices stockPrices;
    private SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator;

    public DoNotBuyInTheLastBuyTradingDays(StockPrices stockPrices,
                                           SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator) {
        this.stockPrices = stockPrices;
        this.sellAfterAFixedNumberOfDaysGenerator = sellAfterAFixedNumberOfDaysGenerator;
    }


    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        if (tradingDate.isBefore(getLastAvailableTradingDate())) {
            return DecisionResult.NEUTRAL;
        }
        return DecisionResult.NO;
    }


    private LocalDate getLastAvailableTradingDate() {
        return stockPrices.getLastTradingDate().minusDays(sellAfterAFixedNumberOfDaysGenerator.getNumberOfDays());
    }

    @Override
    public String buyDecisionToString() {
        return null;
    }

}
