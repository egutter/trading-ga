package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.StockPrices;

/**
 * Created by egutter on 2/15/14.
 */
public interface SellTradingDecisionGenerator extends TradingDecisionGenerator {

    SellTradingDecision generateSellDecision(StockPrices stockPrices);
}
