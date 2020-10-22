package com.egutter.trading.order;

import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.TimeFrameQuote;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConditionalOrderTest {

    private Portfolio portfolio = new Portfolio();
    private OrderBook orderBook = new OrderBook();
    private LocalDate tradingDate = LocalDate.now();

    ConditionalOrder co = new ConditionalOrder("AAPL") {
        private boolean pending = true;
        @Override
        protected void executeOrder(Portfolio portfolio, LocalDate tradingDate, TimeFrameQuote timeFrameQuote, OrderBook orderBook) {
            this.pending = false;
        }
        public boolean isPending() {
            return pending;
        }
    };
    ConditionalOrder co2 = new ConditionalOrder("AAPL") {
        @Override
        protected void executeOrder(Portfolio portfolio, LocalDate tradingDate, TimeFrameQuote timeFrameQuote, OrderBook orderBook) {
        }
    };

    @Test
    public void executeWhenAllconditionsAreMet() {
        co.addCondition((q) -> true);
        co.addCondition((q) -> true);
        co.addCondition((q) -> true);

        co.execute(tradingDate, orderBook, portfolio, null);
        assertThat(co.isPending(), equalTo(false));
    }

    @Test
    public void do_not_executeWhenNotAllConditionsAreMet() {
        co.addCondition((q) -> true);
        co.addCondition((q) -> false);
        co.addCondition((q) -> true);

        co.execute(tradingDate, orderBook, portfolio, null);
        assertThat(co.isPending(), equalTo(true));
    }

    @Test
    public void do_not_executeWhenNoneConditionIsMet() {
        co.addCondition((q) -> true);
        co.addCondition((q) -> false);
        co.addCondition((q) -> true);

        co.execute(tradingDate, orderBook, portfolio, null);
        assertThat(co.isPending(), equalTo(true));
    }

    @Test
    public void whenExecuted_removeAllPendingOrdersForThisStock() {
        co.addToOrderBook(orderBook);
        co2.addToOrderBook(orderBook);

        co.addCondition((q) -> true);

        co.execute(tradingDate, orderBook, portfolio, null);

        assertThat(orderBook.pendingOrdersFor(co.getStockName()).isEmpty(), equalTo(true));
    }

    @Test
    public void whenExpired_removeFromOrderBook() {

        co.addToOrderBook(orderBook);

        co.expiresOn(tradingDate.minusDays(1));
        co.execute(tradingDate, orderBook, portfolio, null);

        assertThat(orderBook.pendingOrdersFor(co.getStockName()).isEmpty(), equalTo(true));
    }

    @Test
    public void whenExpired_doNotRemoveOtherOrdersFromOrderBook() {

        co.addToOrderBook(orderBook);
        co2.addToOrderBook(orderBook);

        co.expiresOn(tradingDate.minusDays(1));
        co.execute(tradingDate, orderBook, portfolio, null);

        assertThat(orderBook.pendingOrdersFor(co.getStockName()).size(), equalTo(1));
    }

    @Test
    public void whenExpired_doNotExecute() {
        co.addCondition((q) -> true);
        co.expiresOn(tradingDate.minusDays(1));

        co.execute(tradingDate, orderBook, portfolio, null);
        assertThat(co.isPending(), equalTo(true));
    }

    @Test
    public void whenNotExecuted_AndNot_Expired_doNot_removeFromOrderBook() {

        co.addCondition((q) -> false);
        co.addToOrderBook(orderBook);

        co.expiresOn(tradingDate.plusDays(1));
        co.execute(tradingDate, orderBook, portfolio, null);

        assertThat(orderBook.pendingOrdersFor(co.getStockName()).isEmpty(), equalTo(false));
    }

    @Test
    public void whenExpirationDateNotSetAndNotExecuted_doNot_removeFromOrderBook() {
        co.addCondition((q) -> false);
        co.addToOrderBook(orderBook);

        co.execute(tradingDate, orderBook, portfolio, null);

        assertThat(orderBook.pendingOrdersFor(co.getStockName()).isEmpty(), equalTo(false));
    }
}