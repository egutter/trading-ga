package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.helper.TestStockPrices;
import com.egutter.trading.order.condition.BuyDecisionConditionsFactory;
import com.egutter.trading.stock.TimeFrameQuote;
import com.google.common.collect.Range;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

import static com.egutter.trading.decision.technicalanalysis.FibonacciRetracementBuyDecision.*;
import static com.egutter.trading.helper.TestHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class FibonacciRetracementBuyDecisionTest {

    private double buyLevel = 10.0;

    @Test
    public void whenLowQuoteIsAfterHighQuote_noOrderIsGenerated() {
        FibonacciRetracementBuyDecision buyDecision = new FibonacciRetracementBuyDecision(TestStockPrices.lowDateAfterHighDate(aStockName()), retracementLevel(), buyLevel, 5, 10, FIB_EXT_1, FIB_EXT_1_236, BuyDecisionConditionsFactory.empty());

        assertThat(buyDecision.generateOrder(aTimeFrameQuote()), equalTo(Optional.empty()));
    }

    @Test
    public void whenPreviousQuoteIsLowerThenCurrentQuote_noOrderIsGenerated() {
        FibonacciRetracementBuyDecision buyDecision = new FibonacciRetracementBuyDecision(TestStockPrices.lowDateBeforeHighDate(aStockName()), retracementLevel(), buyLevel, 5, 10, FIB_EXT_1, FIB_EXT_1_236, BuyDecisionConditionsFactory.empty());

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aDailyQuote(100.00), aDailyQuote(90.00), aDailyQuote());

        assertThat(buyDecision.generateOrder(timeFrameQuote), equalTo(Optional.empty()));
    }

    @Test
    public void whenCloseAndLowFallsAboveRetracement_noOrderIsGenerated() {
        FibonacciRetracementBuyDecision buyDecision = new FibonacciRetracementBuyDecision(TestStockPrices.withHighOverLow(aStockName(), 50.0), retracementLevel(), buyLevel, 5, 10, FIB_EXT_1, FIB_EXT_1_236, BuyDecisionConditionsFactory.empty());

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aDailyQuote(75.00), aDailyQuote(90.00), aDailyQuote());

        assertThat(buyDecision.generateOrder(timeFrameQuote), equalTo(Optional.empty()));
    }

    @Test
    public void whenCloseAndLowFallsBellowRetracement_noOrderIsGenerated() {
        FibonacciRetracementBuyDecision buyDecision = new FibonacciRetracementBuyDecision(TestStockPrices.withHighOverLow(aStockName(), 50.0), retracementLevel(), buyLevel, 5, 10, FIB_EXT_1, FIB_EXT_1_236, BuyDecisionConditionsFactory.empty());

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aDailyQuote(65.00), aDailyQuote(90.00), aDailyQuote());

        assertThat(buyDecision.generateOrder(timeFrameQuote), equalTo(Optional.empty()));
    }

    @Test
    public void whenCloseIsWithinRetracement_anOrderIsGenerated() {
        FibonacciRetracementBuyDecision buyDecision = new FibonacciRetracementBuyDecision(TestStockPrices.withHighOverLow(aStockName(), 50.0), retracementLevel(), buyLevel, 5, 10, FIB_EXT_1, FIB_EXT_1_236, BuyDecisionConditionsFactory.empty());

        TimeFrameQuote timeFrameQuote = new TimeFrameQuote(aDailyQuote(69.10), aDailyQuote(90.00), aDailyQuote());

        assertThat(buyDecision.generateOrder(timeFrameQuote).isPresent(), equalTo(true));
    }

    private Range<BigDecimal> retracementLevel() {
        MathContext mc = new MathContext(2, RoundingMode.DOWN);
        BigDecimal low = new BigDecimal(FIB_RETR_0_618, mc);
        BigDecimal high = low.add(BigDecimal.valueOf(0.01));
        return Range.open(low, high);
    }

}