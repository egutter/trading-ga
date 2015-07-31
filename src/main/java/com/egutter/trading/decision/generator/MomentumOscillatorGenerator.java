package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.technicalanalysis.MomentumOscillator;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Range;
import org.uncommons.maths.binary.BitString;

import java.util.concurrent.ConcurrentMap;

import static com.google.common.collect.Range.atLeast;
import static com.google.common.collect.Range.atMost;

/**
 * Created by egutter on 2/12/14.
 */
public class MomentumOscillatorGenerator<T extends MomentumOscillator> implements BuyTradingDecisionGenerator, SellTradingDecisionGenerator {

    private final Range<Double> buyThreshold;
    private final int days;
    private final Range<Double> sellThreshold;

    private static final transient ConcurrentMap<String, MomentumOscillator> cache = new MapMaker().weakKeys().makeMap();
    private Class<T> momentumOscillatorClass;
    private BitString chromosome;


    public MomentumOscillatorGenerator(Class<T> momentumOscillatorClass, BitString chromosome) {
        this.momentumOscillatorClass = momentumOscillatorClass;
        this.chromosome = chromosome;
        this.buyThreshold = generateBuyThreshold(chromosome);
        this.sellThreshold = generateSellThreshold(chromosome);
        this.days = generateDays(chromosome);
    }

    /**
     * Bits
     * 0 => Buy Threshold range style (0: at least, 1: at most)
     * 1-4 => Buy Threshold value from 5 to 95 by 5 steps
     * 5 => Sell Threshold range style (0: at least, 1: at most)
     * 6-9 => Sell Threshold value from 5 to 95 by 5 steps
     * 10-12 => Days from 10 to 17
     *
     * @param stockPrices
     * @return
     */
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return generateMomentumOscillator(stockPrices);
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return generateMomentumOscillator(stockPrices);
    }

    private MomentumOscillator generateMomentumOscillator(StockPrices stockPrices) {
        try {
            String key = momentumOscillatorClass.getName() + stockPrices.getStockName() + chromosome.toString();
            MomentumOscillator momentumOscillator = cache.get(key);
            if (momentumOscillator == null) {
                    momentumOscillator = momentumOscillatorClass.getConstructor(StockPrices.class, Range.class, Range.class, Integer.class).newInstance(stockPrices,
                            this.buyThreshold,
                            this.sellThreshold,
                            this.days);

                cache.put(key, momentumOscillator);
            }
            return momentumOscillator;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private int generateDays(BitString chromosome) {
        int days = new BitString(chromosome.toString().substring(10, 13)).toNumber().intValue();
        return days + daysBase();
    }

    protected int daysBase() {
        return 10;
    }

    private Range<Double> generateBuyThreshold(BitString chromosome) {
        return generateThreshold(chromosome, 1, 5, chromosome.getBit(12));
    }

    private Range<Double> generateSellThreshold(BitString chromosome) {
        return generateThreshold(chromosome, 6, 10, chromosome.getBit(7));
    }

    private Range<Double> generateThreshold(BitString chromosome, int start, int end, boolean atMostRange) {
        int index = new BitString(chromosome.toString().substring(start, end)).toNumber().intValue();
        int times = 1 + index;
        double thresholdValue = (thresholdStep() * times) + thresholdOffset(index);

        if (atMostRange) {
            return atMost(thresholdValue);
        }
        return atLeast(thresholdValue);
    }

    protected int thresholdOffset(int index) {
        if (index < 9) return 0;
        return 15;
    }

    protected int thresholdStep() {
        return 5;
    }
}
