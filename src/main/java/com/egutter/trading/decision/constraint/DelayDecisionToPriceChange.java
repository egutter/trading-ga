package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.*;
import com.egutter.trading.order.OrderExtraInfo;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;

import java.util.Optional;
import java.util.function.Function;

public class DelayDecisionToPriceChange implements BuyTradingDecision, SellTradingDecision {

    private final StockPrices stockPrices;
    private TradingDecision decision;
    private final int delayDays;
    private int countDays;
    private boolean triggered = false;
    private DailyQuote triggerDailyQuote;
    private DecisionResult cachedDecisionResult;


    public DelayDecisionToPriceChange(StockPrices stockPrices, TradingDecision decision, int days) {
        this.stockPrices = stockPrices;
        this.decision = decision;
        this.delayDays = days;
        this.countDays = 0;
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        return shouldTradeOn(tradingDate, (diffClosePrice) -> diffClosePrice >=0 );
    }
    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        return shouldTradeOn(tradingDate, (diffClosePrice) -> diffClosePrice <=0 );
    }

    private DecisionResult shouldTradeOn(LocalDate tradingDate, Function<Double, Boolean> diffApplies) {
        BuyTradingDecision buyTradingDecision = (BuyTradingDecision) this.decision;
        DecisionResult decisionResult = buyTradingDecision.shouldBuyOn(tradingDate);
        if (decisionResult.equals(DecisionResult.YES)) {
            this.triggered = true;
            this.triggerDailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
            this.cachedDecisionResult = decisionResult;
        }
        if (this.triggered) {
            DailyQuote dailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
            DailyQuote prevQailyQuote = stockPrices.dailyPriceBefore(tradingDate, 1).get();
            DailyQuote nextDailyQuote = stockPrices.dailyPriceAfter(tradingDate, 1).orElse(dailyQuote);
            Optional<OrderExtraInfo> orderExtraInfo = this.cachedDecisionResult.getOrderExtraInfo();
            double triggerPrice = orderExtraInfo.map((ei) -> ei.getBuyPriceTrigger()).orElse(this.triggerDailyQuote.getClosePrice());
            Pair<Double, Double> triggerSellPrice = orderExtraInfo.map((ei) -> ei.getNextTriggerSellPrice()).orElse(Optional.of(new Pair<Double, Double>(this.triggerDailyQuote.getClosePrice(), 1.0))).get();
//            this.triggered = false;
            if (
                    diffApplies.apply(dailyQuote.getLowPrice() - triggerPrice)
                            && diffApplies.apply(triggerSellPrice.getFirst() - nextDailyQuote.getOpenPrice())
                            && diffApplies.apply(dailyQuote.getClosePrice() - dailyQuote.getOpenPrice())
                            && diffApplies.apply(dailyQuote.getClosePrice() - prevQailyQuote.getClosePrice())
            ) {
                this.triggered = false;
                if (ShouldClearDecision.class.isAssignableFrom(buyTradingDecision.getClass())) {
                    ((ShouldClearDecision) buyTradingDecision).clear();
                }
                return this.cachedDecisionResult;
            } else {
                return DecisionResult.NO;
            }
        }
        return decisionResult;
    }

    private DecisionResult shouldTradeOnOld(LocalDate tradingDate, Function<Double, Boolean> diffApplies) {
        DecisionResult decisionResult = ((BuyTradingDecision) this.decision).shouldBuyOn(tradingDate);
        if (decisionResult.equals(DecisionResult.YES)) {
            this.triggered = true;
            this.triggerDailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
            this.cachedDecisionResult = decisionResult;
        }
        if (this.triggered) {
            if (delayDays == countDays) {
                DailyQuote dailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
                this.triggered = false;
                this.countDays = 0;
                double triggerPrice = this.cachedDecisionResult.getOrderExtraInfo().map((ei) -> ei.getBuyPriceTrigger()).orElse(this.triggerDailyQuote.getClosePrice());
                if (diffApplies.apply(dailyQuote.getClosePrice() - triggerPrice)) {
                    return this.cachedDecisionResult;
                }
            } else {
                this.countDays++;
            }
        }
        return DecisionResult.NO;
    }

    @Override
    public String buyDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "Delayed days",
                this.delayDays,
                "For Decision",
                ((BuyTradingDecision)this.decision).buyDecisionToString());
    }

    @Override
    public LocalDate startOn() {
        return ((BuyTradingDecision) decision).startOn();
    }

    @Override
    public String sellDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "Delayed days",
                this.delayDays,
                "For Decision",
                ((SellTradingDecision)this.decision).sellDecisionToString());
    }
}
