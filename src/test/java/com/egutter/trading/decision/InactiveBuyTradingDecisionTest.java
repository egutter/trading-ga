package com.egutter.trading.decision;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 2/13/14.
 */
public class InactiveBuyTradingDecisionTest {

    @Test
    public void should_always_return_false() throws Exception {
        InactiveTradingDecision decision = new InactiveTradingDecision();

        assertThat(decision.shouldSellOn(new LocalDate(2014, 1, 1)), equalTo(false));
        assertThat(decision.shouldBuyOn(new LocalDate(2014, 1, 1)), equalTo(false));
    }
}
