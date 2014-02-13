package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BollingerBands;
import com.google.common.collect.Range;
import org.uncommons.maths.binary.BitString;

/**
 * Created by egutter on 2/12/14.
 */
public class BollingerBandsGenerator {
    /**
     * Bits
     * 0 => Active (1) or Inactive (0)
     * 1-5 => Buy Threshold value from -2 to 2 by 0,13 steps
     * 6 => Buy Threshold Operator (1: At Least; 0: AtMost)
     * 7-11 => Sell Threshold value from -2 to 2 by 0,13 steps
     * 12 => Sell Threshold Operator (1: At Least; 0: AtMost)
     * 13-17 => Moving Average Days from 1 to 33
     * 18-20 => Moving Average Type (0: Sma, 1: Ema, 2: Wma, 3: Dema, 4: Tema, 5: Trima, 6: Kama, 7: Mama)
     *
     * @param chromosome
     * @return
     */
    public BollingerBands generate(BitString chromosome) {
        boolean active = chromosome.getBit(0);
        Range buyThreshold = getBuyThreshold(chromosome);
        Range sellThreshold = getSellThreshold(chromosome);
        
    }

    private Range<Double> getBuyThreshold(BitString chromosome) {
        int index = new BitString(chromosome.toString().substring(1, 5)).toNumber().intValue();
        double buyThresholdValue = (index*0.13)-2;
        if (chromosome.getBit(6)) {
            return Range.atLeast(buyThresholdValue);
        } else {
            return Range.atMost(buyThresholdValue);
        }
    }
    private Range<Double> getSellThreshold(BitString chromosome) {
        int index = new BitString(chromosome.toString().substring(7, 11)).toNumber().intValue();
        double buyThresholdValue = (index*0.13)-2;
        if (chromosome.getBit(12)) {
            return Range.atLeast(buyThresholdValue);
        } else {
            return Range.atMost(buyThresholdValue);
        }
    }
}
