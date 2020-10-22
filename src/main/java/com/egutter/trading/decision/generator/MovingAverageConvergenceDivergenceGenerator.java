package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.technicalanalysis.MovingAverageConvergenceDivergence;
import com.egutter.trading.decision.technicalanalysis.TradeSignal;
import com.egutter.trading.decision.technicalanalysis.macd.SignChange;
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
import static com.google.common.primitives.Ints.min;

/**
 * Created by egutter on 2/12/14.
 */
public class MovingAverageConvergenceDivergenceGenerator implements BuyTradingDecisionGenerator, SellTradingDecisionGenerator, ConditionalOrderConditionGenerator {

    private final TradeSignal buySignal;
    private final TradeSignal sellSignal;

    private static final transient ConcurrentMap<String, MovingAverageConvergenceDivergence> cache = new MapMaker().weakKeys().makeMap();
    private BitString chromosome;

    public MovingAverageConvergenceDivergenceGenerator(BitString chromosome) {
        this.chromosome = chromosome;
        this.buySignal = generateBuySignal(chromosome);
        this.sellSignal = generateSellSignal(chromosome);
    }

    @Override
    public Function<TimeFrameQuote, Boolean> generateCondition(StockPrices stockPrices) {
        return generateMovingAverageConvergenceDivergence(stockPrices);
    }

    /**
     * Bits
     * 0-1 => Buy Sign Change (0: TradeSignal.POSITIVE, 1: TradeSignal.NEGATIVE, 2: TradeSignal.NO_CHANGE)
     * 2 => Buy difference with previous day range style (0: at least, 1: at most)
     * 3-5 => Buy difference with previous day 0.01 to 2.04 by 0,01*POWER(index,2) steps
     * 6-7 => Sell Sign Change (0: TradeSignal.POSITIVE, 1: TradeSignal.NEGATIVE, 2: TradeSignal.NO_CHANGE)
     * 8 => Sell difference with previous day range style (0: at least, 1: at most)
     * 9-11 => Sell difference with previous day 0.01 to 2.04 by 0,01*POWER(index,2) steps
     * 12 => Nothing
     *
     * @param stockPrices
     * @return
     */
    public MovingAverageConvergenceDivergence generateBuyDecision(StockPrices stockPrices) {
        return generateMovingAverageConvergenceDivergence(stockPrices);
    }


    @Override
    public MovingAverageConvergenceDivergence generateSellDecision(StockPrices stockPrices) {
        return generateMovingAverageConvergenceDivergence(stockPrices);
    }

    private MovingAverageConvergenceDivergence generateMovingAverageConvergenceDivergence(StockPrices stockPrices) {
        String key = stockPrices.getStockName() + chromosome.toString();
        MovingAverageConvergenceDivergence macd = cache.get(key);
        if (macd == null) {
            macd = new MovingAverageConvergenceDivergence(stockPrices,
                    this.buySignal,
                    this.sellSignal);
            cache.put(key, macd);
        }
        return macd;
    }

    private TradeSignal generateBuySignal(BitString chromosome) {
        SignChange changeSign = generateSignChange(chromosome, 0, 2);
        Range<Double> changeRange = generateRange(chromosome, 3, 6, chromosome.getBit(10));
        return new TradeSignal(changeSign, changeRange);
    }

    private TradeSignal generateSellSignal(BitString chromosome) {
        SignChange changeSign = generateSignChange(chromosome, 6, 8);
        Range<Double> changeRange = generateRange(chromosome, 9, 12, chromosome.getBit(4));
        return new TradeSignal(changeSign, changeRange);
    }

    private Range<Double> generateRange(BitString chromosome, int start, int end, boolean atMostRange) {
        int index = new BitString(chromosome.toString().substring(start, end)).toNumber().intValue();
        double adjustBy = 0.01;
        double thresholdValue = (adjustBy * index) + (adjustBy * Math.pow(index+1, 3));
        if (atMostRange) {
            return atMost(thresholdValue);
        }
        return atLeast(thresholdValue);
    }

    private SignChange generateSignChange(BitString chromosome, int start, int end) {
        int index = new BitString(chromosome.toString().substring(start, end)).toNumber().intValue();
        return SignChange.values()[min(index, 2)];
    }

}
