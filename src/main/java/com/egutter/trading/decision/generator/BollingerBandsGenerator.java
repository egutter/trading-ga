package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BollingerBands;
import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Range;
import com.tictactec.ta.lib.MAType;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;

import static com.google.common.primitives.Doubles.min;

/**
 * Created by egutter on 2/12/14.
 */
public class BollingerBandsGenerator implements TradingDecisionGenerator {

    private final Range<Double> buyThreshold;
    private final Range<Double> sellThreshold;
    private final int movingAverageDays;
    private final MAType movingAverageType;


    public BollingerBandsGenerator(BitString chromosome) {
        this.buyThreshold = generateBuyThreshold(chromosome);
        this.sellThreshold = generateSellThreshold(chromosome);
        this.movingAverageDays = generateMovingAverageDays(chromosome);
        this.movingAverageType = generateMovingAverageType(chromosome);
    }

    /**
     * Bits
     * 0 => Active (1) or Inactive (0)
     * 1-5 => Buy Threshold value from -2 to 2 by 0,13 steps
     * 6 => Buy Threshold Operator (1: At Least; 0: AtMost)
     * 7-11 => Sell Threshold value from -2 to 2 by 0,13 steps
     * 12 => Sell Threshold Operator (1: At Least; 0: AtMost)
     * 13-17 => Moving Average Days from 1 to 32
     * 18-20 => Moving Average Type (0: Sma, 1: Ema, 2: Wma, 3: Dema, 4: Tema, 5: Trima, 6: Kama, 7: Mama)
     *
     *
     * @param stockPrices
     * @return
     */
    public TradingDecision generate(StockPrices stockPrices) {
        return new BollingerBands(stockPrices,
                this.buyThreshold,
                this.sellThreshold,
                this.movingAverageDays,
                this.movingAverageType);
    }

    private  MAType generateMovingAverageType(BitString chromosome) {
        int typeNumber = new BitString(chromosome.toString().substring(18, 21)).toNumber().intValue();
        switch (typeNumber) {
            case 0:
                return MAType.Sma;
            case 1:
                return MAType.Ema;
            case 2:
                return MAType.Wma;
            case 3:
                return MAType.Dema;
            case 4:
                return MAType.Tema;
            case 5:
                return MAType.Trima;
            case 6:
                return MAType.Kama;
            case 7:
                return MAType.Mama;
            default:
                return MAType.Sma;
        }

    }

    private  int generateMovingAverageDays(BitString chromosome) {
        int days = new BitString(chromosome.toString().substring(13, 18)).toNumber().intValue();
        return days + 2;
    }

    private  Range<Double> generateBuyThreshold(BitString chromosome) {
        return getThresholdRange(chromosome, 1, 7);
    }

    private double truncate(double value) {
        int roundMethod = (value > 0) ? BigDecimal.ROUND_FLOOR : BigDecimal.ROUND_CEILING;
        return new BigDecimal(String.valueOf(value)).setScale(2, roundMethod).doubleValue();
    }

    private  Range<Double> generateSellThreshold(BitString chromosome) {
        return getThresholdRange(chromosome, 7, 13);
    }

    private Range<Double> getThresholdRange(BitString chromosome, int startIndex, int endIndex) {
        int index = new BitString(chromosome.toString().substring(startIndex, endIndex - 1)).toNumber().intValue();
        double buyThresholdValue = truncate(min((index * 0.13) - 2.0, 2.0));


        if (chromosome.getBit(chromosome.getLength() - endIndex)) {
            return Range.atLeast(buyThresholdValue);
        } else {
            return Range.atMost(buyThresholdValue);
        }
    }

}
