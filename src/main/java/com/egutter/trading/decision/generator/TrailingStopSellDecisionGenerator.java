package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.constraint.TrailingStopSellDecision;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * Created by egutter on 2/12/14.
 */
public class TrailingStopSellDecisionGenerator {

    private final BigDecimal stopLoss;
    private final BigDecimal trailingLoss;
    private final int expireInDays;

    public static void main(String[] args) {
        StockPrices stocks = new StockPrices("stock");
        stocks.addAll(Arrays.asList(DailyQuote.empty()));
        BigDecimal pricePaid = new BigDecimal(100.0);

        TrailingStopSellDecisionGenerator trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("1111101111001"));
        TrailingStopSellDecision decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("0000000000000"));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("1111111111111"));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("1010101011111"));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("0101010101010"));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());
    }

    public TrailingStopSellDecisionGenerator(BitString chromosome) {
        this.stopLoss = generateStopLoss(chromosome);
        this.trailingLoss = generateTrailingLoss(chromosome);
        this.expireInDays = generateExpireInDays(chromosome);
    }

    /**
     * Bits
     * 0-3 => Stop Loss value from 1 to 16
     * 4-6 => Trailing Loss value from stopLoss to 24
     * 6-12 => Expire in days from 1 to 64
     *
     * @param pricePaid
     * @return
     */
    public TrailingStopSellDecision generateSellDecision(BigDecimal pricePaid) {
        return new TrailingStopSellDecision(pricePaid,
                    this.stopLoss,
                    this.trailingLoss);
    }


    private BigDecimal generateStopLoss(BitString chromosome) {
        double stopLoss = new BitString(chromosome.toString().substring(0, 4)).toNumber().add(new BigInteger("1")).doubleValue();
        return new BigDecimal(stopLoss);
    }

    private BigDecimal generateTrailingLoss(BitString chromosome) {
        BigDecimal trailingLoss = new BigDecimal(new BitString(chromosome.toString().substring(4, 7)).toNumber());
        if (trailingLoss.compareTo(this.stopLoss) < 0 ) {
            trailingLoss = trailingLoss.add(this.stopLoss);
        }
        return trailingLoss;
    }

    private int generateExpireInDays(BitString chromosome) {
        return new BitString(chromosome.toString().substring(7, 13)).toNumber().add(new BigInteger("1")).intValue();
    }

    public int getExpireInDays() {
        return expireInDays;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TrailingStopSellDecisionGenerator.class.getSimpleName() + "[", "]")
                .add("stopLoss=" + stopLoss)
                .add("trailingLoss=" + trailingLoss)
                .add("expireInDays=" + expireInDays)
                .toString();
    }
}
