package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.constraint.TrailingStop;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.MapMaker;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by egutter on 2/12/14.
 */
public class TrailingStopGenerator implements BuyTradingDecisionGenerator, SellTradingDecisionGenerator {

    private final BigDecimal stopLoss;
    private final BigDecimal trailingLoss;

    private static final transient ConcurrentMap<String, TrailingStop> cache = new MapMaker().weakKeys().makeMap();
    private Portfolio portfolio;
    private BitString chromosome;

    public static void main(String[] args) {
        StockPrices stocks = new StockPrices("stock");
        stocks.addAll(Arrays.asList(DailyQuote.empty()));
        SellTradingDecision stoch = new TrailingStopGenerator(new Portfolio(), new BitString("1111101111001")).generateSellDecision(stocks);
        System.out.println(stoch.sellDecisionToString());
    }

    public TrailingStopGenerator(Portfolio portfolio, BitString chromosome) {
        this.portfolio = portfolio;
        this.chromosome = chromosome;
        this.stopLoss = generateStopLoss(chromosome);
        this.trailingLoss = generateTrailingLoss(chromosome);
    }

    /**
     * Bits
     * 0-5 => Stop Loss value from 0 to 12.8
     * 6-12 => Trailing Loss value from 0 to 18.28
     *
     * @param stockPrices
     * @return
     */
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        throw new RuntimeException("Not supported");
    }


    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        return generateTrailingStop(stockPrices);
    }

    private TrailingStop generateTrailingStop(StockPrices stockPrices) {
        String key = stockPrices.getStockName() + chromosome.toString();
        TrailingStop trailingStop = cache.get(key);
        if (trailingStop == null) {
            trailingStop = new TrailingStop(portfolio, stockPrices,
                    this.stopLoss,
                    this.trailingLoss);
            cache.put(key, trailingStop);
        }
        return trailingStop;
    }


    private BigDecimal generateStopLoss(BitString chromosome) {
        double stopLoss = new BitString(chromosome.toString().substring(0, 6)).toNumber().doubleValue();
        return new BigDecimal(stopLoss).divide(BigDecimal.valueOf(5), 2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal generateTrailingLoss(BitString chromosome) {
        double stopLoss = new BitString(chromosome.toString().substring(6, 13)).toNumber().doubleValue();
        return new BigDecimal(stopLoss).divide(BigDecimal.valueOf(7), 2, RoundingMode.HALF_EVEN);
    }
}
