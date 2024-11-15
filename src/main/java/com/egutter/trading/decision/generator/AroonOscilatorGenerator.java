package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.technicalanalysis.AroonOscilator;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Range;
import org.uncommons.maths.binary.BitString;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import static com.google.common.collect.Range.atLeast;
import static com.google.common.collect.Range.atMost;

/**
 * Created by egutter on 2/12/14.
 */
public class AroonOscilatorGenerator implements BuyTradingDecisionGenerator, SellTradingDecisionGenerator, ConditionalOrderConditionGenerator {

    private final Range<Double> buyThreshold;
    private final Range<Double> sellThreshold;
    private final int days;

//    private static final Map<String, AroonOscilator> cache = new HashMap<>();
    private static final transient ConcurrentMap<String, AroonOscilator> cache = new MapMaker().weakKeys().makeMap();
    private BitString chromosome;

    public AroonOscilatorGenerator(BitString chromosome) {
        this.chromosome = chromosome;
        this.buyThreshold = generateBuyThreshold(chromosome);
        this.sellThreshold = generateSellThreshold(chromosome);
        this.days = generateDays(chromosome);
    }

    @Override
    public Function<TimeFrameQuote, Boolean> generateCondition(StockPrices stockPrices) {
        return generateAroonOscilator(stockPrices);
    }

    /**
     * Bits
     * 0 => Buy Threshold range style (0: at least, 1: at most)
     * 1-4 => Buy Threshold value from -90 to 90 by 10 steps skips -10,0,10
     * 5 => Sell Threshold range style (0: at least, 1: at most)
     * 6-9 => Sell Threshold value from -90 to 90 by 10 steps skips -10,0,10
     * 10-12 => Days from 21 to 28
     *
     * @param stockPrices
     * @return
     */
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return generateAroonOscilator(stockPrices);
    }


    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return generateAroonOscilator(stockPrices);
    }

    private AroonOscilator generateAroonOscilator(StockPrices stockPrices) {
        String key = stockPrices.getStockName() + chromosome.toString();
        AroonOscilator aroon = cache.get(key);
        if (aroon == null) {
            aroon = new AroonOscilator(stockPrices,
                    this.buyThreshold,
                    this.sellThreshold,
                    this.days);
            cache.put(key, aroon);
        }
        return aroon;
    }

    private int generateDays(BitString chromosome) {
        int days = new BitString(chromosome.toString().substring(10, 13)).toNumber().intValue();
        return days + 21;
    }

    private Range<Double> generateBuyThreshold(BitString chromosome) {
        return generateThreshold(chromosome, 1, 5, chromosome.getBit(12));
    }

    private Range<Double> generateSellThreshold(BitString chromosome) {
        return generateThreshold(chromosome, 6, 10, chromosome.getBit(7));
    }

    private Range<Double> generateThreshold(BitString chromosome, int start, int end, boolean atMostRange) {
        int index = new BitString(chromosome.toString().substring(start, end)).toNumber().intValue();
        int step = 10;
        int times = 1 + index;
        int offset = -100;
        if (index >= 9) {
            offset = -70;
        }
        double thresholdValue = (step * times) + offset;

        if (atMostRange) {
            return atMost(thresholdValue);
        }
        return atLeast(thresholdValue);
    }

}
