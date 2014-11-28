package com.egutter.trading.decision;

import com.egutter.trading.decision.generator.TradingDecisionFactory;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

/**
 * Created by egutter on 4/11/14.
 */
public class TradingStrategy {

    private final BuyTradingDecision buyTradingDecision;
    private final SellTradingDecision sellTradingDecision;

    public TradingStrategy(TradingDecisionFactory tradingDecisionFactory, StockPrices stockPrices) {
        this.buyTradingDecision = tradingDecisionFactory.generateBuyDecision(stockPrices);
        this.sellTradingDecision = tradingDecisionFactory.generateSellDecision(stockPrices);
    }

    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        return buyTradingDecision.shouldBuyOn(tradingDate);
    }

    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        return sellTradingDecision.shouldSellOn(tradingDate);
    }
}
