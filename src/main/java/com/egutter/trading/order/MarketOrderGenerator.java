package com.egutter.trading.order;

import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;

/**
 * Created by egutter on 2/12/14.
 */
public class MarketOrderGenerator {

    private final String stockName;
    private final Portfolio portfolio;
    private final TradingDecision tradingDecision;
    private final DailyQuote dailyQuote;
    private int numberOfShares;

    public MarketOrderGenerator(String stockName,
                                Portfolio portfolio,
                                TradingDecision tradingDecision,
                                DailyQuote dailyQuote,
                                int numberOfShares) {


        this.stockName = stockName;
        this.portfolio = portfolio;
        this.tradingDecision = tradingDecision;
        this.dailyQuote = dailyQuote;
        this.numberOfShares = numberOfShares;
    }

    public OrderBook generateOrders() {

        OrderBook marketOrders = new OrderBook();

        if (tradingDecision.shouldBuyOn(dailyQuote.getTradingDate())) {
            marketOrders.add(
                    new BuyOrder(stockName,
                            dailyQuote,
                            numberOfShares));
        };

        if (tradingDecision.shouldSellOn(dailyQuote.getTradingDate())) {
            marketOrders.add(
                    new SellOrder(stockName,
                            dailyQuote,
                        portfolio.getNumberOfSharesFor(stockName)));
        };

        return marketOrders;
    }
}
