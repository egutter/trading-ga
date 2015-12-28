package com.egutter.trading.stats;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by egutter on 4/17/15.
 */
public class CandidateStats {
    private BigDecimal averageReturn;
    private BigDecimal biggestLost;
    private int wonCount;
    private int lostCount;

    public CandidateStats(BigDecimal averageReturn, BigDecimal biggestLost, int wonCount, int lostCount) {
        this.averageReturn = averageReturn;
        this.biggestLost = biggestLost;
        this.wonCount = wonCount;
        this.lostCount = lostCount;
    }

    public BigDecimal getAverageReturn() {
        return averageReturn;
    }

    public BigDecimal getBiggestLost() {
        return biggestLost;
    }

    public int getWonCount() {
        return wonCount;
    }

    public int getLostCount() {
        return lostCount;
    }

    public BigDecimal getLostPctg() {
        if (wonCount == 0) return BigDecimal.ZERO;
        MathContext mc3 = new MathContext(2, RoundingMode.HALF_EVEN);
        return BigDecimal.valueOf(lostCount).divide(BigDecimal.valueOf(wonCount), mc3).multiply(BigDecimal.valueOf(100), mc3);
    }

    public boolean isEmpty() {
        return wonCount == 0 && lostCount == 0;
    }
}
