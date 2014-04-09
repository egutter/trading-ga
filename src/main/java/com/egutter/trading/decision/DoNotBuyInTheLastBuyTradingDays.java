package com.egutter.trading.decision;

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
    private BuyTradingDecision wrappedDecision;

    public DoNotBuyInTheLastBuyTradingDays(StockPrices stockPrices,
                                           SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator,
                                           BuyTradingDecision wrappedDecision) {
        this.stockPrices = stockPrices;
        this.sellAfterAFixedNumberOfDaysGenerator = sellAfterAFixedNumberOfDaysGenerator;
        this.wrappedDecision = wrappedDecision;
    }


    @Override
    public boolean shouldBuyOn(LocalDate tradingDate) {
        return tradingDate.isBefore(getLastAvailableTradingDate()) && wrappedDecision.shouldBuyOn(tradingDate);
    }

    private LocalDate getLastAvailableTradingDate() {
        return stockPrices.getLastTradingDate().minusDays(sellAfterAFixedNumberOfDaysGenerator.getNumberOfDays());
    }

    @Override
    public String toString() {
        return wrappedDecision.toString();
    }

}
