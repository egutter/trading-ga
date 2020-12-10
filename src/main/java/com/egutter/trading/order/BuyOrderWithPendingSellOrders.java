package com.egutter.trading.order;

import java.util.ArrayList;
import java.util.List;

public class BuyOrderWithPendingSellOrders {

    private MarketOrder order;
    private List<ConditionalOrder> pendingOrders = new ArrayList<ConditionalOrder>();

    public BuyOrderWithPendingSellOrders(MarketOrder order) {
        this.order = order;
    }

    public void addSellPendingOrder(ConditionalOrder pendingOrder) {
        this.pendingOrders.add(pendingOrder);
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer().
                append("CONFIRMED ORDER:").
                append("\n").append(order).
                append("\n").
                append("SELL PENDING ORDERS: ").append("\n").
                append(pendingOrders);
        return result.toString();
    }

    public String getStockName() {
        return order.getStockName();
    }
}
