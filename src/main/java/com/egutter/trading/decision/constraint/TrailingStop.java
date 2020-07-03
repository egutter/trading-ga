package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.SellOrder;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.math.BigDecimal;

public class TrailingStop implements SellTradingDecision {

    private Portfolio portfolio;
    private StockPrices stockPrices;
    private final BigDecimal stopLoss;
    private final BigDecimal trailingLoss;
    private double maxClosePrice = 0.0;

    public TrailingStop(Portfolio portfolio, StockPrices stockPrices, BigDecimal stopLoss, BigDecimal trailingLoss) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
        this.stopLoss = stopLoss.abs().negate();
        this.trailingLoss = trailingLoss.abs().negate();
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        if (!portfolio.hasStock(stockPrices.getStockName())) {
            this.maxClosePrice = 0.0;
            return DecisionResult.NEUTRAL;
        }
        BuyOrder buyOrder = portfolio.getPortFolioAsset(stockPrices.getStockName()).getBuyOrder();
        DailyQuote dailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
        trackMaxClosePrice(dailyQuote);

        SellOrder sellOrder = new SellOrder(buyOrder.getStockName(), dailyQuote, buyOrder.getNumberOfShares());
        if (triggerStopLoss(buyOrder, sellOrder)) return DecisionResult.YES;
        if (triggerTrailingLoss(buyOrder, sellOrder)) return DecisionResult.YES;

        return DecisionResult.NEUTRAL;
    }

    private boolean triggerStopLoss(BuyOrder buyOrder, SellOrder sellOrder) {
        BuySellOperation buySellOperation = new BuySellOperation(buyOrder, sellOrder);
        if (!buySellOperation.isLost()) return false;

        boolean stopLoss = buySellOperation.profitPctg().compareTo(this.stopLoss) <= 0;
//        if (stopLoss) System.out.println("Stop Loss triggered on "+ sellOrder.getTradingDate());
        return stopLoss;
    }

    private boolean triggerTrailingLoss(BuyOrder buyOrder, SellOrder sellOrder) {
        BuyOrder buyAtMaxOrder = new BuyOrder(buyOrder.getStockName(), DailyQuote.withClosePrice(maxClosePrice), buyOrder.getNumberOfShares());
        BuySellOperation buySellOperation2 = new BuySellOperation(buyAtMaxOrder, sellOrder);
        boolean trailingLoss = buySellOperation2.profitPctg().compareTo(this.trailingLoss) <= 0;
        if (trailingLoss) {
            this.maxClosePrice = 0.0;
//            System.out.println("Stop trailing lost triggered on "+ sellOrder.getTradingDate());
        }
        return trailingLoss;
    }

    private void trackMaxClosePrice(DailyQuote dailyQuote) {
        double currentClosePrice = dailyQuote.getClosePrice();
        if (currentClosePrice > maxClosePrice) {
            maxClosePrice = currentClosePrice;
        }
    }

    @Override
    public String sellDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(), "Stop Loss: ", this.stopLoss, "Trailing Loss: ", this.trailingLoss);
    }
}
