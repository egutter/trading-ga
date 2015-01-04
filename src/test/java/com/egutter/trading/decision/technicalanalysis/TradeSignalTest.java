package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.technicalanalysis.macd.SignChange;
import com.google.common.collect.Range;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class TradeSignalTest {

    @Test
    public void should_trade_when_receive_and_expect_positive_and_at_least_within_range() {

        assertThat(positiveSignalAtLeast(1.0).shouldTrade(SignChange.POSITIVE, 1.0),
                equalTo(DecisionResult.YES));

        assertThat(positiveSignalAtLeast(0).shouldTrade(SignChange.POSITIVE, 1.0),
                equalTo(DecisionResult.YES));

        assertThat(positiveSignalAtLeast(0.9).shouldTrade(SignChange.POSITIVE, 1.0),
                equalTo(DecisionResult.YES));
    }

    @Test
    public void should_trade_when_receive_and_expect_positive_and_at_most_within_range() {

        assertThat(positiveSignalAtMost(1.0).shouldTrade(SignChange.POSITIVE, 1.0),
            equalTo(DecisionResult.YES));

        assertThat(positiveSignalAtMost(1.0).shouldTrade(SignChange.POSITIVE, 0.0),
                equalTo(DecisionResult.YES));

        assertThat(positiveSignalAtMost(1.0).shouldTrade(SignChange.POSITIVE, 0.9),
                equalTo(DecisionResult.YES));
    }

    @Test
    public void should_not_trade_when_expect_positive_and_receive_other_even_with_at_least_within_range() {

        assertThat(positiveSignalAtLeast(1.0).shouldTrade(SignChange.NEGATIVE, 1.0),
                equalTo(DecisionResult.NO));

        assertThat(positiveSignalAtLeast(0).shouldTrade(SignChange.NO_CHANGE, 1.0),
                equalTo(DecisionResult.NO));

        assertThat(positiveSignalAtLeast(0.9).shouldTrade(SignChange.NEGATIVE, 1.0),
                equalTo(DecisionResult.NO));
    }

    @Test
    public void should_not_trade_when_expect_negative_and_receive_other_even_with_at_least_within_range() {

        assertThat(negativeSignalAtLeast(1.0).shouldTrade(SignChange.POSITIVE, 1.0),
                equalTo(DecisionResult.NO));

        assertThat(negativeSignalAtLeast(0).shouldTrade(SignChange.NO_CHANGE, 1.0),
                equalTo(DecisionResult.NO));

        assertThat(negativeSignalAtLeast(0.9).shouldTrade(SignChange.POSITIVE, 1.0),
                equalTo(DecisionResult.NO));
    }

    private TradeSignal positiveSignalAtLeast(double threshold) {
        return new TradeSignal(SignChange.POSITIVE, Range.atLeast(threshold));
    }

    private TradeSignal negativeSignalAtLeast(double threshold) {
        return new TradeSignal(SignChange.NEGATIVE, Range.atLeast(threshold));
    }

    private TradeSignal positiveSignalAtMost(double threshold) {
        return new TradeSignal(SignChange.POSITIVE, Range.atMost(threshold));
    }
}