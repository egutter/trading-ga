package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.technicalanalysis.MoneyFlowIndex;
import com.google.common.collect.Range;
import org.junit.Test;
import org.uncommons.maths.binary.BitString;

import static com.egutter.trading.helper.TestHelper.aStockPrices;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class MoneyFlowIndexGeneratorTest {

    @Test
    public void should_set_at_least_buy_threshold() throws Exception {
        assertThat(moneyFlowIndex("0000011111111").getBuyThreshold(),
                equalTo(Range.atLeast(5.0)));
        assertThat(moneyFlowIndex("0111111111111").getBuyThreshold(),
                equalTo(Range.atLeast(95.0)));
        assertThat(moneyFlowIndex("0100011111111").getBuyThreshold(),
                equalTo(Range.atLeast(45.0)));
        assertThat(moneyFlowIndex("0001011111111").getBuyThreshold(),
                equalTo(Range.atLeast(15.0)));
    }

    @Test
    public void should_set_at_most_buy_threshold() throws Exception {
        assertThat(moneyFlowIndex("1000011111111").getBuyThreshold(),
                equalTo(Range.atMost(5.0)));
        assertThat(moneyFlowIndex("1111111111111").getBuyThreshold(),
                equalTo(Range.atMost(95.0)));
        assertThat(moneyFlowIndex("1100011111111").getBuyThreshold(),
                equalTo(Range.atMost(45.0)));
        assertThat(moneyFlowIndex("1001011111111").getBuyThreshold(),
                equalTo(Range.atMost(15.0)));
    }

    @Test
    public void should_set_at_least_sell_threshold() throws Exception {
        assertThat(moneyFlowIndex("1111100000111").getSellThreshold(),
                equalTo(Range.atLeast(5.0)));
        assertThat(moneyFlowIndex("1111101111111").getSellThreshold(),
                equalTo(Range.atLeast(95.0)));
        assertThat(moneyFlowIndex("1111101000111").getSellThreshold(),
                equalTo(Range.atLeast(45.0)));
        assertThat(moneyFlowIndex("1111100010111").getSellThreshold(),
                equalTo(Range.atLeast(15.0)));
    }

    @Test
    public void should_set_at_most_sell_threshold() throws Exception {
        assertThat(moneyFlowIndex("1111110000111").getSellThreshold(),
                equalTo(Range.atMost(5.0)));
        assertThat(moneyFlowIndex("1111111111111").getSellThreshold(),
                equalTo(Range.atMost(95.0)));
        assertThat(moneyFlowIndex("1111111000111").getSellThreshold(),
                equalTo(Range.atMost(45.0)));
        assertThat(moneyFlowIndex("1111110010111").getSellThreshold(),
                equalTo(Range.atMost(15.0)));
    }


    @Test
    public void should_set_moving_average_days() throws Exception {
        assertThat(moneyFlowIndex("1111100000111").days(),
                equalTo(17));
        assertThat(moneyFlowIndex("1111100000000").days(),
                equalTo(10));
        assertThat(moneyFlowIndex("1111100000001").days(),
                equalTo(11));
        assertThat(moneyFlowIndex("1111100000100").days(),
                equalTo(14));
    }

    private MoneyFlowIndex moneyFlowIndex(String chromosome) {
        return (MoneyFlowIndex) new MoneyFlowIndexGenerator(new BitString(chromosome)).generateBuyDecision(aStockPrices());
    }

}