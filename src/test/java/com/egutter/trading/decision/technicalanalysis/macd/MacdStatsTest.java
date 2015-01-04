package com.egutter.trading.decision.technicalanalysis.macd;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

public class MacdStatsTest {

    @Test
    public void no_sign_change() {

        assertThat(macd(1.0, 1.0).signChange(),
                equalTo(SignChange.NO_CHANGE));

        assertThat(macd(1.0, 0.9).signChange(),
                equalTo(SignChange.NO_CHANGE));

        assertThat(macd(0.0, 0.1).signChange(),
                equalTo(SignChange.NO_CHANGE));

        assertThat(macd(0.0, -0.1).signChange(),
                equalTo(SignChange.NO_CHANGE));

        assertThat(macd(1.0, 0.0).signChange(),
                equalTo(SignChange.NO_CHANGE));

        assertThat(macd(-0.1, 0.0).signChange(),
                equalTo(SignChange.NO_CHANGE));

        assertThat(macd(-1.0, -0.1).signChange(),
                equalTo(SignChange.NO_CHANGE));

        assertThat(macd(-0.1, -1.0).signChange(),
                equalTo(SignChange.NO_CHANGE));
    }

    @Test
    public void sign_change_to_negative() {

        assertThat(macd(-1.0, 1.0).signChange(),
                equalTo(SignChange.NEGATIVE));

        assertThat(macd(-1.0, 0.9).signChange(),
                equalTo(SignChange.NEGATIVE));

        assertThat(macd(-0.1, 0.1).signChange(),
                equalTo(SignChange.NEGATIVE));

        assertThat(macd(-0.1, 1.0).signChange(),
                equalTo(SignChange.NEGATIVE));

    }

    @Test
    public void sign_change_to_positve() {

        assertThat(macd(1.0, -1.0).signChange(),
                equalTo(SignChange.POSITIVE));

        assertThat(macd(1.0, -0.9).signChange(),
                equalTo(SignChange.POSITIVE));

        assertThat(macd(0.1, -0.1).signChange(),
                equalTo(SignChange.POSITIVE));

        assertThat(macd(0.1, -1.0).signChange(),
                equalTo(SignChange.POSITIVE));

    }

    @Test
    public void no_difference_with_previous_day() {
        assertThat(macd(1.0, 1.0).getDifferenceWithPreviousDay(),
                equalTo(0.0));
        assertThat(macd(0.1, 0.1).getDifferenceWithPreviousDay(),
                equalTo(0.0));
        assertThat(macd(-1.0, -1.0).getDifferenceWithPreviousDay(),
                equalTo(0.0));
        assertThat(macd(-0.1, -0.1).getDifferenceWithPreviousDay(),
                equalTo(0.0));
    }

    @Test
    public void difference_within_same_sign() {
        assertThat(macd(1.0, 2.0).getDifferenceWithPreviousDay(),
                equalTo(1.0));
        assertThat(macd(-0.1, -1.1).getDifferenceWithPreviousDay(),
                equalTo(1.0));
        assertThat(macd(-1.0, -2.0).getDifferenceWithPreviousDay(),
                equalTo(1.0));
        assertThat(macd(0.1, 1.1).getDifferenceWithPreviousDay(),
                equalTo(1.0));
    }

    @Test
    public void difference_with_different_sign() {
        assertThat(macd(1.0, -2.0).getDifferenceWithPreviousDay(),
                closeTo(3.0, 0.01));
        assertThat(macd(-0.1, 1.1).getDifferenceWithPreviousDay(),
                closeTo(1.2, 0.01));
        assertThat(macd(-1.0, 2.0).getDifferenceWithPreviousDay(),
                closeTo(3.0, 0.01));
        assertThat(macd(0.1, -1.1).getDifferenceWithPreviousDay(),
                closeTo(1.2, 0.01));
    }


    private MacdStats macd(double divergence, double previousDaydivergence) {
        return new MacdStats(1.0, 1.1, divergence, previousDaydivergence);
    }

}