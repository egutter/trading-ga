package com.egutter.trading.order;

import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.TimeFrameQuote;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.egutter.trading.helper.TestHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class SellConditionalOrderTest {

    private Portfolio portfolio = new Portfolio(oneThousandPesos());
    private OrderBook orderBook = new OrderBook();
    private BuyOrder buyOrder = new BuyOrder(aStockName(), aDailyQuote(), 100, BigDecimal.valueOf(5.00));

    @Before
    public void addStockToPortfolio() {
        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aDailyQuote(monday()), aDailyQuote(monday()), aDailyQuote(monday()));
        aBuyConditionalOrder().execute(tuesday(), orderBook, portfolio, timeFrameQuote);
    }

    @Test
    public void whenAStockIsSoldForAllShares_portfolioIsUpdated() {
        SellConditionalOrder sco = new SellConditionalOrder(aStockName(), buyOrder, 10, (timeFrameQuote) -> BigDecimal.valueOf(100.00));

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aDailyQuote(monday()), aDailyQuote(monday()), aDailyQuote(monday()));
        sco.execute(tuesday(), orderBook, portfolio, timeFrameQuote);

        assertThat(portfolio.getCash(), equalTo(oneThousandPesos()));
        assertThat(portfolio.hasStock(aStockName()), equalTo(false));

    }

    @Test
    public void whenAStockIsSoldForPartialShares_portfolioIsUpdated() {
        SellConditionalOrder sco = new SellConditionalOrder(aStockName(), buyOrder, 5, (timeFrameQuote) -> BigDecimal.valueOf(100.00));

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aDailyQuote(monday()), aDailyQuote(monday()), aDailyQuote(monday()));
        sco.execute(tuesday(), orderBook, portfolio, timeFrameQuote);

        assertThat(portfolio.getCash(), equalTo(BigDecimal.valueOf(500.00)));
        assertThat(portfolio.hasStock(aStockName()), equalTo(true));
        assertThat(portfolio.getNumberOfSharesFor(aStockName()), equalTo(5));

    }

    @Test
    public void whenAStockIsSoldForPartialShares_addPendingSellOrder() {
        SellConditionalOrder sco = new SellConditionalOrder(aStockName(), buyOrder, 5, (timeFrameQuote) -> BigDecimal.valueOf(100.00));

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aDailyQuote(monday()), aDailyQuote(monday()), aDailyQuote(monday()));
        sco.execute(tuesday(), orderBook, portfolio, timeFrameQuote);

        assertThat(orderBook.pendingOrdersFor(aStockName()).size(), equalTo(1));
    }

    @Test
    public void whenAStockIsSoldForAllShares_doNotAddPendingSellOrder() {
        SellConditionalOrder sco = new SellConditionalOrder(aStockName(), buyOrder, 10, (timeFrameQuote) -> BigDecimal.valueOf(100.00));

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aDailyQuote(monday()), aDailyQuote(monday()), aDailyQuote(monday()));
        sco.execute(tuesday(), orderBook, portfolio, timeFrameQuote);

        assertThat(orderBook.pendingOrdersFor(aStockName()).size(), equalTo(0));
    }
}