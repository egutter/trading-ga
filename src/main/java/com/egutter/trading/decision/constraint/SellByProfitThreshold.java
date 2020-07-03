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

/**
 * Created by egutter on 8/2/15.
 */
public class SellByProfitThreshold implements SellTradingDecision {
    private final Portfolio portfolio;
    private final StockPrices stockPrices;
    private final BigDecimal profitThreshold;

    public SellByProfitThreshold(Portfolio portfolio, StockPrices stockPrices, BigDecimal profitThreshold) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
        this.profitThreshold = profitThreshold;
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        if (portfolio.hasStock(stockPrices.getStockName())) {
            BuyOrder buyOrder = portfolio.getPortFolioAsset(stockPrices.getStockName()).getBuyOrder();
            DailyQuote dailyQuote = stockPrices.dailyPriceOn(tradingDate).get();
            SellOrder sellOrder = new SellOrder(buyOrder.getStockName(), dailyQuote, buyOrder.getNumberOfShares());
            BuySellOperation buySellOperation = new BuySellOperation(buyOrder, sellOrder);
            if (buySellOperation.profitPctg().compareTo(profitThreshold) >= 0) {
                return DecisionResult.YES;
            }
        }
        return DecisionResult.NEUTRAL;
    }

    @Override
    public String sellDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(), this.profitThreshold);
    }
}
