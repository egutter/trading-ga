package com.egutter.trading.genetic;

import org.uncommons.maths.binary.BitString;

/**
 * Genome
 *
 * 0-20 BollingerBands
 * 21-26 SellAfterAFixedNumberOfDays
 *
 * Created by egutter on 2/16/14.
 */
public class TradingDecisionGenome {
    public static final int SIZE = 25;

    private BitString genomeBits;

    public TradingDecisionGenome(BitString genomeBits) {
        this.genomeBits = genomeBits;
    }

    public BitString extractBollingerBandsChromosome() {
        return extractChromosome(0, 21);
    }


    public BitString extractSellAfterAFixedNumberOfDaysChromosome() {
        return extractChromosome(21, 27);
    }

    private BitString extractChromosome(int startIndex, int endIndex) {
        return new BitString(this.genomeBits.toString().substring(startIndex, endIndex));
    }
}
