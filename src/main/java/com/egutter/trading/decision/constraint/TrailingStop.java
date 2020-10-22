package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.OrderExtraInfo;
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
    private double maxHighPrice = 0.0;

    public TrailingStop(Portfolio portfolio, StockPrices stockPrices, BigDecimal stopLoss, BigDecimal trailingLoss) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
        this.stopLoss = stopLoss.abs().negate();
        this.trailingLoss = trailingLoss.abs().negate();
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        if (!portfolio.hasStock(stockPrices.getStockName())) {
            this.maxHighPrice = 0.0;
            return DecisionResult.NEUTRAL;
        }
        BuyOrder buyOrder = portfolio.getPortFolioAsset(stockPrices.getStockName()).getBuyOrder();
        DailyQuote dailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
        trackMaxClosePrice(dailyQuote);

        DecisionResult stopLossDecision = triggerStopLoss(buyOrder, dailyQuote);
        if (stopLossDecision.equals(DecisionResult.YES)) return stopLossDecision;

        DecisionResult trailingDecision = triggerTrailingStopLoss(dailyQuote);
        if (trailingDecision.equals(DecisionResult.YES)) return trailingDecision;

        return DecisionResult.NEUTRAL;
    }

    private DecisionResult triggerStopLoss(BuyOrder buyOrder, DailyQuote dailyQuote) {
        BigDecimal paidPrice = BigDecimal.valueOf(buyOrder.getPricePaid().doubleValue());
        return stopLossDecision(paidPrice, dailyQuote, this.stopLoss);
    }

    private DecisionResult triggerTrailingStopLoss(DailyQuote dailyQuote) {
        return stopLossDecision(BigDecimal.valueOf(maxHighPrice), dailyQuote, this.trailingLoss);
    }

    private DecisionResult stopLossDecision(BigDecimal basePrice, DailyQuote dailyQuote, BigDecimal stopPercentage) {
        BigDecimal threshold = BigDecimal.ONE.add(stopPercentage.divide(BigDecimal.valueOf(100))).multiply(basePrice);
        BigDecimal lowPrice = BigDecimal.valueOf(dailyQuote.getLowPrice());
        BigDecimal openPrice = BigDecimal.valueOf(dailyQuote.getOpenPrice());

        if (lowPrice.compareTo(threshold) < 0) {
            OrderExtraInfo extraInfo = new OrderExtraInfo();
            if (openPrice.compareTo(threshold) < 0) {
                extraInfo.addSellPrice(openPrice.doubleValue());
            } else {
                extraInfo.addSellPrice(threshold.doubleValue());
            }
            return DecisionResult.yesWithExtraInfo(extraInfo);
        }
        return DecisionResult.NEUTRAL;
    }

    private boolean triggerStopLoss2(BuyOrder buyOrder, SellOrder sellOrder) {
        BuySellOperation buySellOperation = new BuySellOperation(buyOrder, sellOrder);
        if (!buySellOperation.isLost()) return false;

        boolean stopLoss = buySellOperation.profitPctg().compareTo(this.stopLoss) <= 0;
//        if (stopLoss) System.out.println("Stop Loss triggered on "+ sellOrder.getTradingDate());
        return stopLoss;
    }

    private boolean triggerTrailingLoss(BuyOrder buyOrder, SellOrder sellOrder) {
        BuyOrder buyAtMaxOrder = new BuyOrder(buyOrder.getStockName(), DailyQuote.withClosePrice(maxHighPrice), buyOrder.getNumberOfShares(), BigDecimal.ZERO);
        BuySellOperation buySellOperation2 = new BuySellOperation(buyAtMaxOrder, sellOrder);
        boolean trailingLoss = buySellOperation2.profitPctg().compareTo(this.trailingLoss) <= 0;
        if (trailingLoss) {
            this.maxHighPrice = 0.0;
//            System.out.println("Stop trailing lost triggered on "+ sellOrder.getTradingDate());
        }
        return trailingLoss;
    }

    private void trackMaxClosePrice(DailyQuote dailyQuote) {
        double currentHighPrice = dailyQuote.getHighPrice();
        if (currentHighPrice > maxHighPrice) {
            maxHighPrice = currentHighPrice;
        }
    }

    @Override
    public String sellDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(), "Stop Loss: ", this.stopLoss, "Trailing Loss: ", this.trailingLoss);
    }
}
