package com.egutter.trading.order;

import com.egutter.trading.order.condition.SellWhenTrailingLossTarget;
import com.egutter.trading.stats.MetricsRecorder;
import com.egutter.trading.stats.MetricsRecorderFactory;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.TimeFrameQuote;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

public class SellConditionalOrder extends ConditionalOrder {

    private final BuyOrder buyOrder;
    private final int shares;
    private final SellPriceResolver priceResolver;

    public SellConditionalOrder(String stockName, BuyOrder buyOrder, int shares, SellPriceResolver priceResolver) {
        super(stockName);
        if (shares < 0) throw new IllegalArgumentException("Shares can't be less than 0: " + shares);
        this.buyOrder = buyOrder;
        this.shares = shares;
        this.priceResolver = priceResolver;
    }

    @Override
    protected void executeOrder(Portfolio portfolio, LocalDate tradingDate, TimeFrameQuote timeFrameQuote, OrderBook orderBook) {

        // sell from portfolio
        portfolio.sellStock(stockName, amountEarned(timeFrameQuote), buildConfirmedOrder(timeFrameQuote));

        // if not all shares sold, add conditional sell for reminder with trailing loss
        if (!portfolio.soldAllSharesOf(this.stockName)) {
            orderBook.addPendingOrder(sellWhenTrailingLossTarget(portfolio, timeFrameQuote));
        }
        MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.SELL_EXECUTED);
    }

    private ConditionalOrder sellWhenTrailingLossTarget(Portfolio portfolio, TimeFrameQuote timeFrameQuote) {
        int outstandingShares = portfolio.getNumberOfSharesFor(this.stockName);
        SellWhenTrailingLossTarget sellWhenTrailingLossTarget = new SellWhenTrailingLossTarget(this.buyOrder.getPricePaid(), priceSold(timeFrameQuote));
        SellConditionalOrder sellConditionalOrder = new SellConditionalOrder(this.stockName, buyOrder, outstandingShares, sellWhenTrailingLossTarget);
        sellConditionalOrder.addCondition(sellWhenTrailingLossTarget);
        return sellConditionalOrder;
    }

    private SellOrder buildConfirmedOrder(TimeFrameQuote timeFrameQuote) {
        return new SellOrder(this.stockName, timeFrameQuote.getQuoteAtDay(), this.shares, this.priceSold(timeFrameQuote));
    }

    private BigDecimal amountEarned(TimeFrameQuote timeFrameQuote) {
        return priceSold(timeFrameQuote).multiply(BigDecimal.valueOf(this.shares));
    }

    private BigDecimal priceSold(TimeFrameQuote timeFrameQuote) {
        return priceResolver.resolveSellPrice(timeFrameQuote);
    }

    @Override
    public String toString() {
        return conditions.toString();
    }
}
