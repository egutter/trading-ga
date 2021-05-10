package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.RelativeStrengthIndexCrossDown;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import org.uncommons.maths.binary.BitString;

import java.util.function.Function;

public class RelativeStrengthIndexCrossDownGenerator implements ConditionalOrderConditionGenerator {

    private int days;
    private Double crossDownSignal;

    public static void main(String[] args) {
        String rsi = new RelativeStrengthIndexCrossDownGenerator(new BitString("0000000000000")).generateCondition(null).toString();
        System.out.println(rsi);

        rsi = new RelativeStrengthIndexCrossDownGenerator(new BitString("1111111111111")).generateCondition(null).toString();
        System.out.println(rsi);

        rsi = new RelativeStrengthIndexCrossDownGenerator(new BitString("0011000111100")).generateCondition(null).toString();
        System.out.println(rsi);
    }

    public RelativeStrengthIndexCrossDownGenerator(BitString chromosome) {
        this.days = extractDays(chromosome);
        this.crossDownSignal = extractCrossDownSignal(chromosome);
    }

    /**
     * Bits
     * 0-5 => Days (2-65)
     * 6-12 => Cross down signal value from 0 to 63
     *
     * @param stockPrices
     * @return
     */
    @Override
    public Function<TimeFrameQuote, Boolean> generateCondition(StockPrices stockPrices) {
        return new RelativeStrengthIndexCrossDown(stockPrices, days, crossDownSignal);
    }

    private int extractDays(BitString chromosome) {
        return new BitString(chromosome.toString().substring(0, 6)).toNumber().intValue() + 2;
    }

    private double extractCrossDownSignal(BitString chromosome) {
        return new BitString(chromosome.toString().substring(6, 12)).toNumber().doubleValue();
    }

}
