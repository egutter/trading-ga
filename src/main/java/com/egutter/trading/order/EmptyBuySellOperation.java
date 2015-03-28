package com.egutter.trading.order;

import java.math.BigDecimal;

/**
 * Created by egutter on 1/12/15.
 */
public class EmptyBuySellOperation extends BuySellOperation {

    public EmptyBuySellOperation() {
        super(BuyOrder.empty(), SellOrder.empty());
    }

    @Override
    public BigDecimal profit() {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal profitPctg() {
        return BigDecimal.ZERO;
    }

    @Override
    public boolean isLost() {
        return false;
    }

    @Override
    public boolean isWon() {
        return false;
    }

    @Override
    public boolean isEven() {
        return false;
    }
}
