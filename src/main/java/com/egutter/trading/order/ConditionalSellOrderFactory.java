package com.egutter.trading.order;

public interface ConditionalSellOrderFactory {
    void addSellOrdersTo(OrderBook orderBook, BuyOrder buyOrder, int shares);
}
