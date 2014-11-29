package com.egutter.trading.decision.generator;

import com.egutter.trading.stock.Portfolio;
import org.junit.Test;
import org.uncommons.maths.binary.BitString;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 2/18/14.
 */
public class SellAfterAFixedNumberOfDaysGeneratorTest {
    @Test
    public void should_set_fixed_number_of_days_to_sell() throws Exception {

        assertThat(sellAfterAFixedNumberOfDays("11111").getNumberOfDays(), equalTo(36));
        assertThat(sellAfterAFixedNumberOfDays("00000").getNumberOfDays(), equalTo(5));
        assertThat(sellAfterAFixedNumberOfDays("00001").getNumberOfDays(), equalTo(6));
        assertThat(sellAfterAFixedNumberOfDays("10001").getNumberOfDays(), equalTo(22));
        assertThat(sellAfterAFixedNumberOfDays("01010").getNumberOfDays(), equalTo(15));

    }

    private SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDays(String chromosome) {
        return new SellAfterAFixedNumberOfDaysGenerator(new BitString(chromosome), new Portfolio());
    }
}
