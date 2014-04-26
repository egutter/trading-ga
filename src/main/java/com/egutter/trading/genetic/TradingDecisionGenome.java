package com.egutter.trading.genetic;

import org.uncommons.maths.binary.BitString;

/**
 * Genome
 * 0 1234567890123456 78901234567890 1234
 * 0 TradingComposite applies All or Any
 * 1-16 BollingerBands
 * 17-30 BollingerBands
 * 31-34 SellAfterAFixedNumberOfDays
 *
 * Created by egutter on 2/16/14.
 */
public class TradingDecisionGenome {
    public static final int SIZE = 35;

    private BitString genomeBits;

    public TradingDecisionGenome(BitString genomeBits) {
        this.genomeBits = genomeBits;
    }

    public BitString extractBollingerBandsChromosome() {
        return extractChromosome(1, 17);
    }


    public BitString extractMoneyFlowIndexChromosome() {
        return extractChromosome(17, 31);
    }

    public BitString extractSellAfterAFixedNumberOfDaysChromosome() {
        return extractChromosome(31, 35);
    }

    private BitString extractChromosome(int startIndex, int endIndex) {
        return new BitString(this.genomeBits.toString().substring(startIndex, endIndex));
    }
}
