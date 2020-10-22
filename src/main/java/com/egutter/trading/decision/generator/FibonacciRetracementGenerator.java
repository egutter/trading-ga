package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.technicalanalysis.FibonacciRetracementBuyDecision;
import com.egutter.trading.order.condition.BuyDecisionConditionsFactory;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Range;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

import static com.egutter.trading.decision.technicalanalysis.FibonacciRetracementBuyDecision.FIBONACCI_EXT_LEVES;
import static com.egutter.trading.decision.technicalanalysis.FibonacciRetracementBuyDecision.FIBONACCI_RETR_LEVELS;

/**
 * Created by egutter on 2/12/14.
 */
public class FibonacciRetracementGenerator implements BuyTradingDecisionGenerator {

    private final Range<BigDecimal> retracementLevel;
    private final double buyLevelTrigger;
    private final double extensionSellFirstLevel;
    private final double extensionSellSecondLevel;
    private BuyDecisionConditionsFactory buyDecisionConditionsFactory;
    private final int highLookback;
    private final int lowLookback;

    private static final transient ConcurrentMap<String, FibonacciRetracementBuyDecision> cache = new MapMaker().weakKeys().makeMap();
    private BitString chromosome;
    private int retracementLevelIndex;
    private int extensionSellFirstLevelIndex;

    public static void main(String[] args) {
        StockPrices stocks = new StockPrices("stock");
        stocks.addAll(Arrays.asList(DailyQuote.empty()));
        BuyTradingDecision fib = new FibonacciRetracementGenerator(new BitString("1010111001111"), null).generateBuyDecision(stocks);
        System.out.println(fib.buyDecisionToString());
    }

    public FibonacciRetracementGenerator(BitString chromosome, BuyDecisionConditionsFactory buyDecisionConditionsFactory) {
        this.chromosome = chromosome;
        this.retracementLevel = generateRetracementLevel(chromosome);
        this.buyLevelTrigger = generateBuyLevelTrigger(chromosome);
        this.highLookback = generateHighLookback(chromosome);
        this.lowLookback = generateLowLookback(chromosome);
        this.extensionSellFirstLevel = generateExtensionSellFirstLevel(chromosome);
        this.extensionSellSecondLevel = generateExtensionSellSecondLevel(chromosome);
        this.buyDecisionConditionsFactory = buyDecisionConditionsFactory;
    }

    /**
     * Bits
     * 0-1 => Retracement Level (0: 0.382, 1: 0.5, 2: 0.618, 3: 0.786)
     * 2-3 => Buy Trigger Level (0: 0.236, 1: 0.382, 2: 0.5, 3: 0.618)
     * 4-5 => High Lookback (0: 5, 1: 10, 2: 15, 3: 20)
     * 6-7 => Low Lookback (0: 10, 1: 15, 2: 20, 3: 25) + High Lookback
     * 8-9 => Extension Sell 1st Level (0: 1, 1: 1.236, 2: 1.382, 3: 1.5)
     * 10-11 => Extension Sell 2st Level (0: 1.382, 1: 1.5, 2: 1.618, 3: 1.786)
     * 12-12 => Not used
     *
     * @param stockPrices
     * @return
     */
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return generateFibonacciRetracement(stockPrices);
    }

    private FibonacciRetracementBuyDecision generateFibonacciRetracement(StockPrices stockPrices) {
        String key = stockPrices.getStockName() + chromosome.toString();
        FibonacciRetracementBuyDecision fibRetracement = cache.get(key);
        if (fibRetracement == null) {
            fibRetracement = new FibonacciRetracementBuyDecision(stockPrices,
                    retracementLevel,
                    buyLevelTrigger,
                    highLookback,
                    lowLookback,
                    extensionSellFirstLevel,
                    extensionSellSecondLevel,
                    this.buyDecisionConditionsFactory);
            cache.put(key, fibRetracement);
        }
        return fibRetracement;
    }

    private Range<BigDecimal> generateRetracementLevel(BitString chromosome) {
        this.retracementLevelIndex = new BitString(chromosome.toString().substring(0, 2)).toNumber().intValue();
        double fibLevel = FIBONACCI_RETR_LEVELS.get(retracementLevelIndex + 1);
//        MathContext mc = new MathContext(2, RoundingMode.DOWN);
        MathContext mc = new MathContext(1, RoundingMode.DOWN);
        BigDecimal low = new BigDecimal(fibLevel, mc);
//        BigDecimal high = low.add(BigDecimal.valueOf(0.01));
        BigDecimal high = low.add(BigDecimal.valueOf(0.1));
        return Range.open(low, high);
    }

    private double generateBuyLevelTrigger(BitString chromosome) {
        int index = new BitString(chromosome.toString().substring(2, 4)).toNumber().intValue();
        if (index > this.retracementLevelIndex) {
            index = this.retracementLevelIndex;
        }
        Double buyLevel = FIBONACCI_RETR_LEVELS.get(index);
        return buyLevel;
    }

    private int generateHighLookback(BitString chromosome) {
        int index = new BitString(chromosome.toString().substring(4, 6)).toNumber().intValue();
        return (5 * (1 + index));
    }

    private int generateLowLookback(BitString chromosome) {
        int index = new BitString(chromosome.toString().substring(6, 8)).toNumber().intValue();
        return 5 * (2 + index) + this.highLookback;
    }

    private double generateExtensionSellFirstLevel(BitString chromosome) {
        this.extensionSellFirstLevelIndex = new BitString(chromosome.toString().substring(8, 10)).toNumber().intValue();
        return FIBONACCI_EXT_LEVES.get(extensionSellFirstLevelIndex);
    }

    private double generateExtensionSellSecondLevel(BitString chromosome) {
        int index = new BitString(chromosome.toString().substring(10, 12)).toNumber().intValue();
        if (index <= this.extensionSellFirstLevelIndex) {
            index = this.extensionSellFirstLevelIndex + 1;
        }
        return FIBONACCI_EXT_LEVES.get(index);
    }
}
