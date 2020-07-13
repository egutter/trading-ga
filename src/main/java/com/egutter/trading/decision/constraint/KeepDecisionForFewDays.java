package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.*;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.util.function.Function;

public class KeepDecisionForFewDays implements BuyTradingDecision, SellTradingDecision, ShouldClearDecision {

    private final StockPrices stockPrices;
    private TradingDecision decision;
    private final int delayDays;
    private int countDays;
    private boolean triggered = false;
    private DailyQuote triggerDailyQuote;
    private DecisionResult cachedDecisionResult;


    public KeepDecisionForFewDays(StockPrices stockPrices, TradingDecision decision, int days) {
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
        DecisionResult decisionResult = ((BuyTradingDecision) this.decision).shouldBuyOn(tradingDate);
        if (decisionResult.equals(DecisionResult.YES)) {
            this.triggered = true;
            this.triggerDailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
            this.countDays = 0;
            this.cachedDecisionResult = decisionResult;
        }
        if (this.triggered) {
            if (countDays <= delayDays) {
                this.countDays++;
                return this.cachedDecisionResult;
            } else {
                clear();
            }
        }
        return decisionResult;
    }

    @Override
    public void clear() {
        this.triggered = false;
        this.countDays = 0;
        this.cachedDecisionResult = null;
    }

//    private DecisionResult shouldTradeOn(LocalDate tradingDate, Function<Double, Boolean> diffApplies) {
//        DecisionResult decisionResult = ((BuyTradingDecision) this.decision).shouldBuyOn(tradingDate);
//        if (decisionResult.equals(DecisionResult.YES)) {
//            this.triggered = true;
//            this.triggerDailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
//            this.countDays = 0;
//        }
//        if (this.triggered) {
//            if (countDays <= delayDays) {
//                this.countDays++;
//                DailyQuote dailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
//                if (diffApplies.apply(dailyQuote.getClosePrice() - this.triggerDailyQuote.getClosePrice())) {
//                    return DecisionResult.YES;
//                }
//            } else {
//                this.triggered = false;
//                this.countDays = 0;
//            }
//        }
//        return DecisionResult.NO;
//    }

    @Override
    public String buyDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "Keep days",
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
                "Keep days",
                this.delayDays,
                "For Decision",
                ((SellTradingDecision)this.decision).sellDecisionToString());
    }
}
