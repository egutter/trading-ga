package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.technicalanalysis.macd.MacdStats;
import com.egutter.trading.decision.technicalanalysis.macd.SignChange;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.math.DoubleMath;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;
import org.uncommons.maths.Maths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 2/10/14.
 */
public class MovingAverageConvergenceDivergence implements BuyTradingDecision, SellTradingDecision {

    private final StockPrices stockPrices;
    private Map<LocalDate, MacdStats> macd;

    private final TradeSignal sellSignal;
    private TradeSignal buySignal;

    public static void main(String[] args) {
        StockMarket stockMarket = new StockMarketBuilder().build(new LocalDate(2012, 1, 1), new LocalDate(2014, 12, 31));
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        List<Double> means = new ArrayList<Double>();
        for (StockPrices stockPrices : stockMarket.getStockPrices()) {
            System.out.println("Stock " + stockPrices.getStockName());
            MovingAverageConvergenceDivergence bb = new MovingAverageConvergenceDivergence(stockPrices, new TradeSignal(SignChange.NEGATIVE, Range.atLeast(0.5)), new TradeSignal(SignChange.POSITIVE, Range.atLeast(0.5)));
            Map<LocalDate, MacdStats> indexes = bb.getMacd();
            Iterable<Double> divergenceValues = Iterables.transform(indexes.values(), getDivergence());
            maxes.add(Ordering.natural().max(divergenceValues));
            minis.add(Ordering.natural().min(divergenceValues));
            means.add(DoubleMath.mean(divergenceValues));
        }
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
        System.out.println("Mean value " + DoubleMath.mean(means));
    }

    private static Function<MacdStats, Double> getDivergence() {
        return new Function<MacdStats, Double>() {
            @Override
            public Double apply(MacdStats macdStats) {
                return macdStats.getDifferenceWithPreviousDay();
            }
        };
    }

    public MovingAverageConvergenceDivergence(StockPrices stockPrices,
                                              TradeSignal buySignal,
                                              TradeSignal sellSignal) {
        this.stockPrices = stockPrices;
        this.buySignal = buySignal;
        this.sellSignal = sellSignal;
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        return shouldTradeOn(tradingDate, buySignal);
    }

    @Override
    public DecisionResult shouldSellOn(LocalDate tradingDate) {
        return shouldTradeOn(tradingDate, sellSignal);
    }

    private DecisionResult shouldTradeOn(LocalDate tradingDate, TradeSignal tradeSignal) {
        if (!getMacd().containsKey(tradingDate)) {
            return DecisionResult.NEUTRAL;
        }
        MacdStats macdStats = getMacd().get(tradingDate);

        return tradeSignal.shouldTrade(macdStats.signChange(), macdStats.getDifferenceWithPreviousDay());
    }

    private synchronized Map<LocalDate, MacdStats> getMacd() {
        if (this.macd == null) {
            calculateMacd();
        }
        return this.macd;
    }

    private void calculateMacd() {
        this.macd = new HashMap<LocalDate, MacdStats>();
        List<Double>closePrices = stockPrices.getAdjustedClosePrices();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outMacd[] = new double[closePrices.size()];
        double outMacdSignal[] = new double[closePrices.size()];
        double outMacdHist[] = new double[closePrices.size()];
        double[] closePricesArray = Doubles.toArray(closePrices);
        CoreAnnotated taCore = new CoreAnnotated();
        int macdLookback = taCore.macdLookback(fastPeriod(), slowPeriod(), signalPeriod());
        RetCode returnCode = taCore.macd(startIndex(),
                endIndex(closePrices),
                closePricesArray,
                fastPeriod(),
                slowPeriod(),
                signalPeriod(),
                outBegIdx,
                outNBElement,
                outMacd,
                outMacdSignal,
                outMacdHist);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating MACD " + returnCode);
        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();

        double lastDay = outMacdHist[0];
        for (int index = 1; index < outNBElement.value; index++) {
            int daysOffset = macdLookback + index;
            LocalDate tradingDate = tradingDates.get(daysOffset);
            this.macd.put(tradingDate, new MacdStats(outMacd[index], outMacdSignal[index], outMacdHist[index], lastDay));
            lastDay = outMacdHist[index];
        }
    }

    private int signalPeriod() {
        return 5;
    }

    private int slowPeriod() {
        return 35;
    }

    public int fastPeriod() {
        return 5;
    }

    public TradeSignal getBuySignal() {
        return buySignal;
    }

    public TradeSignal getSellSignal() {
        return sellSignal;
    }

    private int endIndex(List<Double> closePrices) {
        return closePrices.size() - 1;
    }

    private int startIndex() {
        return 0;
    }

    @Override
    public String buyDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "buy signal",
                this.getBuySignal());
    }


    @Override
    public String sellDecisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "sell signal",
                sellSignal);
    }

}
