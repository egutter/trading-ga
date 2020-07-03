package com.egutter.trading.order;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.TradingStrategy;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by egutter on 2/12/14.
 */
public class MarketOrderGenerator {

    private final String stockName;
    private final Portfolio portfolio;
    private TradingStrategy tradingStrategy;
    private final DailyQuote dailyQuote;
    private DailyQuote nextDayQuote;
    private BigDecimal amountToInvest;
    private Optional<DailyQuote> marketQuote;

    public MarketOrderGenerator(String stockName,
                                Portfolio portfolio,
                                TradingStrategy tradingStrategy,
                                DailyQuote dailyQuote,
                                DailyQuote nextDayQuote,
                                BigDecimal amountToInvest,
                                Optional<DailyQuote> marketQuote) {
        this.stockName = stockName;
        this.portfolio = portfolio;
        this.tradingStrategy = tradingStrategy;
        this.dailyQuote = dailyQuote;
        this.nextDayQuote = nextDayQuote;
        this.amountToInvest = amountToInvest;
        this.marketQuote = marketQuote;
    }

    public OrderBook generateOrders() {

        OrderBook marketOrders = new OrderBook();

        DecisionResult buyResult = tradingStrategy.shouldBuyOn(dailyQuote.getTradingDate());
        if (DecisionResult.YES.equals(buyResult)) {
            marketOrders.add(
                    new BuyOrder(stockName,
                            dailyQuote,
                            nextDayQuote,
                            amountToInvest,
                            this.marketQuote,
                            buyResult.getOrderExtraInfo()));
        };

        DecisionResult sellResult = tradingStrategy.shouldSellOn(dailyQuote.getTradingDate());
        if (DecisionResult.YES.equals(sellResult)) {
            int sharesToSell = portfolio.getNumberOfSharesFor(stockName);
            if (sellResult.hasExtraInfo()) {
                OrderExtraInfo extraInfo = sellResult.getOrderExtraInfo().get();
                sharesToSell = extraInfo.getNumberOfShares();
            }
            marketOrders.add(
                    new SellOrder(stockName,
                            dailyQuote,
                            nextDayQuote,
                            sharesToSell,
                            this.marketQuote,
                            portfolio.getNumberOfMarketSharesFor(stockName)));
        };

        return marketOrders;
    }
}
