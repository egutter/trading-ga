package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.technicalanalysis.MovingAverageCrossOver;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import com.google.common.collect.MapMaker;
import com.tictactec.ta.lib.MAType;
import org.uncommons.maths.binary.BitString;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Created by egutter on 2/12/14.
 */
public class MovingAverageCrossOverGenerator implements BuyTradingDecisionGenerator, SellTradingDecisionGenerator, ConditionalOrderConditionGenerator {

    private final int fastMovingAverageDays;
    private final int slowMovingAverageDays;
    private final MAType fastMovingAverageType;
    private final MAType slowMovingAverageType;

    private static final transient ConcurrentMap<String, MovingAverageCrossOver> cache = new MapMaker().weakKeys().makeMap();
    private BitString chromosome;

    public static void main(String[] args) {
        StockPrices stocks = new StockPrices("stock");
        stocks.addAll(Arrays.asList(DailyQuote.empty()));
        String macd = new MovingAverageCrossOverGenerator(new BitString("0000000000000")).generateCondition(stocks).toString();
        System.out.println(macd);

        macd = new MovingAverageCrossOverGenerator(new BitString("1111111111111")).generateCondition(stocks).toString();
        System.out.println(macd);

        macd = new MovingAverageCrossOverGenerator(new BitString("1111111100000")).generateCondition(stocks).toString();
        System.out.println(macd);

        macd = new MovingAverageCrossOverGenerator(new BitString("0001000000011")).generateCondition(stocks).toString();
        System.out.println(macd);
    }

    public MovingAverageCrossOverGenerator(BitString chromosome) {
        this.chromosome = chromosome;
        this.fastMovingAverageDays = generateFastMovingAvgDays(chromosome);
        this.fastMovingAverageType = generateFastMovingAvgType(chromosome);
        this.slowMovingAverageDays = generateSlowMovingAvgDays(chromosome);
        this.slowMovingAverageType = generateSlowMovingAvgType(chromosome);
    }


    @Override
    public Function<TimeFrameQuote, Boolean> generateCondition(StockPrices stockPrices) {
        return generateMovingAverageCrossOver(stockPrices);
    }

    /**
     * Bits
     * 0 => Fast MovAvg Type (0: SMA, 1: EMA)
     * 1-6 => Fast MovAvg Days (1-64)
     * 7 => Slow MovAvg Type (0: SMA, 1: EMA)
     * 8-12 => Fast MovAvg Days (Slow+ - 191)
     *
     * @param stockPrices
     * @return
     */
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return generateMovingAverageCrossOver(stockPrices);
    }


    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return generateMovingAverageCrossOver(stockPrices);
    }

    private MovingAverageCrossOver generateMovingAverageCrossOver(StockPrices stockPrices) {
        String key = stockPrices.getStockName() + chromosome.toString();
        MovingAverageCrossOver movAvgCrossOver = cache.get(key);
        if (movAvgCrossOver == null) {
            movAvgCrossOver = new MovingAverageCrossOver(stockPrices,
                    this.fastMovingAverageDays,
                    this.slowMovingAverageDays,
                    this.fastMovingAverageType,
                    this.slowMovingAverageType);
            cache.put(key, movAvgCrossOver);
        }
        return movAvgCrossOver;
    }


    private MAType generateFastMovingAvgType(BitString chromosome) {
        return getMaType(chromosome, 0, 1);
    }

    private MAType generateSlowMovingAvgType(BitString chromosome) {
        return getMaType(chromosome, 7, 8);
    }

    private MAType getMaType(BitString chromosome, int start, int end) {
        double type = new BitString(chromosome.toString().substring(start, end)).toNumber().intValue();
        return type == 0 ? MAType.Sma : MAType.Ema;
    }

    private int generateFastMovingAvgDays(BitString chromosome) {
        return new BitString(chromosome.toString().substring(1, 7)).toNumber().intValue() + 1;
    }

    private int generateSlowMovingAvgDays(BitString chromosome) {
        int days = (new BitString(chromosome.toString().substring(8, 13)).toNumber().intValue() + 1) * 6;
        if (days <= this.fastMovingAverageDays) return days + this.fastMovingAverageDays;
        return days;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MovingAverageCrossOverGenerator.class.getSimpleName() + "[", "]")
                .add("fastMovingAverageDays=" + fastMovingAverageDays)
                .add("slowMovingAverageDays=" + slowMovingAverageDays)
                .add("fastMovingAverageType=" + fastMovingAverageType)
                .add("slowMovingAverageType=" + slowMovingAverageType)
                .toString();
    }
}
