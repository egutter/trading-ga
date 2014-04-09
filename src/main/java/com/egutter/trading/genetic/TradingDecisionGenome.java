package com.egutter.trading.genetic;

import org.uncommons.maths.binary.BitString;

/**
 * Genome
 * 0 1234567890123456 7890
 * 0 TradingComposite applies All or Any
 * 1-16 BollingerBands
 * 17-20 SellAfterAFixedNumberOfDays
 *
 * Created by egutter on 2/16/14.
 */
public class TradingDecisionGenome {
    public static final int SIZE = 21;

    private BitString genomeBits;

    public TradingDecisionGenome(BitString genomeBits) {
        this.genomeBits = genomeBits;
    }

    public BitString extractBollingerBandsChromosome() {
        return extractChromosome(1, 17);
    }

    public BitString extractSellAfterAFixedNumberOfDaysChromosome() {
        return extractChromosome(17, 21);
    }

    private BitString extractChromosome(int startIndex, int endIndex) {
        return new BitString(this.genomeBits.toString().substring(startIndex, endIndex));
    }
}
