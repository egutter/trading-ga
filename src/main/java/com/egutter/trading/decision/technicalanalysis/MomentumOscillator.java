package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import com.google.common.collect.Range;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;

/**
 * Created by egutter on 3/1/15.
 */
public abstract class MomentumOscillator implements BuyTradingDecision, SellTradingDecision {

    protected final StockPrices stockPrices;
    protected final Range sellThreshold;
    protected Map<LocalDate, Double> momentumOscillatorIndex;

    protected Range buyThreshold;
    protected int days;

    public MomentumOscillator(StockPrices stockPrices,
                                 Range buyThreshold,
                                 Range sellThreshold,
                                 int days) {
        this.stockPrices = stockPrices;
        this.buyThreshold = buyThreshold;
        this.sellThreshold = sellThreshold;
        this.days = days;
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        return shouldTradeOn(tradingDate, buyThreshold);
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        return shouldTradeOn(tradingDate, sellThreshold);
    }

    private DecisionResult shouldTradeOn(LocalDate tradingDate, Range tradeThreshold) {
        if (!getMomentumOscillatorIndex().containsKey(tradingDate)) {
            return DecisionResult.NEUTRAL;
        }
        Double mfiAtDay = getMomentumOscillatorIndex().get(tradingDate);
        if (tradeThreshold.contains(mfiAtDay)) {
            return DecisionResult.YES;
        }
        return DecisionResult.NO;
    }

    protected synchronized Map<LocalDate, Double> getMomentumOscillatorIndex() {
        if (this.momentumOscillatorIndex == null) {
            this.momentumOscillatorIndex = calculateMomentumOscillatorIndex();
        }
        return this.momentumOscillatorIndex;
    }

    protected abstract Map<LocalDate, Double> calculateMomentumOscillatorIndex();

    @Override
    public String buyDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "buy threshold",
                this.getBuyThreshold(),
                "days",
                this.days);
    }


    @Override
    public String sellDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "sell threshold",
                this.getSellThreshold(),
                "days",
                this.days);
    }

    public int days() {
        return this.days;
    }

    public Range<Double> getBuyThreshold() {
        return buyThreshold;
    }

    public Range<Double> getSellThreshold() {
        return sellThreshold;
    }

    protected int endIndex(List<Double> closePrices) {
        return closePrices.size() - 1;
    }

    protected int startIndex() {
        return 0;
    }

}
