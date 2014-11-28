package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.constraint.DoNotBuyWhenSameStockInPortfolio;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.junit.Test;

import static com.egutter.trading.helper.TestHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 2/12/14.
 */
public class DoNotBuyWhenSameStockInPortfolioTest {

    @Test
    public void should_not_buy_when_stock_is_in_portfolio() throws Exception {

        Portfolio stockPorfolio = new Portfolio();
        stockPorfolio.buyStock("YPF", buyOneHundredShares().amountPaid(), buyOneHundredShares());

        DoNotBuyWhenSameStockInPortfolio decision = new DoNotBuyWhenSameStockInPortfolio(stockPorfolio,
                new StockPrices("YPF", aListOfDailyQuotes()));

        assertThat(decision.shouldBuyOn(aTradingDate()), equalTo(DecisionResult.NO));
    }

    @Test
    public void should_be_neutral_when_stock_is_in_portfolio() throws Exception {

        Portfolio stockPorfolio = new Portfolio();
        stockPorfolio.buyStock("YPF", buyOneHundredShares().amountPaid(), buyOneHundredShares());

        DoNotBuyWhenSameStockInPortfolio decision = new DoNotBuyWhenSameStockInPortfolio(stockPorfolio,
                new StockPrices("GAL", aListOfDailyQuotes()));

        assertThat(decision.shouldBuyOn(aTradingDate()), equalTo(DecisionResult.NEUTRAL));
    }
}
