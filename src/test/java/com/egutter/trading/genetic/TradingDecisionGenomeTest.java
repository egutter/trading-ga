package com.egutter.trading.genetic;

import org.junit.Test;
import org.uncommons.maths.binary.BitString;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 3/20/14.
 */
public class TradingDecisionGenomeTest {

    @Test
    public void testExtractBollingerBandsChromosome() throws Exception {
        TradingDecisionGenome genome = new TradingDecisionGenome(chromosome("010111001010111001111001000001"));
        assertThat(genome.extractFirstDecisionChromosome(), equalTo(chromosome("1100101011100")));
    }

    @Test
    public void testExtractMoneyFlowIndexChromosome() throws Exception {
        TradingDecisionGenome genome = new TradingDecisionGenome(chromosome("010111001010111001111001000001"));
        assertThat(genome.extractSecondDecisionChromosome(), equalTo(chromosome("1111001000001")));
    }

    @Test
    public void testExtractSellAfterAFixedNumberOfDaysChromosome() throws Exception {
        TradingDecisionGenome genome = new TradingDecisionGenome(chromosome("010111001010111001111001000001"));
        assertThat(genome.extractSellAfterAFixedNumberOfDaysChromosome(), equalTo(chromosome("0101")));
    }

    private BitString chromosome(String chromosome) {
        return new BitString(chromosome);
    }
}
