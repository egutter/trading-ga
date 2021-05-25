package com.egutter.trading.order;

import com.egutter.trading.decision.constraint.TrailingStopSellDecision;
import com.egutter.trading.stock.StockGroup;
import com.egutter.trading.stock.TimeFrameQuote;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class BuyOrderWithPendingSellOrders {

    private final MarketOrder order;
    private transient final List<ConditionalOrder> pendingOrders = new ArrayList<ConditionalOrder>();
    private StockGroup stockGroup;
    private String candidate;

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

    public void setStockGroup(StockGroup candidateGroup) {
        this.stockGroup = candidateGroup;
    }

    public StockGroup getStockGroup() {
        return stockGroup;
    }

    public List<ConditionalOrder> getPendingOrders() {
        return pendingOrders;
    }

    public MarketOrder getMarketOrder() {
        return order;
    }

    public BigDecimal getSellTargetPrice() {
        TrailingStopSellDecision trailingStopSellDecision = (TrailingStopSellDecision) getTimeFrameQuoteBooleanFunction(TrailingStopSellDecision.class);
        return trailingStopSellDecision.getTargetWinPrice();
    }

    public BigDecimal getStopLossPrice() {
        TrailingStopSellDecision trailingStopSellDecision = (TrailingStopSellDecision) getTimeFrameQuoteBooleanFunction(TrailingStopSellDecision.class);
        return trailingStopSellDecision.getTrailingLossPrice();
    }

    public BigDecimal getStopLossPercentage() {
        TrailingStopSellDecision trailingStopSellDecision = (TrailingStopSellDecision) getTimeFrameQuoteBooleanFunction(TrailingStopSellDecision.class);
        return trailingStopSellDecision.getStopLossPercentage();
    }

    public BigDecimal getTrailingLossPercentage() {
        TrailingStopSellDecision trailingStopSellDecision = (TrailingStopSellDecision) getTimeFrameQuoteBooleanFunction(TrailingStopSellDecision.class);
        return trailingStopSellDecision.getTrainingLossPercentage();
    }

    private Function<TimeFrameQuote, Boolean> getTimeFrameQuoteBooleanFunction(Class targetClass) {
        return pendingOrders.stream().map(pendingOrder -> pendingOrder.findConditionByClass(targetClass)).filter(Optional::isPresent).findFirst().get().get();
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getCandidate() {
        return candidate;
    }
}
