package com.egutter.trading.stats;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class CandidateRankerTest {

    private CandidateRanker candidateRanker = new CandidateRanker(null);

    @Test
    public void should_rank_a() throws Exception {
        CandidateRank rank = candidateRanker.rank(new CandidateStats(BigDecimal.valueOf(17.81), BigDecimal.ZERO, 3, 0));

        assertThat(rank.toString(), equalTo("[A+++] "));
    }

    @Test
    public void should_rank_b() throws Exception {
        CandidateRank rank = candidateRanker.rank(new CandidateStats(BigDecimal.valueOf(84.72), BigDecimal.valueOf(-12.6), 17, 2));

        assertThat(rank.toString(), equalTo("[B+++++++++++++++++++] "));
    }

    @Test
    public void should_rank_d() throws Exception {
        CandidateRank rank = candidateRanker.rank(new CandidateStats(BigDecimal.valueOf(3.6), BigDecimal.valueOf(-10.4), 1, 1));

        assertThat(rank.toString(), equalTo("[D] "));
    }
}