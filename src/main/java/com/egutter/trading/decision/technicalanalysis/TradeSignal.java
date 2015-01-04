package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.technicalanalysis.macd.SignChange;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Range;
import com.google.common.hash.Hashing;

/**
 * Created by egutter on 12/7/14.
 */
public class TradeSignal {
    private SignChange expectedSignChange;
    private Range<Double> expectedDifferenceWithPreviousDay;

    public TradeSignal(SignChange signChange, Range<Double> differenceWithPreviousDay) {
        this.expectedSignChange = signChange;
        this.expectedDifferenceWithPreviousDay = differenceWithPreviousDay;
    }

    public DecisionResult shouldTrade(SignChange signChange, Double differenceWithPreviousDay) {
        boolean shouldTrade = expectedSignChange.equals(signChange) &&
                expectedDifferenceWithPreviousDay.contains(differenceWithPreviousDay);

        return shouldTrade ? DecisionResult.YES : DecisionResult.NO;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!TradeSignal.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        TradeSignal anotherTradeSignal = (TradeSignal) obj;

        return expectedSignChange.equals(anotherTradeSignal.expectedSignChange) &&
                expectedDifferenceWithPreviousDay.equals(anotherTradeSignal.expectedDifferenceWithPreviousDay);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(expectedSignChange, expectedDifferenceWithPreviousDay);
    }

    @Override
    public String toString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "expected sign change",
                expectedSignChange,
                "expected difference with previous day",
                expectedDifferenceWithPreviousDay);
    }
}
