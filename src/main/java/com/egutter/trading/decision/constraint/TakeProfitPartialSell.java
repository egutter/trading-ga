package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.OrderExtraInfo;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.PortfolioAsset;
import com.egutter.trading.stock.StockPrices;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;

import java.util.Optional;

public class TakeProfitPartialSell implements SellTradingDecision {

    private final Portfolio portfolio;
    private final StockPrices stockPrices;

    public TakeProfitPartialSell(Portfolio portfolio, StockPrices stockPrices) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        if (!portfolio.hasStock(stockPrices.getStockName())) {
            return DecisionResult.NO;
        }
        PortfolioAsset portFolioAsset = portfolio.getPortFolioAsset(stockPrices.getStockName());
        BuyOrder buyOrder = portFolioAsset.getBuyOrder();
        if (buyOrder.hasExtraInfo()) {
            OrderExtraInfo buyExtraInfo = buyOrder.getOrderExtraInfo();
            Optional<Pair<Double, Double>> closeTargetPrice = buyExtraInfo.getNextTriggerSellPrice();
            if (!closeTargetPrice.isPresent()) return DecisionResult.NO;
            DailyQuote dailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
            Pair<Double, Double> targetPriceAndPercentage = closeTargetPrice.get();
            Double targetPrice = targetPriceAndPercentage.getFirst();
            if (targetPrice > dailyQuote.getHighPrice()) {
                return DecisionResult.NO;
            }
            buyExtraInfo.removeFirstTriggerSellPrice();
            OrderExtraInfo sellExtraInfo = new OrderExtraInfo();
            int numberOfShares = (int) Math.round(portFolioAsset.getNumberOfShares() * targetPriceAndPercentage.getSecond());
            sellExtraInfo.addNumberOfShares(numberOfShares);
            if (dailyQuote.getOpenPrice() >= targetPrice) {
                sellExtraInfo.addSellPrice(dailyQuote.getOpenPrice());
            } else {
                sellExtraInfo.addSellPrice(targetPrice);
            }
            return DecisionResult.yesWithExtraInfo(sellExtraInfo);
        }
        return DecisionResult.NO;
    }

    @Override
    public String sellDecisionToString() {
        return this.getClass().getSimpleName();
    }
}
