package com.egutter.trading.order;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.TradingStrategy;
import com.egutter.trading.decision.technicalanalysis.FibonacciRetracementBuyDecision;
import com.egutter.trading.stats.MetricsRecorder;
import com.egutter.trading.stats.MetricsRecorderFactory;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.TimeFrameQuote;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by egutter on 2/12/14.
 */
public class MarketOrderGenerator {

    private final String stockName;
    private final Portfolio portfolio;
    private FibonacciRetracementBuyDecision tradingStrategy;
    private TimeFrameQuote timeFrameQuote;
    private BigDecimal amountToInvest;
    private Optional<DailyQuote> marketQuote;

    public MarketOrderGenerator(String stockName,
                                Portfolio portfolio,
                                FibonacciRetracementBuyDecision tradingStrategy,
                                TimeFrameQuote timeFrameQuote,
                                BigDecimal amountToInvest,
                                Optional<DailyQuote> marketQuote) {
        this.stockName = stockName;
        this.portfolio = portfolio;
        this.tradingStrategy = tradingStrategy;
        this.timeFrameQuote = timeFrameQuote;
        this.amountToInvest = amountToInvest;
        this.marketQuote = marketQuote;
    }

    public void generateOrders_NEW(OrderBook orderBook) {
        tradingStrategy.generateOrder(timeFrameQuote).ifPresent((conditionalOrder -> {
            orderBook.addPendingOrder(conditionalOrder);
            MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.BUY_COND_ORDER);
        }));
    }

//    public OrderBook generateOrders() {
//
//        OrderBook marketOrders = new OrderBook();
//
//        LocalDate tradingDate = timeFrameQuote.getQuoteAtDay().getTradingDate();
//        DecisionResult buyResult = tradingStrategy.shouldBuyOn(tradingDate);
//        if (DecisionResult.YES.equals(buyResult)) {
//            marketOrders.add(
//                    new BuyOrder(stockName,
//                            timeFrameQuote.getQuoteAtDay(),
//                            timeFrameQuote.getQuoteAtNextDay(),
//                            amountToInvest,
//                            this.marketQuote,
//                            buyResult.getOrderExtraInfo()));
//        };
//
//        DecisionResult sellResult = tradingStrategy.shouldSellOn(tradingDate);
//        if (DecisionResult.YES.equals(sellResult)) {
//            int sharesToSell = portfolio.getNumberOfSharesFor(stockName);
//            Double priceToSell = timeFrameQuote.getQuoteAtDay().getClosePrice();
//            if (sellResult.hasExtraInfo()) {
//                OrderExtraInfo extraInfo = sellResult.getOrderExtraInfo().get();
//                sharesToSell = extraInfo.getNumberOfShares().orElse(portfolio.getNumberOfSharesFor(stockName));
//                priceToSell = extraInfo.getSellPrice().orElse(timeFrameQuote.getQuoteAtDay().getClosePrice());
//            }
//            marketOrders.add(
//                    new SellOrder(stockName,
//                            timeFrameQuote.getQuoteAtDay(),
//                            timeFrameQuote.getQuoteAtNextDay(),
//                            sharesToSell,
//                            this.marketQuote,
//                            portfolio.getNumberOfMarketSharesFor(stockName),
//                            priceToSell));
//        };
//
//        return marketOrders;
//    }
}
