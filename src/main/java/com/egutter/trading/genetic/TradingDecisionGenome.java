package com.egutter.trading.genetic;

import org.uncommons.maths.binary.BitString;

/**
 * Genome
 *
 * 0-1 TradingComposite applies All or Any
 * 2-22 BollingerBands
 * 23-28 SellAfterAFixedNumberOfDays
 *
 * Created by egutter on 2/16/14.
 */
public class TradingDecisionGenome {
    public static final int SIZE = 29;

    private BitString genomeBits;

    public TradingDecisionGenome(BitString genomeBits) {
        this.genomeBits = genomeBits;
    }

    public BitString extractTradingDecisionCompositeChromosome() {
        return extractChromosome(0, 2);
    }

    public BitString extractBollingerBandsChromosome() {
        return extractChromosome(2, 23);
    }

    public BitString extractSellAfterAFixedNumberOfDaysChromosome() {
        return extractChromosome(23, 29);
    }

    private BitString extractChromosome(int startIndex, int endIndex) {
        return new BitString(this.genomeBits.toString().substring(startIndex, endIndex));
    }
}
