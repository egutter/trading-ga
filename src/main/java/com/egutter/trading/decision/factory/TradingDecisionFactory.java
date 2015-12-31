package com.egutter.trading.decision.factory;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.StockPrices;

/**
 * Created by egutter on 12/19/15.
 */
public interface TradingDecisionFactory {
    BuyTradingDecision generateBuyDecision(StockPrices stockPrices);

    SellTradingDecision generateSellDecision(StockPrices stockPrices);
}
