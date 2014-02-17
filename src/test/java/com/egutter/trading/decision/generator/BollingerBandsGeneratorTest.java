package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BollingerBands;
import com.google.common.collect.Range;
import com.tictactec.ta.lib.MAType;
import org.junit.Test;
import org.uncommons.maths.binary.BitString;

import static com.egutter.trading.helper.TestHelper.aStockPrices;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 2/14/14.
 */
public class BollingerBandsGeneratorTest {

    @Test
    public void should_set_at_least_buy_threshold() throws Exception {
        assertThat(bollingerBands("100000111111111111111").getBuyThreshold(),
                equalTo(Range.atLeast(-2.0)));
        assertThat(bollingerBands("111111111111111111111").getBuyThreshold(),
                equalTo(Range.atLeast(2.0)));
        assertThat(bollingerBands("110111111111111111111").getBuyThreshold(),
                equalTo(Range.atLeast(0.99)));
        assertThat(bollingerBands("101101111111111111111").getBuyThreshold(),
                equalTo(Range.atLeast(-0.31)));
    }

    @Test
    public void should_set_at_most_buy_threshold() throws Exception {
        assertThat(bollingerBands("100000011111111111111").getBuyThreshold(),
                equalTo(Range.atMost(-2.0)));
        assertThat(bollingerBands("111111011111111111111").getBuyThreshold(),
                equalTo(Range.atMost(2.0)));
        assertThat(bollingerBands("110111011111111111111").getBuyThreshold(),
                equalTo(Range.atMost(0.99)));
        assertThat(bollingerBands("101101011111111111111").getBuyThreshold(),
                equalTo(Range.atMost(-0.31)));
    }

    @Test
    public void should_set_at_least_sell_threshold() throws Exception {
        assertThat(bollingerBands("111111100000111111111").getSellThreshold(),
                equalTo(Range.atLeast(-2.0)));
        assertThat(bollingerBands("111111111111111111111").getSellThreshold(),
                equalTo(Range.atLeast(2.0)));
        assertThat(bollingerBands("111111110111111111111").getSellThreshold(),
                equalTo(Range.atLeast(0.99)));
        assertThat(bollingerBands("111111101101111111111").getSellThreshold(),
                equalTo(Range.atLeast(-0.31)));
    }

    @Test
    public void should_set_at_most_sell_threshold() throws Exception {
        assertThat(bollingerBands("111111100000011111111").getSellThreshold(),
                equalTo(Range.atMost(-2.0)));
        assertThat(bollingerBands("111111111111011111111").getSellThreshold(),
                equalTo(Range.atMost(2.0)));
        assertThat(bollingerBands("111111110111011111111").getSellThreshold(),
                equalTo(Range.atMost(0.99)));
        assertThat(bollingerBands("111111101101011111111").getSellThreshold(),
                equalTo(Range.atMost(-0.31)));
    }

    @Test
    public void should_set_moving_average_days() throws Exception {
        assertThat(bollingerBands("111111111111111111111").movingAverageDays(),
                equalTo(33));
        assertThat(bollingerBands("111111111111100000111").movingAverageDays(),
                equalTo(2));
        assertThat(bollingerBands("111111111111111011111").movingAverageDays(),
                equalTo(29));
        assertThat(bollingerBands("111111111111101110111").movingAverageDays(),
                equalTo(16));
    }

    @Test
    public void should_set_moving_average_type() throws Exception {
        assertThat(bollingerBands("111111111111111111000").movingAverageType(),
                equalTo(MAType.Sma));
        assertThat(bollingerBands("111111111111111111001").movingAverageType(),
                equalTo(MAType.Ema));
        assertThat(bollingerBands("111111111111111111101").movingAverageType(),
                equalTo(MAType.Trima));
        assertThat(bollingerBands("111111111111111111111").movingAverageType(),
                equalTo(MAType.Mama));
    }

    private BollingerBands bollingerBands(String chromosome) {
        return (BollingerBands) new BollingerBandsGenerator(new BitString(chromosome)).generate(aStockPrices());
    }
}
