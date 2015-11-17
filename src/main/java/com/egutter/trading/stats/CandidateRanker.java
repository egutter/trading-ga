package com.egutter.trading.stats;

import com.egutter.trading.decision.Candidate;

import java.math.BigDecimal;

/**
 * Created by egutter on 4/17/15.
 */
public class CandidateRanker {


    private CandidateStatsCollector statsCollector;

    public CandidateRanker(CandidateStatsCollector statsCollector) {
        this.statsCollector = statsCollector;
    }

    public CandidateRank rank(Candidate aCandidate) {
        CandidateStats stats = statsCollector.statsFor(aCandidate);
        CandidateRank ranking;

        if (stats.isEmpty()) {
            ranking = CandidateRank.unknown();
        } else if (stats.getLostCount() == 0) {
            ranking = rankingA(stats);
        } else if (stats.getWonCount() == 0) {
            ranking = CandidateRank.zetaRank();
        } else if (stats.getAverageReturn().compareTo(BigDecimal.ZERO) <= 0) {
            ranking = CandidateRank.chiRank();
        } else {
            ranking = intermediateRanking(stats);
        }
        return ranking;
    }

    private CandidateRank intermediateRanking(CandidateStats stats) {
        CandidateRank ranking;
        if (stats.getBiggestLost().compareTo(BigDecimal.valueOf(10.0)) <= 0 ) {
            if (stats.getLostCount() < 4) {
                ranking = CandidateRank.betaRank();
            } else {
                ranking = CandidateRank.ceRank();
            }
        } else {
            if (stats.getLostCount() < 4) {
                ranking = CandidateRank.deRank();
            } else {
                ranking = CandidateRank.epsilonRank();
            }
        }
        ranking = addPlus(stats, ranking);
        return ranking;
    }

    private CandidateRank rankingA(CandidateStats stats) {
        CandidateRank ranking = CandidateRank.alphaRank();
        return addPlus(stats, ranking);
    }

    private CandidateRank addPlus(CandidateStats stats, CandidateRank ranking) {
        if (stats.getAverageReturn().compareTo(BigDecimal.valueOf(10.0)) < 0)
            return ranking;

        ranking.addPlus();

        if (stats.getAverageReturn().compareTo(BigDecimal.valueOf(15.0)) < 0)
            return ranking;

        ranking.addPlus();

        if (stats.getWonCount() < 2)
            return ranking;

        ranking.addPlus();


        if (stats.getAverageReturn().compareTo(BigDecimal.valueOf(20.0)) < 0) {
            return ranking;
        }

        ranking.addPlus();

        if (stats.getWonCount() < 3)
            return ranking;


        ranking.addPlus(stats.getWonCount()-2);

        return ranking;
    }
}
