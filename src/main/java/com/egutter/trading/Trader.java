package com.egutter.trading;

import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.decision.generator.TradingDecisionGenerator;
import com.egutter.trading.order.MarketOrderGenerator;
import com.egutter.trading.order.OrderBook;
import com.egutter.trading.stock.DailyPrice;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockPortfolio;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Function;

/**
 * Created by egutter on 2/12/14.
 */
public class Trader {

    public static final int NUMBER_OF_SHARES = 100;
    private StockMarket stockMarket;
    private TradingDecisionGenerator tradingDecisionGenerator;
    private StockPortfolio stockPortfolio;
    private OrderBook orderBook;

    public Trader(StockMarket stockMarket,
                  TradingDecisionGenerator tradingDecisionGenerator,
                  StockPortfolio stockPortfolio,
                  OrderBook orderBook) {

        this.stockMarket = stockMarket;
        this.tradingDecisionGenerator = tradingDecisionGenerator;
        this.stockPortfolio = stockPortfolio;
        this.orderBook = orderBook;
    }


    public void trade() {
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            TradingDecision tradingDecision = tradingDecisionGenerator.generate(stockPrices);

            stockPrices.forEachDailyPrice(executeMarketOrders(stockPrices, tradingDecision));
        }
    }

    private Function<DailyPrice, Object> executeMarketOrders(final StockPrices stockPrices, final TradingDecision tradingDecision) {
        return new Function<DailyPrice, Object>() {
            @Override
            public Object apply(DailyPrice dailyPrice) {
                OrderBook newMarketOrders = marketOrderGenerator(stockPrices, tradingDecision, dailyPrice).generateOrders();
                newMarketOrders.execute(stockPortfolio);
                orderBook.append(newMarketOrders);
                return orderBook;
            }
        };
    }

    private MarketOrderGenerator marketOrderGenerator(StockPrices stockPrices, TradingDecision tradingDecision, DailyPrice dailyPrice) {
        return new MarketOrderGenerator(stockPrices.getStockName(),
                            stockPortfolio,
                            tradingDecision,
                            dailyPrice,
                            NUMBER_OF_SHARES);
    }

}
