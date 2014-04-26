package com.egutter.trading.order;

import com.egutter.trading.stock.Portfolio;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egutter on 2/12/14.
 */
public class OrderBook implements MarketOrder {

    private List<MarketOrder> orders = new ArrayList<MarketOrder>();

    @Override
    public void execute(Portfolio portfolio) {
        for (MarketOrder order: orders) {
            order.execute(portfolio);
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

    public int size() {
        return getOrders().size();
    }

    public BigDecimal amountInvested() {
        BigDecimal totalPaid = BigDecimal.ZERO;
        for (BigDecimal amountPaid : paidForEachBuyOrder()) {
            totalPaid = totalPaid.add(amountPaid);
        }
        return totalPaid;
    }

    private List<BigDecimal> paidForEachBuyOrder() {
        return buyOrders().transform(new Function<BuyOrder, BigDecimal>() {
                @Override
                public BigDecimal apply(BuyOrder buyOrder) {
                    return buyOrder.amountPaid();
                }
            }).toList();
    }

    private FluentIterable<BuyOrder> buyOrders() {
        return FluentIterable.from(orders).filter(BuyOrder.class);
    }
}
