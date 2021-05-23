package com.egutter.trading.genetic;

import org.uncommons.maths.binary.BitString;

import java.util.Optional;

/**
 * Genome
 * 0123 4567890123456 7890123456789
 * 0-3 SellAfterAFixedNumberOfDays
 * 4-15 First Technical Analysis Decision
 * 16-27 Second Technical Analysis Decision
 *
 * Created by egutter on 2/16/14.
 */
public class TradingDecisionGenome {
//    public static final int SIZE = 17;
//    public static final int SIZE = 30;
//    public static final int START_POSITION = 4;
    public static final int START_POSITION = 0;
    public static final int LENGTH = 13;
    public static final int HEAD_LENGTH = 13;
    private static final int TAIL_LENGTH = 2;

    private BitString genomeBits;

    public TradingDecisionGenome(BitString genomeBits) {
        this.genomeBits = genomeBits;
    }

    public BitString extractFirstDecisionChromosome() {
        return extractChromosome(4, 17);
    }

    public BitString extractSecondDecisionChromosome() {
        return extractChromosome(17, 30);
    }

    public BitString extractSellAfterAFixedNumberOfDaysChromosome() {
        return extractChromosome(0, 4);
    }

    private BitString extractChromosome(int startIndex, int endIndex) {
        return new BitString(this.genomeBits.toString().substring(startIndex, endIndex));
    }

    public BitString extractChromosome(int index) {
        return extractChromosome(START_POSITION + (LENGTH * index), (LENGTH + START_POSITION) + (LENGTH * index) );
    }

    public Optional<BitString> extractTail(int tailStartIndex) {
        if (this.genomeBits.getLength() > (LENGTH * tailStartIndex)) {
            int startIndex = START_POSITION + (LENGTH * (tailStartIndex));
            return Optional.of(extractChromosome(startIndex, startIndex + TAIL_LENGTH));
        }
        return Optional.empty();
    }
}
