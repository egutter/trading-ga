package com.egutter.trading.decision;

import com.egutter.trading.stock.DailyPrice;
import com.egutter.trading.stock.StockPortfolio;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 2/12/14.
 */
public class DoNotBuyWhenSameStockInPortfolioTest {

    @Test
    public void should_not_buy_when_stock_is_in_portfolio() throws Exception {

        StockPortfolio stockPorfolio = new StockPortfolio();
        stockPorfolio.addStock("YPF", 100);

        DoNotBuyWhenSameStockInPortfolio decision = new DoNotBuyWhenSameStockInPortfolio(stockPorfolio,
                new StockPrices("YPF", getDailyPrices()),
                getTradingDecision());

        assertThat(decision.shouldBuyOn(new LocalDate(2014, 1, 1)), equalTo(false));
    }

    @Test
    public void should_buy_when_stock_is_in_portfolio() throws Exception {

        StockPortfolio stockPorfolio = new StockPortfolio();
        stockPorfolio.addStock("YPF", 100);

        DoNotBuyWhenSameStockInPortfolio decision = new DoNotBuyWhenSameStockInPortfolio(stockPorfolio,
                new StockPrices("GAL", getDailyPrices()),
                getTradingDecision());

        assertThat(decision.shouldBuyOn(new LocalDate(2014, 1, 1)), equalTo(true));
    }

    private TradingDecision getTradingDecision() {
        return new TradingDecision() {
                @Override
                public boolean shouldBuyOn(LocalDate tradingDate) {
                    return true;
                }

                @Override
                public boolean shouldSellOn(LocalDate tradingDate) {
                    return false;
                }
            };
    }

    public List<DailyPrice> getDailyPrices() {
        return Arrays.asList(new DailyPrice(new LocalDate(2014, 1, 1), 0,0, 10.0, 0, 0, 0));
    }
}
