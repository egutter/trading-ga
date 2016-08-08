package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.technicalanalysis.BollingerBands;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Range;
import com.tictactec.ta.lib.MAType;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentMap;

import static com.google.common.collect.Range.atLeast;
import static com.google.common.collect.Range.atMost;
import static com.google.common.primitives.Doubles.min;

/**
 * Created by egutter on 2/12/14.
 */
public class BollingerBandsGenerator implements BuyTradingDecisionGenerator, SellTradingDecisionGenerator {

    private final Range<Double> buyThreshold;
    private final Range<Double> sellThreshold;
    private final int movingAverageDays;
    private final MAType movingAverageType;

    private static final transient ConcurrentMap<String, BollingerBands> cache = new MapMaker().weakKeys().makeMap();
    private BitString chromosome;

    public BollingerBandsGenerator(BitString chromosome) {
        this.chromosome = chromosome;
        this.buyThreshold = generateBuyThreshold(chromosome);
        this.sellThreshold = generateSellThreshold(chromosome);
        this.movingAverageDays = generateMovingAverageDays(chromosome);
        this.movingAverageType = generateMovingAverageType();
    }

    /**
     * Bits
     * 0 => Buy Threshold range style (0: at least, 1: at most)
     * 1-4 => Buy Threshold value from -2 to 2 by 0,25 steps
     * 5 => Sell Threshold range style (0: at least, 1: at most)
     * 6-9 => Sell Threshold value from -2 to 2 by 0,25 steps
     * 10-12 => Moving Average Days from 15 to 22
     *
     * @param stockPrices
     * @return
     */
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return generateBollingerBands(stockPrices);
    }


    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return generateBollingerBands(stockPrices);
    }

    private BollingerBands generateBollingerBands(StockPrices stockPrices) {
        String key = stockPrices.getStockName() + chromosome.toString();
        BollingerBands bbands = cache.get(key);
        if (bbands == null) {
            bbands = new BollingerBands(stockPrices,
                    this.buyThreshold,
                    this.sellThreshold,
                    this.movingAverageDays,
                    this.movingAverageType);
            cache.put(key, bbands);
        }
        return bbands;
    }

    private MAType generateMovingAverageType() {
        return MAType.Wma;
    }

    private int generateMovingAverageDays(BitString chromosome) {
        int days = new BitString(chromosome.toString().substring(10, 13)).toNumber().intValue();
        return days + 15;
    }

    private Range<Double> generateBuyThreshold(BitString chromosome) {
        return generateThreshold(chromosome, 1, 5, chromosome.getBit(12));
    }

    private Range<Double> generateSellThreshold(BitString chromosome) {
        return generateThreshold(chromosome, 6, 10, chromosome.getBit(7));
    }

    private Range<Double> generateThreshold(BitString chromosome, int start, int end, boolean atMostRange) {
        int index = new BitString(chromosome.toString().substring(start, end)).toNumber().intValue();
        double adjustBy = 2.0;
        if (index >= 8) {
            adjustBy -= 0.25;
        }
        double thresholdValue = truncate(min((index * 0.25) - adjustBy, 2.0));

        if (atMostRange) {
            return atMost(thresholdValue);
        }
        return atLeast(thresholdValue);
    }

    private double truncate(double value) {
        int roundMethod = (value > 0) ? BigDecimal.ROUND_FLOOR : BigDecimal.ROUND_CEILING;
        return new BigDecimal(String.valueOf(value)).setScale(2, roundMethod).doubleValue();
    }
}
