package com.egutter.trading.order;

import com.egutter.trading.finnhub.AggregateIndicator;
import com.egutter.trading.finnhub.NewsSentiment;
import com.egutter.trading.finnhub.SupportResistance;
import com.egutter.trading.stock.StockGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringJoiner;

public class BuyBracketOrder {
    private final BigDecimal sellTargetPrice;
    private final BigDecimal sellResistancePrice;
    private final StockGroup stockGroup;
    private final BigDecimal closePrice;
    private final BigDecimal sellStopLossPercentage;
    private final BigDecimal sellTrailingLossPercentage;
    private String candidate;
    private NewsSentiment newsSentiment;
    private SupportResistance supportResistance;
    private AggregateIndicator aggregateIndicator;
    private BigDecimal maxLoss;
    private String stockName;
    private BigDecimal expectedReturn;

    public BuyBracketOrder(String stockName, BuyOrderWithPendingSellOrders buyOrderWithPendingSellOrders,
                           BigDecimal expectedReturn, BigDecimal maxLoss, String candidate,
                           NewsSentiment newsSentiment, SupportResistance supportResistance, AggregateIndicator aggregateIndicator) {
        this.stockName = stockName;
        this.expectedReturn = expectedReturn;
        this.stockGroup = buyOrderWithPendingSellOrders.getStockGroup();
        this.sellTargetPrice = buyOrderWithPendingSellOrders.getSellTargetPrice().setScale(2, RoundingMode.HALF_EVEN);
        this.sellResistancePrice = buyOrderWithPendingSellOrders.getStopLossPrice().setScale(2, RoundingMode.HALF_EVEN);
        this.sellStopLossPercentage = buyOrderWithPendingSellOrders.getStopLossPercentage().setScale(2, RoundingMode.HALF_EVEN);
        this.sellTrailingLossPercentage = buyOrderWithPendingSellOrders.getTrailingLossPercentage().setScale(2, RoundingMode.HALF_EVEN);
        this.maxLoss = maxLoss;
        this.closePrice = buyOrderWithPendingSellOrders.getMarketOrder().getPrice();
        this.candidate = candidate;
        this.newsSentiment = newsSentiment;
        this.supportResistance = supportResistance;
        this.aggregateIndicator = aggregateIndicator;
    }

    public BigDecimal getSellTargetPrice() {
        return sellTargetPrice;
    }

    public BigDecimal getExpectedReturn() {
        return expectedReturn;
    }

    public BigDecimal getMaxLoss() {
        return maxLoss;
    }

    public BigDecimal getSellResistancePrice() {
        return sellResistancePrice;
    }

    public String getStockName() {
        return stockName;
    }

    public BigDecimal getSellStopLossPercentage() {
        return sellStopLossPercentage;
    }

    public BigDecimal getSellTrailingLossPercentage() {
        return sellTrailingLossPercentage;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BuyBracketOrder.class.getSimpleName() + "[", "]")
                .add("sellTargetPrice=" + sellTargetPrice)
                .add("sellResistancePrice=" + sellResistancePrice)
                .add("stockGroup=" + stockGroup)
                .add("closePrice=" + closePrice)
                .add("sellStopLossPercentage=" + sellStopLossPercentage)
                .add("sellTrailingLossPercentage=" + sellTrailingLossPercentage)
                .add("candidate='" + candidate + "'")
                .add("newsSentiment=" + newsSentiment)
                .add("supportResistance=" + supportResistance)
                .add("aggregateIndicator=" + aggregateIndicator)
                .add("maxLoss=" + maxLoss)
                .add("stockName='" + stockName + "'")
                .add("expectedReturn=" + expectedReturn)
                .toString();
    }
}
