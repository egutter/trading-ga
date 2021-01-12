package com.egutter.trading.order;

import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.TimeFrameQuote;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class ConditionalOrder {

    List<Function<TimeFrameQuote, Boolean>> conditions = new ArrayList<>();
    protected Optional<LocalDate> expirationDate = Optional.empty();
    protected String stockName;

    public ConditionalOrder(String stockName) {
        this.stockName = stockName;
    }

    public boolean execute(LocalDate tradingDate, OrderBook orderBook, Portfolio portfolio, TimeFrameQuote timeFrameQuote) {
        if (isExpired(tradingDate)) {
            removeFromOrderBook(orderBook);
            return false;
        }
        if (allConditionsAreMet(timeFrameQuote)) {
            removeAllFromOrderBook(orderBook);
            executeOrder(portfolio, tradingDate, timeFrameQuote, orderBook);
            return true;
        }
        return false;
    }

    private void removeAllFromOrderBook(OrderBook orderBook) {
        orderBook.removeAllPendingOrdersFor(this.getStockName());
    }

    private void removeFromOrderBook(OrderBook orderBook) {
        orderBook.removePendingOrder(this);
    }

    public boolean isExpired(LocalDate tradingDate) {
        return (this.expirationDate.isPresent()
                && tradingDate.isAfter(this.expirationDate.get()));
    }

    public void addToOrderBook(OrderBook orderBook) {
        orderBook.addPendingOrder(this);
    }

    public void addCondition(Function<TimeFrameQuote, Boolean> condition) {
        getConditions().add(condition);
    }
    private boolean allConditionsAreMet(TimeFrameQuote timeFrameQuote) {
        return getConditions().stream().allMatch(e -> e.apply(timeFrameQuote));
    }

    protected abstract void executeOrder(Portfolio portfolio, LocalDate tradingDate, TimeFrameQuote timeFrameQuote, OrderBook orderBook);

    public boolean isPending() {
        return true;
    }

    public void expiresOn(LocalDate expirationDate) {
        this.expirationDate = Optional.of(expirationDate);
    }

    public String getStockName() {
        return stockName;
    }

    public boolean isSellOrder() {
        return false;
    }

    public Optional<Function<TimeFrameQuote, Boolean>> findConditionByClass(Class targetClass) {
        return getConditions().stream().filter(condition -> targetClass.equals(condition.getClass())).findFirst();
    }

    protected List<Function<TimeFrameQuote, Boolean>> getConditions() {
        return conditions;
    }
}
