package com.egutter.trading.decision.factory;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.consensus.BuyWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.constraint.*;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;

import java.math.BigDecimal;

/**
 * Created by egutter on 12/19/15.
 */
public class HitProfitTargetDecisionFactory implements TradingDecisionFactory {

    private static final int DAYS_TO_SELL_THRESHOLD = 10;
    private static final BigDecimal PROFIT_THRESHOLD = BigDecimal.TEN;
    private Portfolio portfolio;

    public HitProfitTargetDecisionFactory(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        BuyWhenNoOppositionsTradingDecision tradingDecisionComposite = new BuyWhenNoOppositionsTradingDecision();
        tradingDecisionComposite.addBuyTradingDecision(new DoNotBuyWhenSameStockInPortfolio(portfolio, stockPrices));
        tradingDecisionComposite.addBuyTradingDecision(new DoNotBuyInTheLastBuyTradingDays(stockPrices, DAYS_TO_SELL_THRESHOLD));
        tradingDecisionComposite.addBuyTradingDecision(new HitProfitTargetBuyTradingDecision(stockPrices, PROFIT_THRESHOLD));
        return tradingDecisionComposite;
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        SellWhenNoOppositionsTradingDecision sellAfterAFixedNumberOfDaysComposite = new SellWhenNoOppositionsTradingDecision();
        sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices));
        sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(new SellAfterAFixedNumberOFDays(portfolio, stockPrices, DAYS_TO_SELL_THRESHOLD));

        sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(new SellByProfitThreshold(portfolio, stockPrices, PROFIT_THRESHOLD));

        return sellAfterAFixedNumberOfDaysComposite;
    }


}
