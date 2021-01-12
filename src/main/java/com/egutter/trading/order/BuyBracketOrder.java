package com.egutter.trading.order;

import com.egutter.trading.stock.StockGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BuyBracketOrder {
    private final BigDecimal sellTargetPrice;
    private final BigDecimal sellResistancePrice;
    private final StockGroup stockGroup;
    private final BigDecimal closePrice;
    private BigDecimal maxLoss;
    private String stockName;
    private BigDecimal expectedReturn;

    public BuyBracketOrder(String stockName, BuyOrderWithPendingSellOrders buyOrderWithPendingSellOrders, BigDecimal expectedReturn, BigDecimal maxLoss) {
        this.stockName = stockName;
        this.expectedReturn = expectedReturn;
        this.stockGroup = buyOrderWithPendingSellOrders.getStockGroup();
        this.sellTargetPrice = buyOrderWithPendingSellOrders.getSellTargetPrice().setScale(2, RoundingMode.HALF_EVEN);
        this.sellResistancePrice = buyOrderWithPendingSellOrders.getSellResistancePrice().setScale(2, RoundingMode.HALF_EVEN);
        this.maxLoss = maxLoss;
        this.closePrice = buyOrderWithPendingSellOrders.getMarketOrder().getPrice();
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
}
