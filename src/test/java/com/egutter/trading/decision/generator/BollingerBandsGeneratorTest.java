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
        assertThat(bollingerBands("1000011111111111").getBuyThreshold(),
                equalTo(Range.atLeast(-2.0)));
        assertThat(bollingerBands("1111111111111111").getBuyThreshold(),
                equalTo(Range.atLeast(2.0)));
        assertThat(bollingerBands("1100011111111111").getBuyThreshold(),
                equalTo(Range.atLeast(0.25)));
        assertThat(bollingerBands("1001011111111111").getBuyThreshold(),
                equalTo(Range.atLeast(-1.5)));
    }

    @Test
    public void should_set_at_least_sell_threshold() throws Exception {
        assertThat(bollingerBands("1111100001111111").getSellThreshold(),
                equalTo(Range.atLeast(-2.0)));
        assertThat(bollingerBands("1111111111111111").getSellThreshold(),
                equalTo(Range.atLeast(2.0)));
        assertThat(bollingerBands("1111110001111111").getSellThreshold(),
                equalTo(Range.atLeast(0.25)));
        assertThat(bollingerBands("1111100101111111").getSellThreshold(),
                equalTo(Range.atLeast(-1.5)));
    }


    @Test
    public void should_set_moving_average_days() throws Exception {
        assertThat(bollingerBands("1111111111111111").movingAverageDays(),
                equalTo(27));
        assertThat(bollingerBands("1111111110000111").movingAverageDays(),
                equalTo(12));
        assertThat(bollingerBands("1111111110010111").movingAverageDays(),
                equalTo(14));
        assertThat(bollingerBands("1111111111000111").movingAverageDays(),
                equalTo(20));
    }

    @Test
    public void should_set_moving_average_type() throws Exception {
        assertThat(bollingerBands("1111111111111000").movingAverageType(),
                equalTo(MAType.Sma));
        assertThat(bollingerBands("1111111111111101").movingAverageType(),
                equalTo(MAType.Trima));
        assertThat(bollingerBands("1111111111111011").movingAverageType(),
                equalTo(MAType.Dema));
        assertThat(bollingerBands("1111111111111111").movingAverageType(),
                equalTo(MAType.Mama));
    }

    private BollingerBands bollingerBands(String chromosome) {
        return (BollingerBands) new BollingerBandsGenerator(new BitString(chromosome)).generateBuyDecision(aStockPrices());
    }
}
