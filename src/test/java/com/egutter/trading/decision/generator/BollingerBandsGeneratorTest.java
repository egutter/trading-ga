package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.BollingerBands;
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
        assertThat(bollingerBands("0000011111111").getBuyThreshold(),
                equalTo(Range.atLeast(-2.0)));
        assertThat(bollingerBands("0111111111111").getBuyThreshold(),
                equalTo(Range.atLeast(2.0)));
        assertThat(bollingerBands("0100011111111").getBuyThreshold(),
                equalTo(Range.atLeast(0.25)));
        assertThat(bollingerBands("0001011111111").getBuyThreshold(),
                equalTo(Range.atLeast(-1.5)));
    }

    @Test
    public void should_set_at_most_buy_threshold() throws Exception {
        assertThat(bollingerBands("1000011111111").getBuyThreshold(),
                equalTo(Range.atMost(-2.0)));
        assertThat(bollingerBands("1111111111111").getBuyThreshold(),
                equalTo(Range.atMost(2.0)));
        assertThat(bollingerBands("1100011111111").getBuyThreshold(),
                equalTo(Range.atMost(0.25)));
        assertThat(bollingerBands("1001011111111").getBuyThreshold(),
                equalTo(Range.atMost(-1.5)));
    }

    @Test
    public void should_set_at_least_sell_threshold() throws Exception {
        assertThat(bollingerBands("1111100000111").getSellThreshold(),
                equalTo(Range.atLeast(-2.0)));
        assertThat(bollingerBands("1111101111111").getSellThreshold(),
                equalTo(Range.atLeast(2.0)));
        assertThat(bollingerBands("1111101000111").getSellThreshold(),
                equalTo(Range.atLeast(0.25)));
        assertThat(bollingerBands("1111100010111").getSellThreshold(),
                equalTo(Range.atLeast(-1.5)));
    }

    @Test
    public void should_set_at_most_sell_threshold() throws Exception {
        assertThat(bollingerBands("1111110000111").getSellThreshold(),
                equalTo(Range.atMost(-2.0)));
        assertThat(bollingerBands("1111111111111").getSellThreshold(),
                equalTo(Range.atMost(2.0)));
        assertThat(bollingerBands("1111111000111").getSellThreshold(),
                equalTo(Range.atMost(0.25)));
        assertThat(bollingerBands("1111110010111").getSellThreshold(),
                equalTo(Range.atMost(-1.5)));
    }


    @Test
    public void should_set_moving_average_days() throws Exception {
        assertThat(bollingerBands("1111100000111").movingAverageDays(),
                equalTo(22));
        assertThat(bollingerBands("1111100000000").movingAverageDays(),
                equalTo(15));
        assertThat(bollingerBands("1111100000001").movingAverageDays(),
                equalTo(16));
        assertThat(bollingerBands("1111100000100").movingAverageDays(),
                equalTo(19));
    }

    @Test
    public void should_set_moving_average_type() throws Exception {
        assertThat(bollingerBands("1111100000001").movingAverageType(),
                equalTo(MAType.Wma));
    }

    private BollingerBands bollingerBands(String chromosome) {
        return (BollingerBands) new BollingerBandsGenerator(new BitString(chromosome)).generateBuyDecision(aStockPrices());
    }
}
