package com.egutter.trading.stats;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.repository.PortfolioRepository;

import java.math.BigDecimal;

/**
 * Created by egutter on 4/17/15.
 */
public class CandidateRanker {


    private CandidateStatsCollector statsCollector;

    public CandidateRanker(CandidateStatsCollector statsCollector) {
        this.statsCollector = statsCollector;
    }

    public String rank(Candidate aCandidate) {
        CandidateStats stats = statsCollector.statsFor(aCandidate);
        String ranking = "";

        if (stats.isEmpty()) {
            ranking = "?";
        } else if (stats.getLostCount() == 0) {
            ranking = rankingA(stats);
        } else if (stats.getWonCount() == 0) {
            ranking = "Z";
        } else if (stats.getAverageReturn().compareTo(BigDecimal.ZERO) <= 0) {
            ranking = "X";
        } else {
            ranking = intermediateRanking(stats, ranking);
        }
        return "[" + ranking + "] ";
    }

    private String intermediateRanking(CandidateStats stats, String ranking) {
        if (stats.getBiggestLost().compareTo(BigDecimal.valueOf(10.0)) <= 0 ) {
            if (stats.getLostCount() < 4) {
                ranking = "B";
            } else {
                ranking = "C";
            }
        } else {
            if (stats.getLostCount() < 4) {
                ranking = "D";
            } else {
                ranking = "E";
            }
        }
        ranking = addPlus(stats, ranking);
        return ranking;
    }

    private String rankingA(CandidateStats stats) {
        String ranking;
        ranking = "A";
        ranking = addPlus(stats, ranking);
        return ranking;
    }

    private String addPlus(CandidateStats stats, String ranking) {
        if (stats.getAverageReturn().compareTo(BigDecimal.valueOf(10.0)) > 0) {
            ranking += "+";
        }
        if (stats.getAverageReturn().compareTo(BigDecimal.valueOf(15.0)) > 0) {
            ranking += "+";
        }
        if (stats.getAverageReturn().compareTo(BigDecimal.valueOf(20.0)) > 0) {
            ranking += "+";
        }
        return ranking;
    }
}
