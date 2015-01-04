package com.egutter.trading.decision.technicalanalysis.macd;

import com.google.common.base.Joiner;

/**
 * Created by egutter on 12/6/14.
 */
public class MacdStats {
    public static final Double ZERO = Double.valueOf(0.0);
    private final Double macd;
    private final Double signal;
    private final Double divergence;
    private final Double previousDayDivergence;

    public MacdStats(double macd, double signal, double divergence, double previousDayDivergence) {
        this.macd = Double.valueOf(macd);
        this.signal = Double.valueOf(signal);
        this.divergence = Double.valueOf(divergence);
        this.previousDayDivergence = Double.valueOf(previousDayDivergence);
    }

    public Double getDifferenceWithPreviousDay() {
        return Double.valueOf(Math.abs(divergence - previousDayDivergence));
    }

    public SignChange signChange() {
        int divergenceCompareToZero = divergence.compareTo(ZERO);
        int prevDayDivCompareToZero = previousDayDivergence.compareTo(ZERO);
        if (divergenceCompareToZero == 0 || prevDayDivCompareToZero == 0) {
            return SignChange.NO_CHANGE;
        }
        if (divergenceCompareToZero > prevDayDivCompareToZero) {
            return SignChange.POSITIVE;
        }
        if (divergenceCompareToZero < prevDayDivCompareToZero) {
            return SignChange.NEGATIVE;
        }
        return SignChange.NO_CHANGE;

    }

    @Override
    public String toString() {
        return Joiner.on(": ").join("MACD", macd, "Signal", signal, "Divergence", divergence, "Difference with previous day", previousDayDivergence);
    }
}
