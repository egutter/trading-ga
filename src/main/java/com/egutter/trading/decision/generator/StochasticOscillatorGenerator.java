package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.technicalanalysis.StochasticOscillator;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.MapMaker;
import com.tictactec.ta.lib.MAType;
import org.uncommons.maths.binary.BitString;

import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by egutter on 2/12/14.
 */
public class StochasticOscillatorGenerator implements BuyTradingDecisionGenerator, SellTradingDecisionGenerator {

    private static final transient ConcurrentMap<String, StochasticOscillator> cache = new MapMaker().weakKeys().makeMap();
    private BitString chromosome;
    private int fastKDays;
    private int slowKDays;
    private int slowDPeriod;
    private MAType slowKMaType;
    private MAType slowDMaType;

    public static void main(String[] args) {
        StockPrices stocks = new StockPrices("stock");
        stocks.addAll(Arrays.asList(DailyQuote.empty()));
        BuyTradingDecision stoch = new StochasticOscillatorGenerator(new BitString("0010001001000")).generateBuyDecision(stocks);
        System.out.println(stoch.buyDecisionToString());
    }

    public StochasticOscillatorGenerator(BitString chromosome) {
        this.chromosome = chromosome;
        this.fastKDays = generateFastKDays(chromosome);
        this.slowKDays = generateSlowKDays(chromosome);
        this.slowDPeriod = generateSlowDDays(chromosome);
        this.slowKMaType = generateSlowKType(chromosome);
        this.slowDMaType = generateSlowDType(chromosome);
    }

    /**
     * Bits
     * 0-4 => Fast K Days (1-33)
     * 5-7 => Slow K Days (1-8)
     * 8-10 => Slow D Days (1-8)
     * 11 => Slow K MA Type (0: SMA, 1: EMA)
     * 12 => Slow D MA Type (0: SMA, 1: EMA)
     *
     * @param stockPrices
     * @return
     */
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return generateStochasticOscillator(stockPrices);
    }


    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return generateStochasticOscillator(stockPrices);
    }

    private StochasticOscillator generateStochasticOscillator(StockPrices stockPrices) {
        String key = stockPrices.getStockName() + chromosome.toString();
        StochasticOscillator stochOsc = cache.get(key);
        if (stochOsc == null) {
            stochOsc = new StochasticOscillator(stockPrices,
                    this.fastKDays,
                    this.slowKDays,
                    this.slowDPeriod,
                    this.slowKMaType,
                    this.slowDMaType);
            cache.put(key, stochOsc);
        }
        return stochOsc;
    }


    private MAType generateSlowKType(BitString chromosome) {
        return getMaType(chromosome, 11, 12);
    }

    private MAType generateSlowDType(BitString chromosome) {
        return getMaType(chromosome, 12, 13);
    }

    private MAType getMaType(BitString chromosome, int start, int end) {
        double type = new BitString(chromosome.toString().substring(start, end)).toNumber().intValue();
        return type == 0 ? MAType.Sma : MAType.Ema;
    }

    private int generateFastKDays(BitString chromosome) {
        return new BitString(chromosome.toString().substring(0, 5)).toNumber().intValue() + 1;
    }

    private int generateSlowDDays(BitString chromosome) {
        return new BitString(chromosome.toString().substring(5, 8)).toNumber().intValue() + 1;
    }

    private int generateSlowKDays(BitString chromosome) {
        return new BitString(chromosome.toString().substring(8, 11)).toNumber().intValue() + 1;
    }

}
