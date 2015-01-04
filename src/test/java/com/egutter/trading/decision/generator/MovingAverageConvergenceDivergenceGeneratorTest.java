package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.MovingAverageConvergenceDivergence;
import com.egutter.trading.decision.technicalanalysis.TradeSignal;
import com.egutter.trading.decision.technicalanalysis.macd.SignChange;
import com.google.common.collect.Range;
import org.junit.Test;
import org.uncommons.maths.binary.BitString;

import static com.egutter.trading.helper.TestHelper.aStockPrices;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class MovingAverageConvergenceDivergenceGeneratorTest {

    @Test
    public void test_buy_signal() {
        assertThat(macd("0000001111111").getBuySignal(),
                equalTo(new TradeSignal(SignChange.POSITIVE, Range.atLeast(0.01))));

        assertThat(macd("0110011111111").getBuySignal(),
                equalTo(new TradeSignal(SignChange.NEGATIVE, Range.atMost(0.09))));

        assertThat(macd("1001001111111").getBuySignal(),
                equalTo(new TradeSignal(SignChange.NO_CHANGE, Range.atLeast(1.29))));

        assertThat(macd("1111111111111").getBuySignal(),
                equalTo(new TradeSignal(SignChange.NO_CHANGE, Range.atMost(5.19))));
    }

    @Test
    public void test_sell_signal() {
        assertThat(macd("1111110000001").getSellSignal(),
                equalTo(new TradeSignal(SignChange.POSITIVE, Range.atLeast(0.01))));

        assertThat(macd("1111110110011").getSellSignal(),
                equalTo(new TradeSignal(SignChange.NEGATIVE, Range.atMost(0.09))));

        assertThat(macd("1111111001001").getSellSignal(),
                equalTo(new TradeSignal(SignChange.NO_CHANGE, Range.atLeast(1.29))));

        assertThat(macd("1111111111111").getSellSignal(),
                equalTo(new TradeSignal(SignChange.NO_CHANGE, Range.atMost(5.19))));
    }

    private MovingAverageConvergenceDivergence macd(String chromosome) {
        return (MovingAverageConvergenceDivergence) new MovingAverageConvergenceDivergenceGenerator(new BitString(chromosome)).generateBuyDecision(aStockPrices());
    }
}