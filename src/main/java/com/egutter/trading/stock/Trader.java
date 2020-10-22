package com.egutter.trading.stock;

import com.egutter.trading.decision.FibonacciRetracementStrategyFactory;
import com.egutter.trading.decision.TradingStrategy;
import com.egutter.trading.decision.factory.TradingDecisionFactory;
import com.egutter.trading.decision.technicalanalysis.FibonacciRetracementBuyDecision;
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
    public static final double MAX_PORTFOLIO_LOST = 0.2;
    private StockMarket stockMarket;
    private TradingDecisionFactory tradingDecisionFactory;
    private Portfolio portfolio;
    private OrderBook orderBook;
    private FibonacciRetracementStrategyFactory fibonacciStrategy;

    public Trader(StockMarket stockMarket,
                  TradingDecisionFactory tradingDecisionFactory,
                  Portfolio portfolio,
                  OrderBook orderBook) {

        this.stockMarket = stockMarket;
        this.tradingDecisionFactory = tradingDecisionFactory;
        this.portfolio = portfolio;
        this.orderBook = orderBook;
    }

    public Trader(StockMarket stockMarket, FibonacciRetracementStrategyFactory fibonacciStrategy, Portfolio portfolio, OrderBook orderBook) {
        this.stockMarket = stockMarket;
        this.fibonacciStrategy = fibonacciStrategy;
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
        return this.portfolio.isLostAbove(BigDecimal.valueOf(MAX_PORTFOLIO_LOST));
    }

    private void tradeOneStock(StockPrices stockPrices) {
//        TradingStrategy tradingStrategy = new TradingStrategy(this.tradingDecisionFactory, stockPrices);
//        LocalDate startOn = tradingStrategy.startOn();
        FibonacciRetracementBuyDecision tradingStrategy = fibonacciStrategy.generateBuyDecision(stockPrices);
        LocalDate startOn = stockPrices.getFirstTradingDate();
        stockPrices.forEachDailyPrice(startOn, executeMarketOrders(stockPrices, tradingStrategy), () -> shouldStop());
    }

    public void tradeOn(LocalDate tradingDate) {
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            FibonacciRetracementBuyDecision tradingStrategy = fibonacciStrategy.generateBuyDecision(stockPrices);
            LocalDate startOn = tradingDate.minusDays(10);
            stockPrices.forEachDailyPrice(startOn, executeMarketOrders(stockPrices, tradingStrategy, (runOnDate) -> runOnDate.equals(tradingDate)), () -> false);
        }
    }

    private Function<DailyQuote, Object> executeMarketOrders(final StockPrices stockPrices, final FibonacciRetracementBuyDecision buyDecision) {
        return executeMarketOrders(stockPrices, buyDecision, (runOnDate) -> true);
    }

    private Function<DailyQuote, Object> executeMarketOrders(final StockPrices stockPrices, final FibonacciRetracementBuyDecision buyDecision, Function<LocalDate, Boolean> shouldAddOrders) {
        return new Function<DailyQuote, Object>() {
            @Override
            public Object apply(DailyQuote dailyQuote) {
                TimeFrameQuote timeFrameQuote = builTimeFrameQuote(dailyQuote, stockPrices);

                orderBook.forEachPendingOrder((order) ->
                        order.execute(timeFrameQuote.getQuoteAtDay().getTradingDate(), orderBook, portfolio, timeFrameQuote));

                MarketOrderGenerator marketOrderGenerator = marketOrderGenerator(stockPrices, dailyQuote, buyDecision, timeFrameQuote);

                OrderBook newMarketOrders = new OrderBook();
                marketOrderGenerator.generateOrders_NEW(newMarketOrders);

                if (shouldAddOrders.apply(dailyQuote.getTradingDate())) {
                    orderBook.append(newMarketOrders);
                }
                return orderBook;
            }
        };
    }

    private TimeFrameQuote builTimeFrameQuote(DailyQuote dailyQuote, StockPrices stockPrices) {
        DailyQuote quoteAtNextDay = stockPrices.dailyPriceAfter(dailyQuote.getTradingDate(), 1).orElse(dailyQuote);
        DailyQuote quoteAtPrevDay = stockPrices.dailyPriceBefore(dailyQuote.getTradingDate(), 1).orElse(dailyQuote);
        return new TimeFrameQuote(dailyQuote, quoteAtPrevDay, quoteAtNextDay);
    }

    private MarketOrderGenerator marketOrderGenerator(StockPrices stockPrices, DailyQuote dailyQuote, FibonacciRetracementBuyDecision tradingStrategy, TimeFrameQuote timeFrameQuote) {
        Optional<DailyQuote> marketQuote = stockMarket.getMarketIndexPrices().dailyPriceOn(dailyQuote.getTradingDate());

        return new MarketOrderGenerator(stockPrices.getStockName(),
                portfolio,
                tradingStrategy,
                timeFrameQuote,
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
