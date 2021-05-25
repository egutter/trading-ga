package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.constraint.TrailingStopSellDecision;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Created by egutter on 2/12/14.
 */
public class TrailingStopSellDecisionGenerator {

    private final BigDecimal stopLoss;
    private final BigDecimal trailingLoss;
    private final int expireInDays;
    private BigDecimal targetPriceMultiplicator;

    public static void main(String[] args) {
        StockPrices stocks = new StockPrices("stock");
        stocks.addAll(Arrays.asList(DailyQuote.empty()));
        BigDecimal pricePaid = new BigDecimal(100.0);

        TrailingStopSellDecisionGenerator trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("1111101111001"));
        TrailingStopSellDecision decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());
        System.out.println(trailingStopSellDecisionGenerator.getTargetPriceMultiplicator());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("0000000000000"));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());
        System.out.println(trailingStopSellDecisionGenerator.getTargetPriceMultiplicator());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("1111111111111"));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());
        System.out.println(trailingStopSellDecisionGenerator.getTargetPriceMultiplicator());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("1010101011111"));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());
        System.out.println(trailingStopSellDecisionGenerator.getTargetPriceMultiplicator());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("0101010101010"));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());
        System.out.println(trailingStopSellDecisionGenerator.getTargetPriceMultiplicator());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("0101010101010"), Optional.of(new BitString("00")));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());
        System.out.println(trailingStopSellDecisionGenerator.getTargetPriceMultiplicator());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("0101010101010"), Optional.of(new BitString("01")));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());
        System.out.println(trailingStopSellDecisionGenerator.getTargetPriceMultiplicator());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("0101010101010"), Optional.of(new BitString("10")));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());
        System.out.println(trailingStopSellDecisionGenerator.getTargetPriceMultiplicator());

        trailingStopSellDecisionGenerator = new TrailingStopSellDecisionGenerator(new BitString("0101010101010"), Optional.of(new BitString("11")));
        decision = trailingStopSellDecisionGenerator.generateSellDecision(pricePaid);
        System.out.println(decision.toString());
        System.out.println(trailingStopSellDecisionGenerator.getExpireInDays());
        System.out.println(trailingStopSellDecisionGenerator.getTargetPriceMultiplicator());
    }

    public TrailingStopSellDecisionGenerator(BitString chromosome) {
        this(chromosome, Optional.empty());
    }
    public TrailingStopSellDecisionGenerator(BitString chromosome, Optional<BitString> targetPriceMultiplicatorChromosome) {
        this.stopLoss = generateStopLoss(chromosome);
        this.trailingLoss = generateTrailingLoss(chromosome);
        this.expireInDays = generateExpireInDays(chromosome);
        this.targetPriceMultiplicator = generateTargetPriceMultiplicator(targetPriceMultiplicatorChromosome);
    }

    /**
     * Bits
     * 0-3 => Stop Loss value from 1 to 16
     * 4-6 => Trailing Loss value from stopLoss to 24
     * 7-12 => Expire in days from 1 to 64
     *
     * @param pricePaid
     * @return
     */
    public TrailingStopSellDecision generateSellDecision(BigDecimal pricePaid) {
        return new TrailingStopSellDecision(pricePaid,
                    this.stopLoss,
                    this.trailingLoss, targetPriceMultiplicator);
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

    private BigDecimal generateTargetPriceMultiplicator(Optional<BitString> chromosomeOpt) {
        BitString chromosome = chromosomeOpt.orElseGet(() -> new BitString("01"));
        return new BigDecimal(chromosome.toNumber().add(new BigInteger("1"))).multiply(new BigDecimal(0.5));
    }

    public int getExpireInDays() {
        return expireInDays;
    }

    public BigDecimal getTargetPriceMultiplicator() {
        return targetPriceMultiplicator;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TrailingStopSellDecisionGenerator.class.getSimpleName() + "[", "]")
                .add("stopLoss=" + stopLoss)
                .add("trailingLoss=" + trailingLoss)
                .add("expireInDays=" + expireInDays)
                .add("targetPriceMultiplicator=" + targetPriceMultiplicator)
                .toString();
    }
}
