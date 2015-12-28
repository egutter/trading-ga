package com.egutter.trading.stats;

import com.egutter.trading.order.BuySellOperation;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.egutter.trading.helper.TestHelper.aTradingDate;
import static com.egutter.trading.helper.TestHelper.buySellOperation;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CandidateStatsCollectorTest {

    private CandidateStatsCollector candidateStatsCollector = new CandidateStatsCollector(null);
    private List<BuySellOperation> buySellOrders = new ArrayList<BuySellOperation>();

    @Test
    public void should_collect_empty_stats() throws Exception {
        CandidateStats stats = candidateStatsCollector.statsFor(buySellOrders);

        assertThat(stats.isEmpty(), equalTo(true));
    }

    @Test
    public void should_collect_all_winners() throws Exception {
        addWinners();

        CandidateStats stats = candidateStatsCollector.statsFor(buySellOrders);

        assertThat(stats.isEmpty(), equalTo(false));
        assertThat(stats.getWonCount(), equalTo(2));
        assertThat(stats.getLostCount(), equalTo(0));
        assertThat(stats.getBiggestLost(), equalTo(BigDecimal.ZERO));
        assertThat(stats.getAverageReturn(), equalTo(BigDecimal.valueOf(70.2)));
    }

    @Test
    public void should_collect_all_loosers() throws Exception {
        addLoosers();

        CandidateStats stats = candidateStatsCollector.statsFor(buySellOrders);

        assertThat(stats.isEmpty(), equalTo(false));
        assertThat(stats.getWonCount(), equalTo(0));
        assertThat(stats.getLostCount(), equalTo(2));
        assertThat(stats.getBiggestLost(), equalTo(BigDecimal.valueOf(-20.0)));
        assertThat(stats.getAverageReturn(), equalTo(BigDecimal.valueOf(-15.4)));
    }

    @Test
    public void should_collect_winners_and_loosers() throws Exception {
        addWinners();
        addLoosers();

        CandidateStats stats = candidateStatsCollector.statsFor(buySellOrders);

        assertThat(stats.isEmpty(), equalTo(false));
        assertThat(stats.getWonCount(), equalTo(2));
        assertThat(stats.getLostCount(), equalTo(2));
        assertThat(stats.getBiggestLost(), equalTo(BigDecimal.valueOf(-20.0)));
        assertThat(stats.getAverageReturn(), equalTo(BigDecimal.valueOf(27.4)));
    }

    public void addLoosers() {
        buySellOrders.add(buySellOperation(2.77, aTradingDate(), 2.7, aTradingDate(7)));
        buySellOrders.add(buySellOperation(23.79, aTradingDate(), 23.7, aTradingDate(8)));
    }

    private void addWinners() {
        buySellOrders.add(buySellOperation(34.5, aTradingDate(), 41, aTradingDate(6)));
        buySellOrders.add(buySellOperation(35.05, aTradingDate(), 38.5, aTradingDate(7)));
    }

}