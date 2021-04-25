package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.constraint.TrailingStopSellDecision;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;

/**
 * Created by egutter on 2/12/14.
 */
public class TrailingStopSellDecisionGenerator {

    private final BigDecimal stopLoss;
    private final BigDecimal trailingLoss;

    private BitString chromosome;

    public static void main(String[] args) {
        StockPrices stocks = new StockPrices("stock");
        stocks.addAll(Arrays.asList(DailyQuote.empty()));
        BigDecimal pricePaid = BigDecimal.TEN;

        TrailingStopSellDecision decision = new TrailingStopSellDecisionGenerator(new BitString("1111101111001")).generateSellDecision(pricePaid);
        System.out.println(decision.toString());

        decision = new TrailingStopSellDecisionGenerator(new BitString("0000000000000")).generateSellDecision(pricePaid);
        System.out.println(decision.toString());

        decision = new TrailingStopSellDecisionGenerator(new BitString("1111111111111")).generateSellDecision(pricePaid);
        System.out.println(decision.toString());

        decision = new TrailingStopSellDecisionGenerator(new BitString("1010101010101")).generateSellDecision(pricePaid);
        System.out.println(decision.toString());

        decision = new TrailingStopSellDecisionGenerator(new BitString("0101010101010")).generateSellDecision(pricePaid);
        System.out.println(decision.toString());
    }

    public TrailingStopSellDecisionGenerator(BitString chromosome) {
        this.chromosome = chromosome;
        this.stopLoss = generateStopLoss(chromosome);
        this.trailingLoss = generateTrailingLoss(chromosome);
    }

    /**
     * Bits
     * 0-5 => Stop Loss value from 1 to 13.6
     * 6-12 => Trailing Loss value from 1 to 19.14
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
        double stopLoss = new BitString(chromosome.toString().substring(0, 6)).toNumber().add(new BigInteger("5")).doubleValue();
        return new BigDecimal(stopLoss).divide(BigDecimal.valueOf(5), 2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal generateTrailingLoss(BitString chromosome) {
        double stopLoss = new BitString(chromosome.toString().substring(6, 13)).toNumber().add(new BigInteger("7")).doubleValue();
        return new BigDecimal(stopLoss).divide(BigDecimal.valueOf(7), 2, RoundingMode.HALF_EVEN);
    }
}
