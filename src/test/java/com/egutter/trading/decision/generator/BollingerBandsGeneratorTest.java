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
        assertThat(bollingerBands("100000011111111").getBuyThreshold(),
                equalTo(Range.atLeast(-2.0)));
        assertThat(bollingerBands("101111011111111").getBuyThreshold(),
                equalTo(Range.atLeast(2.0)));
        assertThat(bollingerBands("101000011111111").getBuyThreshold(),
                equalTo(Range.atLeast(0.25)));
        assertThat(bollingerBands("100010011111111").getBuyThreshold(),
                equalTo(Range.atLeast(-1.5)));
    }

    @Test
    public void should_set_at_most_buy_threshold() throws Exception {
        assertThat(bollingerBands("110000011111111").getBuyThreshold(),
                equalTo(Range.atMost(-2.0)));
        assertThat(bollingerBands("111111011111111").getBuyThreshold(),
                equalTo(Range.atMost(2.0)));
        assertThat(bollingerBands("111000011111111").getBuyThreshold(),
                equalTo(Range.atMost(0.25)));
        assertThat(bollingerBands("110010011111111").getBuyThreshold(),
                equalTo(Range.atMost(-1.5)));
    }

    @Test
    public void should_set_at_least_sell_threshold() throws Exception {
        assertThat(bollingerBands("111111000001111").getSellThreshold(),
                equalTo(Range.atLeast(-2.0)));
        assertThat(bollingerBands("111111011111111").getSellThreshold(),
                equalTo(Range.atLeast(2.0)));
        assertThat(bollingerBands("111111010001111").getSellThreshold(),
                equalTo(Range.atLeast(0.25)));
        assertThat(bollingerBands("111111000101111").getSellThreshold(),
                equalTo(Range.atLeast(-1.5)));
    }

    @Test
    public void should_set_at_most_sell_threshold() throws Exception {
        assertThat(bollingerBands("111111100001111").getSellThreshold(),
                equalTo(Range.atMost(-2.0)));
        assertThat(bollingerBands("111111111111111").getSellThreshold(),
                equalTo(Range.atMost(2.0)));
        assertThat(bollingerBands("111111110001111").getSellThreshold(),
                equalTo(Range.atMost(0.25)));
        assertThat(bollingerBands("111111100101111").getSellThreshold(),
                equalTo(Range.atMost(-1.5)));
    }


    @Test
    public void should_set_moving_average_days() throws Exception {
        assertThat(bollingerBands("111111111111111").movingAverageDays(),
                equalTo(22));
        assertThat(bollingerBands("111111111110001").movingAverageDays(),
                equalTo(15));
        assertThat(bollingerBands("111111111110011").movingAverageDays(),
                equalTo(16));
        assertThat(bollingerBands("111111111111001").movingAverageDays(),
                equalTo(19));
    }

    @Test
    public void should_set_moving_average_type() throws Exception {
        assertThat(bollingerBands("111111111111110").movingAverageType(),
                equalTo(MAType.Sma));
        assertThat(bollingerBands("111111111111111").movingAverageType(),
                equalTo(MAType.Wma));
    }

    private BollingerBands bollingerBands(String chromosome) {
        return (BollingerBands) new BollingerBandsGenerator(new BitString(chromosome)).generateBuyDecision(aStockPrices());
    }
}
