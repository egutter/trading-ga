package com.egutter.trading.order;

import com.egutter.trading.order.condition.SellWhenPriceAboveTarget;
import com.egutter.trading.order.condition.SellWhenPriceBellowTarget;
import com.egutter.trading.stats.MetricsRecorder;
import com.egutter.trading.stats.MetricsRecorderFactory;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.TimeFrameQuote;
import org.joda.time.LocalDate;

import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BuyConditionalOrder extends ConditionalOrder {
    private final DailyQuote dailyQuote;
    private final BigDecimal amountToInvest;
    private final BigDecimal sellTargetPrice;
    private final BigDecimal resistancePrice;
    public static int EXPIRE_IN_DAYS = 3;

    public BuyConditionalOrder(String stockName, DailyQuote dailyQuote, BigDecimal amountToInvest, BigDecimal sellTargetPrice, BigDecimal resistancePrice) {
        super(stockName);
        this.dailyQuote = dailyQuote;
        this.amountToInvest = amountToInvest;
        this.sellTargetPrice = sellTargetPrice;
        this.resistancePrice = resistancePrice;
        expiresOn(expirationDate(dailyQuote.getTradingDate()));
    }

    @Override
    protected void executeOrder(Portfolio portfolio, LocalDate tradingDate, TimeFrameQuote timeFrameQuote, OrderBook orderBook) {
        BigDecimal price = pricePaid(timeFrameQuote);
        int shares = sharesBought(price);
        BigDecimal amount = price.multiply(BigDecimal.valueOf(shares), new MathContext(2, RoundingMode.HALF_DOWN));

        BuyOrder buyOrder = buildConfirmedOrder(timeFrameQuote, shares, price);
        portfolio.buyStock(this.stockName, amount, buyOrder);

        // ADD Sell Orders
        ConditionalOrder sellWhenPriceAboveTarget = sellWhenPriceAboveTarget(shares, buyOrder);
        ConditionalOrder sellWhenPriceDropBellowStopLoss = sellWhenPriceDropBellowStopLoss(shares, buyOrder);
        orderBook.addPendingOrder(new OneCancelTheOtherOrder(sellWhenPriceAboveTarget, sellWhenPriceDropBellowStopLoss));
        orderBook.addPendingOrder(new OneCancelTheOtherOrder(sellWhenPriceDropBellowStopLoss, sellWhenPriceAboveTarget));

        MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.BUY_EXECUTED);
    }

    private ConditionalOrder sellWhenPriceDropBellowStopLoss(int shares, BuyOrder buyOrder) {
        SellWhenPriceBellowTarget sellWhenPriceAboveTarget = new SellWhenPriceBellowTarget(this.resistancePrice);
        SellConditionalOrder sellWhenPriceBreaksResistance = new SellConditionalOrder(this.stockName, buyOrder, shares, sellWhenPriceAboveTarget);
        sellWhenPriceBreaksResistance.addCondition(sellWhenPriceAboveTarget);
        return sellWhenPriceBreaksResistance;
    }

    private ConditionalOrder sellWhenPriceAboveTarget(int shares, BuyOrder buyOrder) {
        int halfShares = shares/2;
        SellWhenPriceAboveTarget sellWhenPriceAboveTarget = new SellWhenPriceAboveTarget(this.sellTargetPrice);
        SellConditionalOrder sellConditionalOrder = new SellConditionalOrder(this.stockName, buyOrder, halfShares, sellWhenPriceAboveTarget);
        sellConditionalOrder.addCondition(sellWhenPriceAboveTarget);
        return sellConditionalOrder;
    }

    private BuyOrder buildConfirmedOrder(TimeFrameQuote timeFrameQuote, int shares, BigDecimal price) {
        return new BuyOrder(this.stockName, timeFrameQuote.getQuoteAtDay(), shares, price);
    }

    private BigDecimal pricePaid(TimeFrameQuote timeFrameQuote) {
        return BigDecimal.valueOf(timeFrameQuote.getQuoteAtNextDay().getOpenPrice());
    }
    private int sharesBought(BigDecimal price) {
        return this.amountToInvest.divide(price, RoundingMode.HALF_EVEN).intValue();
    }

    private LocalDate expirationDate(LocalDate tradingDate) {
        int daysInAdvance = (tradingDate.getDayOfWeek() > 2) ? EXPIRE_IN_DAYS + 2 : EXPIRE_IN_DAYS;
        return tradingDate.plusDays(daysInAdvance);
    }

}
