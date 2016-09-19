package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

import java.util.Optional;

/**
 * Created by egutter on 2/12/14.
 */
public class OnlyBuyWhenStockPriceFalls implements BuyTradingDecision {


    private StockPrices stockPrices;

    public OnlyBuyWhenStockPriceFalls(StockPrices stockPrices) {
        this.stockPrices = stockPrices;
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        Optional<DailyQuote> currentQuote = stockPrices.dailyPriceOn(tradingDate);
        Optional<DailyQuote> previousQuote = stockPrices.dailyPriceBefore(tradingDate, 1);
        if (!currentQuote.isPresent() || !previousQuote.isPresent()) return DecisionResult.NO;

        double currentPrice = currentQuote.get().getAdjustedClosePrice();
        double previousPrice = previousQuote.get().getAdjustedClosePrice();
        if (previousPrice <= currentPrice) {
            return DecisionResult.NO;
        }

        return DecisionResult.NEUTRAL;
    }

    @Override
    public String buyDecisionToString() {
        return null;
    }

    @Override
    public LocalDate startOn() {
        return LocalDate.now();
    }

}
