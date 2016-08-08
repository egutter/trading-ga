package com.egutter.trading.stock;

import com.egutter.trading.decision.TradingStrategy;
import com.egutter.trading.decision.factory.TradingDecisionFactory;
import com.egutter.trading.order.MarketOrderGenerator;
import com.egutter.trading.order.OrderBook;
import com.google.common.base.Function;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by egutter on 2/12/14.
 */
public class Trader {

    public static final BigDecimal AMOUNT_TO_INVEST = BigDecimal.valueOf(10000.00);
    private StockMarket stockMarket;
    private TradingDecisionFactory tradingDecisionFactory;
    private Portfolio portfolio;
    private OrderBook orderBook;

    public Trader(StockMarket stockMarket,
                  TradingDecisionFactory tradingDecisionFactory,
                  Portfolio portfolio,
                  OrderBook orderBook) {

        this.stockMarket = stockMarket;
        this.tradingDecisionFactory = tradingDecisionFactory;
        this.portfolio = portfolio;
        this.orderBook = orderBook;
    }


    public void tradeAllStocksInMarketInParallel() {
        stockMarket.getStockPrices().parallelStream().forEach(stockPrices -> {
            System.out.println("Trading " + stockPrices.getStockName());
            tradeOneStock(stockPrices);
        });
    }

    public void tradeAllStocksInMarket() {
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            tradeOneStock(stockPrices);
            if (shouldStop()) break;
        }
    }

    private boolean shouldStop() {
        return this.portfolio.getStats().hasLostOrders();
    }

    private void tradeOneStock(StockPrices stockPrices) {
        TradingStrategy tradingStrategy = new TradingStrategy(this.tradingDecisionFactory, stockPrices);
        LocalDate startOn = tradingStrategy.startOn();
        stockPrices.forEachDailyPrice(startOn, executeMarketOrders(stockPrices, tradingStrategy), () -> shouldStop());
    }

    public void tradeOn(LocalDate tradingDate) {
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            TradingStrategy tradingStrategy = new TradingStrategy(this.tradingDecisionFactory, stockPrices);
            stockPrices.withDailyPriceOn(tradingDate, executeMarketOrders(stockPrices, tradingStrategy));
        }
    }

    private Function<DailyQuote, Object> executeMarketOrders(final StockPrices stockPrices, final TradingStrategy tradingStrategy) {
        return new Function<DailyQuote, Object>() {
            @Override
            public Object apply(DailyQuote dailyQuote) {
                MarketOrderGenerator marketOrderGenerator = marketOrderGenerator(stockPrices, dailyQuote, tradingStrategy);
                OrderBook newMarketOrders = marketOrderGenerator.generateOrders();
                newMarketOrders.execute(portfolio);
                orderBook.append(newMarketOrders);
                return orderBook;
            }
        };
    }

    private MarketOrderGenerator marketOrderGenerator(StockPrices stockPrices, DailyQuote dailyQuote, TradingStrategy tradingStrategy) {
        Optional<DailyQuote> marketQuote = stockMarket.getMarketIndexPrices().dailyPriceOn(dailyQuote.getTradingDate());
        return new MarketOrderGenerator(stockPrices.getStockName(),
                portfolio,
                tradingStrategy,
                dailyQuote,
                AMOUNT_TO_INVEST,
                marketQuote);
    }

    public int ordersExecuted() {
        return orderBook.size();
    }

    public BigDecimal amountInvested() {
        return orderBook.amountInvested();
    }

}
