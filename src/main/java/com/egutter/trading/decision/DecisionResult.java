package com.egutter.trading.decision;

import com.egutter.trading.order.OrderExtraInfo;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by egutter on 5/25/14.
 */
public class DecisionResult {
    public final static DecisionResult YES = new DecisionResult("YES");
    public final static DecisionResult NO = new DecisionResult("NO");
    public final static DecisionResult NEUTRAL = new DecisionResult("NEUTRAL");
    private Optional<OrderExtraInfo> orderExtraInfo = Optional.empty();
    private String label;

    public DecisionResult(String label, OrderExtraInfo orderExtraInfo) {
        this.label = label;
        this.orderExtraInfo = Optional.of(orderExtraInfo);
    }

    public DecisionResult(String label) {
        this.label = label;
    }

    public static DecisionResult yesWithExtraInfo(OrderExtraInfo orderExtraInfo) {
        return new DecisionResult("YES", orderExtraInfo);
    }

    public boolean hasExtraInfo() {
        return this.orderExtraInfo.isPresent();
    }

    public Optional<OrderExtraInfo> getOrderExtraInfo() {
        return orderExtraInfo;
    }

    @Override
    public String toString() {
        String extra = orderExtraInfo.isPresent() ? orderExtraInfo.toString() : "";
        return label + " - " + orderExtraInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecisionResult that = (DecisionResult) o;
        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
