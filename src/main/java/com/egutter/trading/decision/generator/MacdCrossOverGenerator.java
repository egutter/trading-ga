package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.technicalanalysis.MacdCrossOver;
import com.egutter.trading.decision.technicalanalysis.MovingAverageCrossOver;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import com.google.common.collect.MapMaker;
import com.tictactec.ta.lib.MAType;
import org.uncommons.maths.binary.BitString;

import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Created by egutter on 2/12/14.
 */
public class MacdCrossOverGenerator implements BuyTradingDecisionGenerator, SellTradingDecisionGenerator, ConditionalOrderConditionGenerator {

    private final int fastMovingAverageDays;
    private final int slowMovingAverageDays;
    private final int signalDays;

    private static final transient ConcurrentMap<String, MacdCrossOver> cache = new MapMaker().weakKeys().makeMap();
    private BitString chromosome;

    public static void main(String[] args) {
        StockPrices stocks = new StockPrices("stock");
        stocks.addAll(Arrays.asList(DailyQuote.empty()));
        String macd = new MacdCrossOverGenerator(new BitString("0000000000000")).generateCondition(stocks).toString();
        System.out.println(macd);

        macd = new MacdCrossOverGenerator(new BitString("1111111111111")).generateCondition(stocks).toString();
        System.out.println(macd);

        macd = new MacdCrossOverGenerator(new BitString("1111111100000")).generateCondition(stocks).toString();
        System.out.println(macd);

        macd = new MacdCrossOverGenerator(new BitString("0001000000011")).generateCondition(stocks).toString();
        System.out.println(macd);
    }

    public MacdCrossOverGenerator(BitString chromosome) {
        this.chromosome = chromosome;
        this.fastMovingAverageDays = generateFastMovingAvgDays(chromosome);
        this.slowMovingAverageDays = generateSlowMovingAvgDays(chromosome);
        this.signalDays = generateSignalDays(chromosome);
    }

    @Override
    public Function<TimeFrameQuote, Boolean> generateCondition(StockPrices stockPrices) {
        return generateMacdCrossOver(stockPrices);
    }

    /**
     * Bits
     * 0-4 => Fast MovAvg Days (1-33)
     * 5-9 => Fast MovAvg Days (Slow+ - 65)
     * 10-12 => Signal 1 to 17
     *
     * @param stockPrices
     * @return
     */
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return generateMacdCrossOver(stockPrices);
    }


    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return generateMacdCrossOver(stockPrices);
    }

    private MacdCrossOver generateMacdCrossOver(StockPrices stockPrices) {
        String key = stockPrices.getStockName() + chromosome.toString();
        MacdCrossOver movAvgCrossOver = cache.get(key);
        if (movAvgCrossOver == null) {
            movAvgCrossOver = new MacdCrossOver(stockPrices,
                    this.fastMovingAverageDays,
                    this.slowMovingAverageDays,
                    this.signalDays);
            cache.put(key, movAvgCrossOver);
        }
        return movAvgCrossOver;
    }

    private int generateFastMovingAvgDays(BitString chromosome) {
        return new BitString(chromosome.toString().substring(0, 5)).toNumber().intValue() + 2;
    }

    private int generateSignalDays(BitString chromosome) {
        return new BitString(chromosome.toString().substring(10, 13)).toNumber().intValue() + 1;
    }

    private int generateSlowMovingAvgDays(BitString chromosome) {
        int days = (new BitString(chromosome.toString().substring(5, 10)).toNumber().intValue() + 2);
        if (days <= this.fastMovingAverageDays) return days + this.fastMovingAverageDays;
        return days;
    }

}
