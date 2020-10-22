package com.egutter.trading.order;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.TimeFrameQuote;
import org.joda.time.LocalDate;
import org.junit.Test;

import static com.egutter.trading.helper.TestHelper.*;
import static com.egutter.trading.order.BuyConditionalOrder.EXPIRE_IN_DAYS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class BuyConditionalOrderTest {

    private Portfolio portfolio = new Portfolio(oneThousandPesos());
    private OrderBook orderBook = new OrderBook();

    @Test
    public void whenAStockIsBought_portfolioIsUpdated() {
        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(buildQuoteAt(monday()), buildQuoteAt(monday()), buildQuoteAt(monday()));
        aBuyConditionalOrder().execute(tuesday(), orderBook, portfolio, timeFrameQuote);

        assertThat(portfolio.getCash(), equalTo(zeroPesos()));
        assertThat(portfolio.getPortFolioAsset(aStockName()).getNumberOfShares(), equalTo(1000));
    }

    @Test
    public void whenAStockIsBought_sellOrdersAreAdded() {
        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(buildQuoteAt(monday()), buildQuoteAt(monday()), buildQuoteAt(monday()));
        aBuyConditionalOrder().execute(tuesday(), orderBook, portfolio, timeFrameQuote);

        assertThat(orderBook.pendingOrdersFor(aStockName()).size(), equalTo(2));
    }

    @Test
    public void expirationDate_isSet() {
        LocalDate monday = monday();
        BuyConditionalOrder bco = aBuyConditionalOrder();

        assertThat(bco.isExpired(monday.plusDays(EXPIRE_IN_DAYS + 1)), equalTo(true));
    }

    @Test
    public void expirationDate_considersWeekends() {
        LocalDate friday = new LocalDate(2020, 9, 18);
        BuyConditionalOrder bco = aBuyConditionalOrder(friday);

        assertThat(bco.isExpired(friday.plusDays(EXPIRE_IN_DAYS + 1)), equalTo(false));
    }

    private DailyQuote buildQuoteAt(LocalDate day) {
        return new DailyQuote(day, 1.0, 1.0, 1.0, 1.0, 1,1);
    }
}