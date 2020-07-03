package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.*;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

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
            this.triggered = false;
            double triggerPrice = this.cachedDecisionResult.getOrderExtraInfo().map((ei) -> ei.getBuyPriceTrigger()).orElse(this.triggerDailyQuote.getClosePrice());
            if (diffApplies.apply(dailyQuote.getClosePrice() - triggerPrice)) {
                if (ShouldCrearDecision.class.isAssignableFrom(buyTradingDecision.getClass())) {
                    ((ShouldCrearDecision) buyTradingDecision).clear();
                }
                return this.cachedDecisionResult;
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
