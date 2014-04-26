package com.egutter.trading.decision;

import com.egutter.trading.helper.TestHelper;
import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;
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
                new StockPrices("YPF", aListOfDailyQuotes()),
                getTradingDecision());

        assertThat(decision.shouldBuyOn(aTradingDate()), equalTo(false));
    }

    @Test
    public void should_buy_when_stock_is_in_portfolio() throws Exception {

        Portfolio stockPorfolio = new Portfolio();
        stockPorfolio.buyStock("YPF", buyOneHundredShares().amountPaid(), buyOneHundredShares());

        DoNotBuyWhenSameStockInPortfolio decision = new DoNotBuyWhenSameStockInPortfolio(stockPorfolio,
                new StockPrices("GAL", aListOfDailyQuotes()),
                getTradingDecision());

        assertThat(decision.shouldBuyOn(aTradingDate()), equalTo(true));
    }

    private BuyTradingDecision getTradingDecision() {
        return new BuyTradingDecision() {
                @Override
                public boolean shouldBuyOn(LocalDate tradingDate) {
                    return true;
                }
            };
    }
}
