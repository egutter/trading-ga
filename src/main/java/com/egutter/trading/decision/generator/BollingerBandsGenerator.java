package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BollingerBands;
import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Range;
import com.tictactec.ta.lib.MAType;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;

import static com.google.common.collect.Range.atLeast;
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
     * 1-4 => Buy Threshold value from -2 to 2 by 0,25 steps
     * 5-8 => Sell Threshold value from -2 to 2 by 0,25 steps
     * 9-12 => Moving Average Days from 10 to 27
     * 13-15 => Moving Average Type (0: Sma, 1: Ema, 2: Wma, 3: Dema, 4: Tema, 5: Trima, 6: Kama, 7: Mama)
     *
     * @param stockPrices
     * @return
     */
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return new BollingerBands(stockPrices,
                this.buyThreshold,
                this.sellThreshold,
                this.movingAverageDays,
                this.movingAverageType);
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return new BollingerBands(stockPrices,
                this.buyThreshold,
                this.sellThreshold,
                this.movingAverageDays,
                this.movingAverageType);
    }

    private MAType generateMovingAverageType(BitString chromosome) {
        int typeNumber = new BitString(chromosome.toString().substring(13, 16)).toNumber().intValue();
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

    private int generateMovingAverageDays(BitString chromosome) {
        int days = new BitString(chromosome.toString().substring(9, 13)).toNumber().intValue();
        return days + 12;
    }

    private Range<Double> generateBuyThreshold(BitString chromosome) {
        return generateThreshold(chromosome, 1, 5);
    }

    private Range<Double> generateSellThreshold(BitString chromosome) {
        return generateThreshold(chromosome, 5, 9);
    }

    private Range<Double> generateThreshold(BitString chromosome, int start, int end) {
        int index = new BitString(chromosome.toString().substring(start, end)).toNumber().intValue();
        double adjustBy = 2.0;
        if (index >= 8) {
            adjustBy -= 0.25;
        }
        double buyThresholdValue = truncate(min((index * 0.25) - adjustBy, 2.0));

        return atLeast(buyThresholdValue);
    }

    private double truncate(double value) {
        int roundMethod = (value > 0) ? BigDecimal.ROUND_FLOOR : BigDecimal.ROUND_CEILING;
        return new BigDecimal(String.valueOf(value)).setScale(2, roundMethod).doubleValue();
    }
}
