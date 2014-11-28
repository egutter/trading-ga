package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.constraint.SellAfterAFixedNumberOFDays;
import com.egutter.trading.stock.Portfolio;
import org.joda.time.LocalDate;
import org.junit.Test;

import static com.egutter.trading.helper.TestHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by egutter on 2/13/14.
 */
public class SellAfterAFixedNumberOFDaysTest {

    @Test
    public void should_not_sell_before_the_given_number_of_days() throws Exception {
        Portfolio portfolio = new Portfolio();
        LocalDate purchaseDate = new LocalDate(2014, 1, 1);
        portfolio.buyStock(aStockName(), buyOneHundredSharesOn(purchaseDate).amountPaid(), buyOneHundredSharesOn(purchaseDate));

        SellAfterAFixedNumberOFDays decision = new SellAfterAFixedNumberOFDays(portfolio, aStockPrices(), 4);

        assertThat(decision.shouldSellOn(purchaseDate), equalTo(DecisionResult.NEUTRAL));
        assertThat(decision.shouldSellOn(purchaseDate.plusDays(1)), equalTo(DecisionResult.NEUTRAL));
        assertThat(decision.shouldSellOn(purchaseDate.plusDays(2)), equalTo(DecisionResult.NEUTRAL));
        assertThat(decision.shouldSellOn(purchaseDate.plusDays(3)), equalTo(DecisionResult.NEUTRAL));
        assertThat(decision.shouldSellOn(purchaseDate.plusDays(4)), equalTo(DecisionResult.NEUTRAL));
    }
    @Test
    public void should_sell_after_the_given_number_of_days() throws Exception {
        Portfolio portfolio = new Portfolio();
        LocalDate purchaseDate = new LocalDate(2014, 1, 1);
        portfolio.buyStock(aStockName(), buyOneHundredSharesOn(purchaseDate).amountPaid(), buyOneHundredSharesOn(purchaseDate));

        SellAfterAFixedNumberOFDays decision = new SellAfterAFixedNumberOFDays(portfolio, aStockPrices(), 4);

        assertThat(decision.shouldSellOn(purchaseDate.plusDays(5)), equalTo(DecisionResult.YES));
        assertThat(decision.shouldSellOn(purchaseDate.plusDays(6)), equalTo(DecisionResult.YES));
        assertThat(decision.shouldSellOn(purchaseDate.plusDays(7)), equalTo(DecisionResult.YES));
        assertThat(decision.shouldSellOn(purchaseDate.plusDays(24)), equalTo(DecisionResult.YES));
    }

}
