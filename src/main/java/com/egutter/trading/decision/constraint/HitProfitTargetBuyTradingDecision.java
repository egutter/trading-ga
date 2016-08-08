package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.SellOrder;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Created by egutter on 12/19/15.
 */
public class HitProfitTargetBuyTradingDecision implements BuyTradingDecision {

    public static final BigDecimal AMOUNT_TO_INVEST = BigDecimal.valueOf(10000.00);
    private static final int MAX_DAYS_TO_WAIT = 10;

    private StockPrices stockPrices;
    private BigDecimal profitThreshold;

    public HitProfitTargetBuyTradingDecision(StockPrices stockPrices, BigDecimal profitThreshold) {
        this.stockPrices = stockPrices;
        this.profitThreshold = profitThreshold;
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        BuyOrder buyOrder = new BuyOrder(stockPrices.getStockName(), stockPrices.dailyPriceOn(tradingDate).get(), AMOUNT_TO_INVEST);

        boolean targetHit = false;
        List<DailyQuote> futureQuotes = stockPrices.dailyQuotesFromWithLimit(tradingDate, MAX_DAYS_TO_WAIT);
        for (DailyQuote dailyQuote: futureQuotes) {
            SellOrder sellOrder = new SellOrder(buyOrder.getStockName(), dailyQuote, buyOrder.getNumberOfShares());
            BuySellOperation buySellOperation = new BuySellOperation(buyOrder, sellOrder);
            if (buySellOperation.profitPctg().compareTo(profitThreshold) >= 0) {
                targetHit = true;
                break;
            }
        }
//        LocalDate sellOn = tradingDate.plusDays(1);
//        LocalDate lastTradingDay = stockPrices.getLastTradingDate();
//
//        for (int i = 1; i < MAX_DAYS_TO_WAIT || !targetHit; i++) {
//            sellOn = nextTradingDay(stockPrices, sellOn, lastTradingDay);
//            if (!sellOn.isAfter(lastTradingDay)) {
//                DailyQuote dailyQuote = stockPrices.dailyPriceOn(sellOn).get();
//                SellOrder sellOrder = new SellOrder(buyOrder.getStockName(), dailyQuote, buyOrder.getNumberOfShares());
//                BuySellOperation buySellOperation = new BuySellOperation(buyOrder, sellOrder);
//                if (buySellOperation.profitPctg().compareTo(profitThreshold) >= 0) {
//                    targetHit = true;
//                }
//            }
//
//        }
        if (targetHit) return DecisionResult.YES;

        return DecisionResult.NEUTRAL;
    }

    private LocalDate nextTradingDay(StockPrices stockPrices, LocalDate sellOn, LocalDate lastTradingDay) {
        Optional<DailyQuote> dailyQuoteOptional = stockPrices.dailyPriceOn(sellOn);
        boolean isPresent = false;
        while (!isPresent && !sellOn.isAfter(lastTradingDay)) {
            isPresent = dailyQuoteOptional.isPresent();
            if (!isPresent) {
                sellOn = sellOn.plusDays(1);
            }
        }
        return sellOn;
    }

    @Override
    public String buyDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(), profitThreshold);
    }

    @Override
    public LocalDate startOn() {
        return LocalDate.now();
    }
}
