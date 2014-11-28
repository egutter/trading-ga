package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.MoneyFlowIndex;
import com.google.common.collect.Range;
import org.junit.Test;
import org.uncommons.maths.binary.BitString;

import static com.egutter.trading.helper.TestHelper.aStockPrices;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 2/14/14.
 */
public class MoneyFlowGeneratorTest {

    @Test
    public void should_set_at_least_buy_threshold() throws Exception {
        assertThat(moneyFlowIndex("10000001111111").getBuyThreshold(),
                equalTo(Range.atLeast(5.0)));
        assertThat(moneyFlowIndex("10111101111111").getBuyThreshold(),
                equalTo(Range.atLeast(95.0)));
        assertThat(moneyFlowIndex("10010001111111").getBuyThreshold(),
                equalTo(Range.atLeast(25.0)));
        assertThat(moneyFlowIndex("10100101111111").getBuyThreshold(),
                equalTo(Range.atLeast(65.0)));
        assertThat(moneyFlowIndex("10001001111111").getBuyThreshold(),
                equalTo(Range.atLeast(15.0)));
    }

    @Test
    public void should_set_at_most_buy_threshold() throws Exception {
        assertThat(moneyFlowIndex("11000001111111").getBuyThreshold(),
                equalTo(Range.atMost(5.0)));
        assertThat(moneyFlowIndex("11111101111111").getBuyThreshold(),
                equalTo(Range.atMost(95.0)));
        assertThat(moneyFlowIndex("11010001111111").getBuyThreshold(),
                equalTo(Range.atMost(25.0)));
        assertThat(moneyFlowIndex("11100101111111").getBuyThreshold(),
                equalTo(Range.atMost(65.0)));
        assertThat(moneyFlowIndex("11001001111111").getBuyThreshold(),
                equalTo(Range.atMost(15.0)));
    }

    @Test
    public void should_set_at_least_sell_threshold() throws Exception {
        assertThat(moneyFlowIndex("11111100000111").getSellThreshold(),
                equalTo(Range.atLeast(5.0)));
        assertThat(moneyFlowIndex("11111101111111").getSellThreshold(),
                equalTo(Range.atLeast(95.0)));
        assertThat(moneyFlowIndex("11111100100111").getSellThreshold(),
                equalTo(Range.atLeast(25.0)));
        assertThat(moneyFlowIndex("11111101001111").getSellThreshold(),
                equalTo(Range.atLeast(65.0)));
        assertThat(moneyFlowIndex("11111100010111").getSellThreshold(),
                equalTo(Range.atLeast(15.0)));
    }

    @Test
    public void should_set_at_most_sell_threshold() throws Exception {
        assertThat(moneyFlowIndex("11111110000111").getSellThreshold(),
                equalTo(Range.atMost(5.0)));
        assertThat(moneyFlowIndex("11111111111111").getSellThreshold(),
                equalTo(Range.atMost(95.0)));
        assertThat(moneyFlowIndex("11111110100111").getSellThreshold(),
                equalTo(Range.atMost(25.0)));
        assertThat(moneyFlowIndex("11111111001111").getSellThreshold(),
                equalTo(Range.atMost(65.0)));
        assertThat(moneyFlowIndex("11111110010111").getSellThreshold(),
                equalTo(Range.atMost(15.0)));
    }


    @Test
    public void should_set_days() throws Exception {
        assertThat(moneyFlowIndex("11111111111111").days(),
                equalTo(17));
        assertThat(moneyFlowIndex("11111111111000").days(),
                equalTo(10));
        assertThat(moneyFlowIndex("11111111111001").days(),
                equalTo(11));
        assertThat(moneyFlowIndex("11111111111100").days(),
                equalTo(14));
    }

    private MoneyFlowIndex moneyFlowIndex(String chromosome) {
        return (MoneyFlowIndex) new MoneyFlowIndexGenerator(new BitString(chromosome)).generateBuyDecision(aStockPrices());
    }
}
