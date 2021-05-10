package com.egutter.trading.order;

import com.egutter.trading.decision.constraint.TrailingStopSellDecision;
import com.egutter.trading.decision.generator.TrailingStopSellDecisionGenerator;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;

import java.math.BigDecimal;
import java.util.function.Function;

public class StopTrailingLossConditionalSellOrderFactory implements ConditionalSellOrderFactory {

    private String stockName;
    private StockPrices stockPrices;
    private TrailingStopSellDecisionGenerator stopTrailingLossGenerator;

    public StopTrailingLossConditionalSellOrderFactory(String stockName, StockPrices stockPrices, TrailingStopSellDecisionGenerator stopTrailingLossGenerator) {
        this.stockName = stockName;
        this.stockPrices = stockPrices;
        this.stopTrailingLossGenerator = stopTrailingLossGenerator;
    }

    @Override
    public void addSellOrdersTo(OrderBook orderBook, BuyOrder buyOrder, int shares) {
        ConditionalOrder sellWhenStopTrailingLoss = sellWhenStopTrailingLoss(shares, buyOrder);
        ConditionalOrder sellOnLastTradingDate = sellOnLastTradingDate(shares, buyOrder);
        orderBook.addPendingOrder(new OneCancelTheOtherOrder(sellWhenStopTrailingLoss, sellOnLastTradingDate));
        orderBook.addPendingOrder(new OneCancelTheOtherOrder(sellOnLastTradingDate, sellWhenStopTrailingLoss));
    }

    private ConditionalOrder sellWhenStopTrailingLoss(int shares, BuyOrder buyOrder) {
//        int sharesToSell = shares/2; // UNCOMMENT TO DO A 2ND ORDER
        int sharesToSell = shares; // COMMENT TO DO A SECOND ORDER
        TrailingStopSellDecision stopTrailingLoss = stopTrailingLossGenerator.generateSellDecision(buyOrder.getPricePaid());
        SellConditionalOrder sellConditionalOrder = new SellConditionalOrder(this.stockName, buyOrder, sharesToSell, stopTrailingLoss);
        sellConditionalOrder.addCondition(stopTrailingLoss);
        return sellConditionalOrder;
    }

    private ConditionalOrder sellOnLastTradingDate(int shares, BuyOrder buyOrder) {
        SellConditionalOrder sellConditionalOrder = new SellConditionalOrder(this.stockName, buyOrder, shares, buildSellOnLastTradingDatePriceResolver());
        sellConditionalOrder.addCondition(buildSellOnLastTradingDate());
        return sellConditionalOrder;
    }

    private SellPriceResolver buildSellOnLastTradingDatePriceResolver() {
        return new SellPriceResolver() {
            @Override
            public BigDecimal resolveSellPrice(TimeFrameQuote timeFrameQuote) {
                return BigDecimal.valueOf(timeFrameQuote.getQuoteAtDay().getClosePrice());
            }
        };
    }

    private Function<TimeFrameQuote, Boolean> buildSellOnLastTradingDate() {
        return (timeFrameQuote -> timeFrameQuote.getQuoteAtDay().getTradingDate().equals(this.stockPrices.getLastTradingDate()));
    }

}
