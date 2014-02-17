package com.egutter.trading.stock;

import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.decision.generator.TradingDecisionGeneratorBuilder;
import com.egutter.trading.order.MarketOrderGenerator;
import com.egutter.trading.order.OrderBook;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
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
            TradingDecision tradingDecision = tradingDecisionGenerator.generate(stockPrices);

            stockPrices.forEachDailyPrice(executeMarketOrders(stockPrices, tradingDecision));
        }
    }

    private Function<DailyQuote, Object> executeMarketOrders(final StockPrices stockPrices, final TradingDecision tradingDecision) {
        return new Function<DailyQuote, Object>() {
            @Override
            public Object apply(DailyQuote dailyQuote) {
                OrderBook newMarketOrders = marketOrderGenerator(stockPrices, tradingDecision, dailyQuote).generateOrders();
                newMarketOrders.execute(portfolio);
                orderBook.append(newMarketOrders);
                return orderBook;
            }
        };
    }

    private MarketOrderGenerator marketOrderGenerator(StockPrices stockPrices, TradingDecision tradingDecision, DailyQuote dailyQuote) {
        return new MarketOrderGenerator(stockPrices.getStockName(),
                portfolio,
                            tradingDecision,
                dailyQuote,
                            NUMBER_OF_SHARES);
    }

}
