package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.technicalanalysis.StochasticOscillator;
import com.egutter.trading.decision.technicalanalysis.StochasticOscillatorThreshold;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Range;
import org.uncommons.maths.binary.BitString;

import java.util.concurrent.ConcurrentMap;

public class StochasticOscillatorThresholdGenerator implements BuyTradingDecisionGenerator {

    private static final transient ConcurrentMap<String, StochasticOscillatorThreshold> cache = new MapMaker().weakKeys().makeMap();

    private final Range buyThreshold;
    private StochasticOscillator oscillator;
    private BitString chromosome;

    public static void main(String[] args) {
        System.out.println(" range " + Range.atMost(new BitString("1111111").toNumber().intValue() + 1));
        System.out.println(" range " + Range.atMost(20));
    }
    public StochasticOscillatorThresholdGenerator(StochasticOscillator oscillator, BitString chromosome) {
        this.oscillator = oscillator;
        this.chromosome = chromosome;
        this.buyThreshold = generateBuyThreshold(chromosome);
    }

    /**
     * Bits
     * 0-6 => Buy Threshold (1-129)
     * 8-12 => not used
     *
     * @param stockPrices
     * @return
     */
    @Override
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        String key = stockPrices.getStockName() + chromosome.toString();
        StochasticOscillatorThreshold stochOsc = cache.get(key);
        if (stochOsc == null) {
            stochOsc = new StochasticOscillatorThreshold(oscillator, buyThreshold);
            cache.put(key, stochOsc);
        }
        return stochOsc;
    }

    private Range generateBuyThreshold(BitString chromosome) {
        double value = new BitString(chromosome.toString().substring(0, 7)).toNumber().doubleValue() + 1;
        return Range.atMost(value);
    }

}
