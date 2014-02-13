package com.egutter.trading.order;

import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.stock.DailyPrice;
import com.egutter.trading.stock.StockPortfolio;

/**
 * Created by egutter on 2/12/14.
 */
public class MarketOrderGenerator {

    private final String stockName;
    private final StockPortfolio stockPortfolio;
    private final TradingDecision tradingDecision;
    private final DailyPrice dailyPrice;
    private int numberOfShares;

    public MarketOrderGenerator(String stockName,
                                StockPortfolio stockPortfolio,
                                TradingDecision tradingDecision,
                                DailyPrice dailyPrice,
                                int numberOfShares) {


        this.stockName = stockName;
        this.stockPortfolio = stockPortfolio;
        this.tradingDecision = tradingDecision;
        this.dailyPrice = dailyPrice;
        this.numberOfShares = numberOfShares;
    }

    public OrderBook generateOrders() {

        OrderBook marketOrders = new OrderBook();

        if (tradingDecision.shouldBuyOn(dailyPrice.getTradingDate())) {
            marketOrders.add(
                    new BuyOrder(stockName,
                            dailyPrice,
                            numberOfShares));
        };

        if (tradingDecision.shouldSellOn(dailyPrice.getTradingDate())) {
            marketOrders.add(
                    new SellOrder(stockName,
                        dailyPrice,
                        stockPortfolio.getNumberOfSharesFor(stockName)));
        };

        return marketOrders;
    }
}
