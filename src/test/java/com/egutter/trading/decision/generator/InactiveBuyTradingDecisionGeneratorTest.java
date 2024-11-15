package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.InactiveTradingDecision;
import org.junit.Test;
import org.uncommons.maths.binary.BitString;

import static com.egutter.trading.helper.TestHelper.aStockPrices;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 2/15/14.
 */
public class InactiveBuyTradingDecisionGeneratorTest {


    @Test
    public void should_generate_an_inactive_tradicing_decision() throws Exception {
        BitString chromosome = new BitString("111111111111111111111");
        BuyTradingDecision decision = inactiveTradingDecisionGenerator(chromosome).generateBuyDecision(aStockPrices());
        assertThat(decision, is(instanceOf(InactiveTradingDecision.class)));
    }


    @Test
    public void should_generate_an_inactive_trading_decision() throws Exception {
        BitString chromosome = new BitString("011111111111111111111");
        BuyTradingDecision decision = inactiveTradingDecisionGenerator(chromosome).generateBuyDecision(aStockPrices());
        assertThat(decision, is(instanceOf(InactiveTradingDecision.class)));

    }

    private BuyTradingDecisionGenerator inactiveTradingDecisionGenerator(BitString chromosome) {
        return new InactiveTradingDecisionGenerator(chromosome);
    }

}
