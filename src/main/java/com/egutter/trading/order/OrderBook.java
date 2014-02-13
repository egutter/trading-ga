package com.egutter.trading.order;

import com.egutter.trading.stock.StockPortfolio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egutter on 2/12/14.
 */
public class OrderBook implements MarketOrder {

    private List<MarketOrder> orders = new ArrayList<MarketOrder>();

    @Override
    public void execute(StockPortfolio stockPortfolio) {
        for (MarketOrder order: orders) {
            order.execute(stockPortfolio);
        }
    }

    public void add(MarketOrder order) {
        orders.add(order);
    }

    public List<MarketOrder> getOrders() {
        return orders;
    }

    public void append(OrderBook newOrderBook) {
        orders.addAll(newOrderBook.getOrders());
    }
}
