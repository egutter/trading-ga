package com.egutter.trading.order;

import com.egutter.trading.stock.Portfolio;
import com.google.common.base.Joiner;
import org.joda.time.Days;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by egutter on 12/31/14.
 */
public class BuySellOperation implements Comparable<BuySellOperation> {
    private static final java.math.BigDecimal BUY_COMMISION = Portfolio.COMMISION.add(BigDecimal.ONE);
    private static final java.math.BigDecimal SELL_COMMISION = BigDecimal.ONE.subtract(Portfolio.COMMISION);
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
        return amountPaidWithCommision().equals(amountEarnedWithoutCommision());
    }

    public BigDecimal profit() {
        return amountEarnedWithoutCommision().subtract(amountPaidWithCommision());
    }

    public BigDecimal profitWithoutCommision() {
        return amountEarned().subtract(amountPaid());
    }

    public BigDecimal profitPctg() {
        MathContext mc3 = new MathContext(3, RoundingMode.HALF_EVEN);
        MathContext mc8 = new MathContext(8, RoundingMode.HALF_EVEN);
        return amountEarnedWithoutCommision().divide(amountPaidWithCommision(), mc8).subtract(BigDecimal.ONE).multiply(BigDecimal.valueOf(100), mc3);
    }

    public BigDecimal profitPctg30() {
        MathContext mc3 = new MathContext(3, RoundingMode.HALF_EVEN);
        BigDecimal tmp = profitPctg().divide(BigDecimal.valueOf(100), mc3).add(BigDecimal.ONE);
        return BigDecimal.valueOf(Math.pow(tmp.doubleValue(), 30.0 / daysBetween())).subtract(BigDecimal.ONE).multiply(BigDecimal.valueOf(100), mc3);
    }

    private int daysBetween() {
        return Days.daysBetween(buyOrder.getTradingDate(), sellOrder.getTradingDate()).getDays();
    }

    public boolean isLost() {
        return amountPaidWithCommision().compareTo(amountEarnedWithoutCommision()) > 0;
    }

    public boolean isWon() {
        return amountPaidWithCommision().compareTo(amountEarnedWithoutCommision()) < 0;
    }

    public boolean isAboveMarket() {
        return profitWithoutCommision().compareTo(marketProfit()) > 0;
    }

    private BigDecimal marketProfit() {
        return marketEarned().subtract(marketPaid());
    }

    @Override
    public int compareTo(BuySellOperation anotherOperation) {
        return this.profit().compareTo(anotherOperation.profit());
    }

    @Override
    public String toString() {
        return Joiner.on(" ").join("Buy Order:", buyOrder, "- Sell Order:", sellOrder, "- Profit: $" + profit(), "In", daysBetween() + "d", profitPctg() + "%", "In 30d", profitPctg30() + "%");
    }

    public static BuySellOperation empty() {
        return new EmptyBuySellOperation();
    }

    private BigDecimal amountPaidWithCommision() {
        return amountPaid().multiply(BUY_COMMISION);
    }

    private BigDecimal amountPaid() {
        return buyOrder.amountPaid();
    }

    private BigDecimal amountEarnedWithoutCommision() {
        return amountEarned().multiply(SELL_COMMISION);
    }

    private BigDecimal amountEarned() {
        return sellOrder.amountEarned();
    }

    private BigDecimal marketPaid() {
        return buyOrder.marketAmountPaid();
    }

    private BigDecimal marketEarned() {
        return sellOrder.marketAmountEarned();
    }

}
