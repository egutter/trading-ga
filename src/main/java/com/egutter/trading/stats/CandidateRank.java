package com.egutter.trading.stats;

import com.google.common.base.Strings;

/**
 * Created by egutter on 11/14/15.
 */
public class CandidateRank {

    public static final String ALPHA_RANK = "A";
    public static final String BETA_RANK = "B";

    private String primaryRank;
    private String secondaryRank;

    public CandidateRank(String primaryRank) {
        this.primaryRank = primaryRank;
        this.secondaryRank = "";
    }

    public static CandidateRank alphaRank() {
        return new CandidateRank(ALPHA_RANK);
    }

    public static CandidateRank betaRank() {
        return new CandidateRank(BETA_RANK);
    }

    public static CandidateRank ceRank() {
        return new CandidateRank("C");
    }

    public static CandidateRank deRank() {
        return new CandidateRank("D");
    }

    public static CandidateRank epsilonRank() {
        return new CandidateRank("E");
    }

    public static CandidateRank unknown() {
        return new CandidateRank("?");
    }

    public static CandidateRank zetaRank() {
        return new CandidateRank("Z");
    }

    public static CandidateRank chiRank() {
        return new CandidateRank("X");
    }

    public boolean isHighRank() {
        return primaryRank.equals(ALPHA_RANK) || primaryRank.equals(BETA_RANK);
    }

    @Override
    public String toString() {
        return "[" + rankingAsString() + "] ";
    }

    public String rankingAsString() {
        return primaryRank + secondaryRank;
    }

    public void addPlus() {
        this.secondaryRank += "+";
    }

    public void addPlus(int times) {
        this.secondaryRank += Strings.repeat("+", times);
    }

}
