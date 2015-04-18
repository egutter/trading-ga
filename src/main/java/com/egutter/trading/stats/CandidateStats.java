package com.egutter.trading.stats;

import java.math.BigDecimal;
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

    public boolean isEmpty() {
        return wonCount == 0 && lostCount == 0;
    }
}
