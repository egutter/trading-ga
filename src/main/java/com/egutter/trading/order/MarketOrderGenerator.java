package com.egutter.trading.order;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.TradingStrategy;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;

/**
 * Created by egutter on 2/12/14.
 */
public class MarketOrderGenerator {

    private final String stockName;
    private final Portfolio portfolio;
    private TradingStrategy tradingStrategy;
    private final DailyQuote dailyQuote;
    private int numberOfShares;

    public MarketOrderGenerator(String stockName,
                                Portfolio portfolio,
                                TradingStrategy tradingStrategy,
                                DailyQuote dailyQuote,
                                int numberOfShares) {


        this.stockName = stockName;
        this.portfolio = portfolio;
        this.tradingStrategy = tradingStrategy;
        this.dailyQuote = dailyQuote;
        this.numberOfShares = numberOfShares;
    }

    public OrderBook generateOrders() {

        OrderBook marketOrders = new OrderBook();

        if (tradingStrategy.shouldBuyOn(dailyQuote.getTradingDate())) {
            marketOrders.add(
                    new BuyOrder(stockName,
                            dailyQuote,
                            numberOfShares));
        };

        if (tradingStrategy.shouldSellOn(dailyQuote.getTradingDate())) {
            marketOrders.add(
                    new SellOrder(stockName,
                            dailyQuote,
                        portfolio.getNumberOfSharesFor(stockName)));
        };

        return marketOrders;
    }
}
