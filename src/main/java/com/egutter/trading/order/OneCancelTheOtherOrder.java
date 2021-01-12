package com.egutter.trading.order;

import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.TimeFrameQuote;
import org.joda.time.LocalDate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class OneCancelTheOtherOrder extends ConditionalOrder {

    private final ConditionalOrder primaryOrder;
    private final ConditionalOrder secondaryOrder;
    private final Map<String, Method> methods = new HashMap<>();

    public OneCancelTheOtherOrder(ConditionalOrder primaryOrder, ConditionalOrder secondaryOrder) {
        super(primaryOrder.getStockName());
        this.primaryOrder = primaryOrder;
        this.secondaryOrder = secondaryOrder;
        for(Method method: this.primaryOrder.getClass().getDeclaredMethods()) {
            this.methods.put(method.getName(), method);
        }
    }

    @Override
    public boolean execute(LocalDate tradingDate, OrderBook orderBook, Portfolio portfolio, TimeFrameQuote timeFrameQuote) {
        boolean executed = this.primaryOrder.execute(tradingDate, orderBook, portfolio, timeFrameQuote);
        if (executed) {
            cancelOther();
        }
        return executed;
    }

    @Override
    public void addCondition(Function<TimeFrameQuote, Boolean> condition) {
        this.primaryOrder.addCondition(condition);
    }

    @Override
    protected void executeOrder(Portfolio portfolio, LocalDate tradingDate, TimeFrameQuote timeFrameQuote, OrderBook orderBook) {
        throw new RuntimeException("Should never call here");
    }

    private void cancelOther() {
        this.secondaryOrder.addCondition(neverExecuteCondition());
    }

    private Function<TimeFrameQuote, Boolean> neverExecuteCondition() {
        return (timeFrameQuote -> false);
    }

    @Override
    public String toString() {
        return primaryOrder.toString();
    }

    public boolean isSellOrder() {
        return this.primaryOrder.isSellOrder();
    }

    protected List<Function<TimeFrameQuote, Boolean>> getConditions() {
        return primaryOrder.getConditions();
    }
}
