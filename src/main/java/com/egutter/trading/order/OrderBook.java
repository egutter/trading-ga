package com.egutter.trading.order;

import com.egutter.trading.stats.CandidateStats;
import com.egutter.trading.stock.Portfolio;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by egutter on 2/12/14.
 */
public class OrderBook {

    private List<MarketOrder> orders = new ArrayList<>();
    private Map<String, List<ConditionalOrder>> pendingOrders = new HashMap();

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
        newOrderBook.getPendingOrders().forEach((stock, newOrders) ->
            this.pendingOrders.merge(stock, newOrders,
                    (existingOrders, ordersToAppend) -> {
                        existingOrders.addAll(ordersToAppend);
                        return existingOrders;
                    })
                );
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

    public FluentIterable<BuyOrder> buyOrders() {
        return FluentIterable.from(orders).filter(BuyOrder.class);
    }
    public FluentIterable<SellOrder> sellOrders() {
        return FluentIterable.from(orders).filter(SellOrder.class);
    }

    public Map<String, List<ConditionalOrder>> pendingOrders() {
        return pendingOrders;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer().append("CONFIRMED ORDERS IN BOOK:").append("\n").append(orders).append("\n").append("SELL PENDING ORDERS IN BOOK: ").append("\n");
        pendingOrders.forEach((stockName, conditionalOrders) -> {
            List pendingSellOrders = conditionalOrders.stream().filter(co -> co.isSellOrder())
                    .collect(Collectors.toList());
            if (!pendingSellOrders.isEmpty()) {
                result.append(stockName).append(": ").append(pendingSellOrders);
            }
        });
        return result.toString();
    }

    public void addPendingOrder(ConditionalOrder conditionalOrder) {
        List<ConditionalOrder> conditionalOrders;
        if (this.pendingOrders.containsKey(conditionalOrder.getStockName())) {
            conditionalOrders = this.pendingOrders.get(conditionalOrder.getStockName());
        } else {
            conditionalOrders = new ArrayList<>();
            this.pendingOrders.put(conditionalOrder.getStockName(), conditionalOrders);
        }
        conditionalOrders.add(conditionalOrder);
    }

    public void removePendingOrder(ConditionalOrder conditionalOrder) {
        if (this.pendingOrders.containsKey(conditionalOrder.getStockName())) {
            List<ConditionalOrder> conditionalOrders = this.pendingOrders.get(conditionalOrder.getStockName());
            conditionalOrders.remove(conditionalOrder);
        }
    }

    public List<ConditionalOrder> pendingOrdersFor(String stockName) {
        if (this.pendingOrders.containsKey(stockName)) {
            return this.pendingOrders.get(stockName);
        }
        return new ArrayList<>();
    }

    public void forEachPendingOrder(String stockName, Consumer<ConditionalOrder> block) {
        if (this.pendingOrders.containsKey(stockName)) {
            List<ConditionalOrder> conditionalOrders = pendingOrders.get(stockName);
            for (int j = 0; j < conditionalOrders.size(); j++) {
                block.accept(conditionalOrders.get(j));
            }
        }
    }

    public Map<String, List<ConditionalOrder>> getPendingOrders() {
        return pendingOrders;
    }

    public void removeAllPendingOrdersFor(String stockName) {
        this.pendingOrders.remove(stockName);
    }
}
