package com.egutter.trading.stock;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.generator.TradingDecisionGeneratorBuilder;
import com.egutter.trading.order.MarketOrderGenerator;
import com.egutter.trading.order.OrderBook;
import com.google.common.base.Function;

/**
 * Created by egutter on 2/12/14.
 */
public class Trader {

    public static final int NUMBER_OF_SHARES = 100;
    private StockMarket stockMarket;
    private TradingDecisionGeneratorBuilder tradingDecisionGenerator;
    private Portfolio portfolio;
    private OrderBook orderBook;

    public Trader(StockMarket stockMarket,
                  TradingDecisionGeneratorBuilder tradingDecisionGenerator,
                  Portfolio portfolio,
                  OrderBook orderBook) {

        this.stockMarket = stockMarket;
        this.tradingDecisionGenerator = tradingDecisionGenerator;
        this.portfolio = portfolio;
        this.orderBook = orderBook;
    }


    public void trade() {
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            BuyTradingDecision buyTradingDecision = tradingDecisionGenerator.generateBuyDecision(stockPrices);
            SellTradingDecision sellTradingDecision = tradingDecisionGenerator.generateSellDecision(stockPrices);

            stockPrices.forEachDailyPrice(executeMarketOrders(stockPrices, buyTradingDecision, sellTradingDecision));
        }
    }

    private Function<DailyQuote, Object> executeMarketOrders(final StockPrices stockPrices, final BuyTradingDecision buyTradingDecision, final SellTradingDecision sellTradingDecision) {
        return new Function<DailyQuote, Object>() {
            @Override
            public Object apply(DailyQuote dailyQuote) {
                MarketOrderGenerator marketOrderGenerator = marketOrderGenerator(stockPrices, buyTradingDecision, sellTradingDecision, dailyQuote);
                OrderBook newMarketOrders = marketOrderGenerator.generateOrders();
                newMarketOrders.execute(portfolio);
                orderBook.append(newMarketOrders);
                return orderBook;
            }
        };
    }

    private MarketOrderGenerator marketOrderGenerator(StockPrices stockPrices, BuyTradingDecision buyTradingDecision, SellTradingDecision sellTradingDecision, DailyQuote dailyQuote) {
        return new MarketOrderGenerator(stockPrices.getStockName(),
                portfolio,
                buyTradingDecision,
                sellTradingDecision,
                dailyQuote,
                NUMBER_OF_SHARES);
    }

    public int ordersExecuted() {
        return orderBook.getOrders().size();
    }
}
