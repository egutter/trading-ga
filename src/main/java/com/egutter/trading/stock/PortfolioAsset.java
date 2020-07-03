package com.egutter.trading.stock;

import com.egutter.trading.order.BuyOrder;

public class PortfolioAsset {
    private int numberOfShares;
    private final BuyOrder buyOrder;

    public PortfolioAsset(int numberOfShares, BuyOrder order) {
        this.numberOfShares = numberOfShares;
        this.buyOrder = order;
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public BuyOrder getBuyOrder() {
        return buyOrder;
    }

    public void decreaseShares(int shares) {
        this.numberOfShares = this.numberOfShares - shares;
    }

    public boolean soldAllShares() {
        return this.numberOfShares == 0;
    }
}
