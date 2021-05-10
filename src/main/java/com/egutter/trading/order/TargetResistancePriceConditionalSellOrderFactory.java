package com.egutter.trading.order;

import com.egutter.trading.order.condition.SellWhenPriceAboveTarget;
import com.egutter.trading.order.condition.SellWhenPriceBellowTarget;

import java.math.BigDecimal;

public class TargetResistancePriceConditionalSellOrderFactory implements ConditionalSellOrderFactory {

    private BigDecimal sellTargetPrice;
    private String stockName;
    private BigDecimal resistancePrice;

    public TargetResistancePriceConditionalSellOrderFactory(String stockName, BigDecimal sellTargetPrice, BigDecimal resistancePrice) {
        this.sellTargetPrice = sellTargetPrice;
        this.stockName = stockName;
        this.resistancePrice = resistancePrice;
    }

    @Override
    public void addSellOrdersTo(OrderBook orderBook, BuyOrder buyOrder, int shares) {
        ConditionalOrder sellWhenPriceAboveTarget = sellWhenPriceAboveTarget(shares, buyOrder);
        ConditionalOrder sellWhenPriceDropBellowStopLoss = sellWhenPriceDropBellowStopLoss(shares, buyOrder);
        orderBook.addPendingOrder(new OneCancelTheOtherOrder(sellWhenPriceAboveTarget, sellWhenPriceDropBellowStopLoss));
        orderBook.addPendingOrder(new OneCancelTheOtherOrder(sellWhenPriceDropBellowStopLoss, sellWhenPriceAboveTarget));
    }

    private ConditionalOrder sellWhenPriceAboveTarget(int shares, BuyOrder buyOrder) {
//        int sharesToSell = shares/2; // UNCOMMENT TO DO A 2ND ORDER
        int sharesToSell = shares; // COMMENT TO DO A SECOND ORDER
        SellWhenPriceAboveTarget sellWhenPriceAboveTarget = new SellWhenPriceAboveTarget(this.sellTargetPrice);
        SellConditionalOrder sellConditionalOrder = new SellConditionalOrder(this.stockName, buyOrder, sharesToSell, sellWhenPriceAboveTarget);
        sellConditionalOrder.addCondition(sellWhenPriceAboveTarget);
        return sellConditionalOrder;
    }

    private ConditionalOrder sellWhenPriceDropBellowStopLoss(int shares, BuyOrder buyOrder) {
        SellWhenPriceBellowTarget sellWhenPriceAboveTarget = new SellWhenPriceBellowTarget(this.resistancePrice);
        SellConditionalOrder sellWhenPriceBreaksResistance = new SellConditionalOrder(this.stockName, buyOrder, shares, sellWhenPriceAboveTarget);
        sellWhenPriceBreaksResistance.addCondition(sellWhenPriceAboveTarget);
        return sellWhenPriceBreaksResistance;
    }

}
