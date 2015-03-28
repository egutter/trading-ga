package com.egutter.trading.order;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.TradingStrategy;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.google.common.base.Optional;

import java.math.BigDecimal;

/**
 * Created by egutter on 2/12/14.
 */
public class MarketOrderGenerator {

    private final String stockName;
    private final Portfolio portfolio;
    private TradingStrategy tradingStrategy;
    private final DailyQuote dailyQuote;
    private BigDecimal amountToInvest;
    private Optional<DailyQuote> marketQuote;

    public MarketOrderGenerator(String stockName,
                                Portfolio portfolio,
                                TradingStrategy tradingStrategy,
                                DailyQuote dailyQuote,
                                BigDecimal amountToInvest,
                                Optional<DailyQuote> marketQuote) {
        this.stockName = stockName;
        this.portfolio = portfolio;
        this.tradingStrategy = tradingStrategy;
        this.dailyQuote = dailyQuote;
        this.amountToInvest = amountToInvest;
        this.marketQuote = marketQuote;
    }

    public OrderBook generateOrders() {

        OrderBook marketOrders = new OrderBook();

        if (DecisionResult.YES.equals(tradingStrategy.shouldBuyOn(dailyQuote.getTradingDate()))) {
            marketOrders.add(
                    new BuyOrder(stockName,
                            dailyQuote,
                            amountToInvest,
                            this.marketQuote));
        };

        if (DecisionResult.YES.equals(tradingStrategy.shouldSellOn(dailyQuote.getTradingDate()))) {
            marketOrders.add(
                    new SellOrder(stockName,
                            dailyQuote,
                            portfolio.getNumberOfSharesFor(stockName),
                            this.marketQuote,
                            portfolio.getNumberOfMarketSharesFor(stockName)));
        };

        return marketOrders;
    }
}
