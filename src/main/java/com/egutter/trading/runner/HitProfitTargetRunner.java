package com.egutter.trading.runner;

import com.egutter.trading.decision.factory.HitProfitTargetDecisionFactory;
import com.egutter.trading.decision.factory.TradingDecisionFactory;
import com.egutter.trading.decision.technicalanalysis.*;
import com.egutter.trading.decision.technicalanalysis.macd.SignChange;
import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.OrderBook;
import com.egutter.trading.stock.*;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.function.Function;

/**
 * Created by egutter on 12/19/15.
 */
public class HitProfitTargetRunner {

    public static final BigDecimal INITIAL_CASH = new BigDecimal(1000000.00);
    private HashMap<String, TechnicalAnalysisIndicator> indexCache = new HashMap<String, TechnicalAnalysisIndicator>();

    public static void main(String[] args) {
        LocalTime startTime = LocalTime.now();

        new HitProfitTargetRunner().run();

        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    private void run() {
        LocalDate fromDate = new LocalDate(2014, 1, 1);
        LocalDate toDate = LocalDate.now();
        System.out.println("Period from " + fromDate + " to " + toDate);

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);


        Portfolio portfolio = new Portfolio(INITIAL_CASH);

        TradingDecisionFactory tradingDecisionFactory = new HitProfitTargetDecisionFactory(portfolio);
        OrderBook orderBook = new OrderBook();
        new Trader(stockMarket, tradingDecisionFactory, portfolio, orderBook).tradeAllStocksInMarketInParallel();

        System.out.println("NUM OF STOCKS BOUGHT: " + orderBook.buyOrders().size());
        System.out.println("NUM OF STOCKS SOLD: " + orderBook.sellOrders().size());
        System.out.println("RESULTS ARE");
        System.out.println(orderBook);
        System.out.println("STOCK, DATE, PRICE PAID, VOLUME, AROON, ADX, BOLLINGER BANDS, MACD SIGN, MACD, RSI, WILLR, MFI");
        for (BuyOrder order: orderBook.buyOrders()) {
            DailyQuote dailyQuote = order.getDailyQuote();

            System.out.println(Joiner.on(",").join(order.getStockName(),
                    order.getTradingDate(),
                    dailyQuote.getAdjustedClosePrice(),
                    dailyQuote.getVolume(),
                    getAroonAtDay(stockMarket, order.getStockName(), order.getTradingDate()),
                    getAdxAtDay(stockMarket, order.getStockName(), order.getTradingDate()),
                    getBollingerAtDay(stockMarket, order.getStockName(), order.getTradingDate()),
                    getMacdAtDay(stockMarket, order.getStockName(), order.getTradingDate()),
                    getRsiAtDay(stockMarket, order.getStockName(), order.getTradingDate()),
                    getWilliamRAtDay(stockMarket, order.getStockName(), order.getTradingDate()),
                    getMoneyFlowIndexAtDay(stockMarket, order.getStockName(), order.getTradingDate())));

        }
    }

    private Double getBollingerAtDay(StockMarket stockMarket, String stockName, LocalDate tradingDate) {
        return getIndexAtDay(stockMarket, stockName, tradingDate, "BBAND", (stockPrices) -> BollingerBands.empty(stockPrices));
    }

    private Double getAdxAtDay(StockMarket stockMarket, String stockName, LocalDate tradingDate) {
        return getIndexAtDay(stockMarket, stockName, tradingDate, "ADX", (stockPrices) -> AverageDirectionalIndex.empty(stockPrices));
    }

    private Double getAroonAtDay(StockMarket stockMarket, String stockName, LocalDate tradingDate) {
        return getIndexAtDay(stockMarket, stockName, tradingDate, "AROON", (stockPrices) -> AroonOscilator.empty(stockPrices));
    }

    private Double getMoneyFlowIndexAtDay(StockMarket stockMarket, String stockName, LocalDate tradingDate) {
        return getIndexAtDay(stockMarket, stockName, tradingDate, "MFI", (stockPrices) -> MoneyFlowIndex.empty(stockPrices));
    }

    private Double getRsiAtDay(StockMarket stockMarket, String stockName, LocalDate tradingDate) {
        return getIndexAtDay(stockMarket, stockName, tradingDate, "RSI", (stockPrices) -> RelativeStrengthIndex.empty(stockPrices));
    }

    private Double getWilliamRAtDay(StockMarket stockMarket, String stockName, LocalDate tradingDate) {
        return getIndexAtDay(stockMarket, stockName, tradingDate, "WILLR", (stockPrices) -> WilliamsR.empty(stockPrices));
    }

    private String getMacdAtDay(StockMarket stockMarket, String stockName, LocalDate tradingDate) {
        TechnicalAnalysisIndicator technicalAnalysisIndicator = getTechnicalAnalysisIndicator(stockMarket, stockName, "MACD", (stockPrices) -> MovingAverageConvergenceDivergence.empty(stockPrices));
        SignChange signChange = ((MovingAverageConvergenceDivergence) technicalAnalysisIndicator).getSignChangeAtDate(tradingDate).orElse(SignChange.NO_CHANGE);
        Double diffWithPrevDay = technicalAnalysisIndicator.getIndexAtDate(tradingDate).orElse(Double.valueOf(-9999));
        return signChange + "," + diffWithPrevDay;
    }

    private Double getIndexAtDay(StockMarket stockMarket, String stockName, LocalDate tradingDate, String tradingDecisionName, Function<StockPrices, TechnicalAnalysisIndicator> calculateIndex) {
        TechnicalAnalysisIndicator technicalAnalysisIndicator = getTechnicalAnalysisIndicator(stockMarket, stockName, tradingDecisionName, calculateIndex);
        return technicalAnalysisIndicator.getIndexAtDate(tradingDate).orElse(Double.valueOf(-9999));
    }

    private TechnicalAnalysisIndicator getTechnicalAnalysisIndicator(StockMarket stockMarket, String stockName, String tradingDecisionName, Function<StockPrices, TechnicalAnalysisIndicator> calculateIndex) {
        TechnicalAnalysisIndicator technicalAnalysisIndicator;
        String cacheKey = stockName + "." + tradingDecisionName;
        if (indexCache.containsKey(cacheKey)) {
            technicalAnalysisIndicator = indexCache.get(cacheKey);
        } else {
            technicalAnalysisIndicator = calculateIndex.apply(stockMarket.getStockPricesFor(stockName));
            indexCache.put(cacheKey, technicalAnalysisIndicator);
        }
        return technicalAnalysisIndicator;
    }


}
