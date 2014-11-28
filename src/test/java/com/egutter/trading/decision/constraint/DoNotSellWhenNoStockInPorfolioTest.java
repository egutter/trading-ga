package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.constraint.DoNotSellWhenNoStockInPorfolio;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.junit.Test;

import static com.egutter.trading.helper.TestHelper.aListOfDailyQuotes;
import static com.egutter.trading.helper.TestHelper.aTradingDate;
import static com.egutter.trading.helper.TestHelper.buyOneHundredShares;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 2/12/14.
 */
public class DoNotSellWhenNoStockInPorfolioTest {

    @Test
    public void should_not_sell_when_no_stock_is_in_portfolio() throws Exception {

        Portfolio stockPorfolio = new Portfolio();
        stockPorfolio.buyStock("YPF", buyOneHundredShares().amountPaid(), buyOneHundredShares());

        DoNotSellWhenNoStockInPorfolio decision = new DoNotSellWhenNoStockInPorfolio(stockPorfolio,
                new StockPrices("GAL", aListOfDailyQuotes()));

        assertThat(decision.shouldSellOn(aTradingDate()), equalTo(DecisionResult.NO));
    }

    @Test
    public void should_be_neutral_when_stock_is_in_portfolio() throws Exception {

        Portfolio stockPorfolio = new Portfolio();
        stockPorfolio.buyStock("YPF", buyOneHundredShares().amountPaid(), buyOneHundredShares());

        DoNotSellWhenNoStockInPorfolio decision = new DoNotSellWhenNoStockInPorfolio(stockPorfolio,
                new StockPrices("YPF", aListOfDailyQuotes()));

        assertThat(decision.shouldSellOn(aTradingDate()), equalTo(DecisionResult.NEUTRAL));
    }

}