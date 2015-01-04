package com.egutter.trading.order;

import com.google.common.base.Joiner;

import java.math.BigDecimal;

/**
 * Created by egutter on 12/31/14.
 */
public class BuySellOperation implements Comparable<BuySellOperation> {
    private BuyOrder buyOrder;
    private SellOrder sellOrder;

    public BuySellOperation(BuyOrder buyOrder, SellOrder sellOrder) {
        this.buyOrder = buyOrder;
        this.sellOrder = sellOrder;
    }

    public BuyOrder getBuyOrder() {
        return buyOrder;
    }

    public SellOrder getSellOrder() {
        return sellOrder;
    }

    public boolean isEven() {
        return getBuyOrder().amountPaid().equals(getSellOrder().amountEarned());
    }

    public BigDecimal profit() {
        BigDecimal amountEarned = sellOrder.amountEarned();
        BigDecimal amountPaid = buyOrder.amountPaid();
        return amountEarned.subtract(amountPaid);
    }

    public boolean isLost() {
        return buyOrder.amountPaid().doubleValue() > sellOrder.amountEarned().doubleValue();
    }

    public boolean isWon() {
        return buyOrder.amountPaid().doubleValue() < sellOrder.amountEarned().doubleValue();
    }

    @Override
    public int compareTo(BuySellOperation anotherOperation) {
        return this.profit().compareTo(anotherOperation.profit());
    }

    @Override
    public String toString() {
        return Joiner.on(" ").join("Buy Order:", buyOrder, "- Sell Order:", sellOrder, "- Profit:", profit());
    }

    public static BuySellOperation empty() {
        return new BuySellOperation(BuyOrder.empty(), SellOrder.empty());
    }
}
