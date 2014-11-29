package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.AroonOscilator;
import com.google.common.collect.Range;
import org.junit.Test;
import org.uncommons.maths.binary.BitString;

import static com.egutter.trading.helper.TestHelper.aStockPrices;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class AroonOscilatorGeneratorTest {
    @Test
    public void should_set_at_least_buy_threshold() throws Exception {
        assertThat(aroonOscilator("0000011111111").getBuyThreshold(),
                equalTo(Range.atLeast(-90.0)));
        assertThat(aroonOscilator("0111111111111").getBuyThreshold(),
                equalTo(Range.atLeast(90.0)));
        assertThat(aroonOscilator("0100011111111").getBuyThreshold(),
                equalTo(Range.atLeast(-10.0)));
        assertThat(aroonOscilator("0001011111111").getBuyThreshold(),
                equalTo(Range.atLeast(-70.0)));
    }

    @Test
    public void should_set_at_most_buy_threshold() throws Exception {
        assertThat(aroonOscilator("1000011111111").getBuyThreshold(),
                equalTo(Range.atMost(-90.0)));
        assertThat(aroonOscilator("1111111111111").getBuyThreshold(),
                equalTo(Range.atMost(90.0)));
        assertThat(aroonOscilator("1100011111111").getBuyThreshold(),
                equalTo(Range.atMost(-10.0)));
        assertThat(aroonOscilator("1001011111111").getBuyThreshold(),
                equalTo(Range.atMost(-70.0)));
    }

    @Test
    public void should_set_at_least_sell_threshold() throws Exception {
        assertThat(aroonOscilator("1111100000111").getSellThreshold(),
                equalTo(Range.atLeast(-90.0)));
        assertThat(aroonOscilator("1111101111111").getSellThreshold(),
                equalTo(Range.atLeast(90.0)));
        assertThat(aroonOscilator("1111101000111").getSellThreshold(),
                equalTo(Range.atLeast(-10.0)));
        assertThat(aroonOscilator("1111100010111").getSellThreshold(),
                equalTo(Range.atLeast(-70.0)));
    }

    @Test
    public void should_set_at_most_sell_threshold() throws Exception {
        assertThat(aroonOscilator("1111110000111").getSellThreshold(),
                equalTo(Range.atMost(-90.0)));
        assertThat(aroonOscilator("1111111111111").getSellThreshold(),
                equalTo(Range.atMost(90.0)));
        assertThat(aroonOscilator("1111111000111").getSellThreshold(),
                equalTo(Range.atMost(-10.0)));
        assertThat(aroonOscilator("1111110010111").getSellThreshold(),
                equalTo(Range.atMost(-70.0)));
    }


    @Test
    public void should_set_moving_average_days() throws Exception {
        assertThat(aroonOscilator("1111100000111").days(),
                equalTo(28));
        assertThat(aroonOscilator("1111100000000").days(),
                equalTo(21));
        assertThat(aroonOscilator("1111100000001").days(),
                equalTo(22));
        assertThat(aroonOscilator("1111100000100").days(),
                equalTo(25));
    }

    private AroonOscilator aroonOscilator(String chromosome) {
        return (AroonOscilator) new AroonOscilatorGenerator(new BitString(chromosome)).generateBuyDecision(aStockPrices());
    }
}