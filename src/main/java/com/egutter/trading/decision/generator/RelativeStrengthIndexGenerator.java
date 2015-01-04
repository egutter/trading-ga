package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.technicalanalysis.MoneyFlowIndex;
import com.egutter.trading.decision.technicalanalysis.RelativeStrengthIndex;
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
public class RelativeStrengthIndexGenerator implements BuyTradingDecisionGenerator, SellTradingDecisionGenerator {

    private final Range<Double> buyThreshold;
    private final Range<Double> sellThreshold;
    private final int days;

    private static final transient ConcurrentMap<String, RelativeStrengthIndex> cache = new MapMaker().weakKeys().makeMap();
    private BitString chromosome;


    public RelativeStrengthIndexGenerator(BitString chromosome) {
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
        return generateRelativeStrengthIndex(stockPrices);
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return generateRelativeStrengthIndex(stockPrices);
    }

    private RelativeStrengthIndex generateRelativeStrengthIndex(StockPrices stockPrices) {
        String key = stockPrices.getStockName() + chromosome.toString();
        RelativeStrengthIndex relativeStrengthIndex = cache.get(key);
        if (relativeStrengthIndex == null) {
            relativeStrengthIndex = new RelativeStrengthIndex(stockPrices,
                    this.buyThreshold,
                    this.sellThreshold,
                    this.days);
            cache.put(key, relativeStrengthIndex);
        }
        return relativeStrengthIndex;
    }

    private int generateDays(BitString chromosome) {
        int days = new BitString(chromosome.toString().substring(10, 13)).toNumber().intValue();
        return days + 10;
    }

    private Range<Double> generateBuyThreshold(BitString chromosome) {
        return generateThreshold(chromosome, 1, 5, chromosome.getBit(12));
    }

    private Range<Double> generateSellThreshold(BitString chromosome) {
        return generateThreshold(chromosome, 6, 10, chromosome.getBit(7));
    }

    private Range<Double> generateThreshold(BitString chromosome, int start, int end, boolean atMostRange) {
        int index = new BitString(chromosome.toString().substring(start, end)).toNumber().intValue();
        int step = 5;
        int times = 1 + index;
        int offset = 0;
        if (index >= 9) {
            offset = 15;
        }
        double thresholdValue = (step * times) + offset;

        if (atMostRange) {
            return atMost(thresholdValue);
        }
        return atLeast(thresholdValue);
    }
}
