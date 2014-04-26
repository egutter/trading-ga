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
        TradingDecisionGenome genome = new TradingDecisionGenome(chromosome("01111000011110000100000011111111111"));
        assertThat(genome.extractBollingerBandsChromosome(), equalTo(chromosome("1111000011110000")));
    }

    @Test
    public void testExtractMoneyFlowIndexChromosome() throws Exception {
        TradingDecisionGenome genome = new TradingDecisionGenome(chromosome("01111000011110000100000011111111111"));
        assertThat(genome.extractMoneyFlowIndexChromosome(), equalTo(chromosome("10000001111111")));
    }

    @Test
    public void testExtractSellAfterAFixedNumberOfDaysChromosome() throws Exception {
        TradingDecisionGenome genome = new TradingDecisionGenome(chromosome("01111000011110000100000011111111010"));
        assertThat(genome.extractSellAfterAFixedNumberOfDaysChromosome(), equalTo(chromosome("1010")));
    }

    private BitString chromosome(String chromosome) {
        return new BitString(chromosome);
    }
}
