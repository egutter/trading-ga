package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import org.joda.time.LocalDate;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public abstract class CrossOverOscillator implements BuyTradingDecision, SellTradingDecision {

    protected final StockPrices stockPrices;
    protected LocalDate startOnDate;

    public CrossOverOscillator(StockPrices stockPrices) {
        this.stockPrices = stockPrices;
        this.startOnDate = stockPrices.getFirstTradingDate();
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        // Index cross up Signal
        return shouldTradeOn(tradingDate, (yesterdayDiff) -> yesterdayDiff < 0, (todayDiff) -> todayDiff > 0);
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        // Signal cross down Index
        return shouldTradeOn(tradingDate, (yesterdayDiff) -> yesterdayDiff > 0, (todayDiff) -> todayDiff < 0);
    }

    private DecisionResult shouldTradeOn(LocalDate tradingDate,
                                         Function<Double, Boolean> calcYesterdayDiff,
                                         Function<Double, Boolean> calcTodayDiff) {
        Optional<DailyQuote> previousQuote = this.stockPrices.dailyPriceBefore(tradingDate, 1);
        if (!previousQuote.isPresent()) {
            return DecisionResult.NEUTRAL;
        }
        LocalDate yesterday = previousQuote.get().getTradingDate();
        Optional<Double> yesterdayD = getIndexAtDate(getSignalValues(), yesterday);
        if (!yesterdayD.isPresent()) {
            return DecisionResult.NEUTRAL;
        }

        Optional<Double> todayD = getIndexAtDate(getSignalValues(), tradingDate);
        Optional<Double> yesterdayK = getIndexAtDate(getIndexValues(), yesterday);
        Optional<Double> todayK = getIndexAtDate(getIndexValues(), tradingDate);

        boolean yesterdayDiff = calcYesterdayDiff.apply(yesterdayK.get() - yesterdayD.get());
        boolean todayDiff = calcTodayDiff.apply(todayK.get() - todayD.get());
        return (yesterdayDiff && todayDiff) ? DecisionResult.YES : DecisionResult.NO;
    }

    public abstract Map<LocalDate, Double> getIndexValues();

    public abstract Map<LocalDate, Double> getSignalValues();

    public Optional<Double> getIndexAtDate(Map<LocalDate, Double> indexValues, LocalDate tradingDate) {
        return Optional.ofNullable(indexValues.get(tradingDate));
    }

    @Override
    public abstract String buyDecisionToString();

    @Override
    public LocalDate startOn() {
        return this.startOnDate;
    }

    @Override
    public abstract String sellDecisionToString();
}
