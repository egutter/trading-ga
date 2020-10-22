package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.technicalanalysis.ChaikinOscillator;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import org.uncommons.maths.binary.BitString;

import java.util.Arrays;
import java.util.function.Function;

public class ChaikinOscillatorGenerator implements ConditionalOrderConditionGenerator, BuyTradingDecisionGenerator {

    private int fastDays = 3;
    private int slowDays = 10;

    public static void main(String[] args) {
        StockPrices stocks = new StockPrices("stock");
        stocks.addAll(Arrays.asList(DailyQuote.empty()));
        ChaikinOscillator chaikin = (ChaikinOscillator) new ChaikinOscillatorGenerator(new BitString("0000100001000")).generateCondition(stocks);
        System.out.println(chaikin.buyDecisionToString());
    }

    public ChaikinOscillatorGenerator(BitString bitString) {
        this.fastDays = generateFastDays(bitString);
        this.slowDays = generateSlowDays(bitString);
    }

    @Override
    public Function<TimeFrameQuote, Boolean> generateCondition(StockPrices stockPrices) {
        return new ChaikinOscillator(stockPrices, fastDays, slowDays);
    }

    private int generateFastDays(BitString chromosome) {
        return new BitString(chromosome.toString().substring(0, 5)).toNumber().intValue() + 2;
    }

    private int generateSlowDays(BitString chromosome) {
        return new BitString(chromosome.toString().substring(5, 13)).toNumber().intValue() + 2;
    }

    @Override
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        return new ChaikinOscillator(stockPrices, fastDays, slowDays);
    }
}
