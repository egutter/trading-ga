package com.egutter.trading.stats;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.repository.PortfolioRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by egutter on 4/17/15.
 */
public class CandidateStatsCollector {

    private PortfolioRepository portfolioRepository;

    public CandidateStatsCollector(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public CandidateStats statsFor(List<BuySellOperation> buySellOrders) {
        AtomicReference<BigDecimal> profitAccum = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger wonCount = new AtomicInteger(0);
        AtomicInteger lostCount = new AtomicInteger(0);
        List<BigDecimal> loses = new ArrayList<BigDecimal>();

        buySellOrders.forEach(buySellOrder -> {
            if (buySellOrder.isWon()) {
                wonCount.getAndIncrement();
            } else if (buySellOrder.isLost()) {
                lostCount.getAndIncrement();
                loses.add(buySellOrder.profitPctg30());
            }

            profitAccum.getAndAccumulate(buySellOrder.profitPctg30(), (currentProfit, newProfit) -> {
                return currentProfit.add(newProfit);
            });

            count.getAndIncrement();
        });

        BigDecimal averageReturn = count.intValue() > 0 ? profitAccum.get().divide(BigDecimal.valueOf(count.intValue()), RoundingMode.HALF_EVEN) : BigDecimal.ZERO;
        Collections.sort(loses);
        BigDecimal biggestLost = loses.isEmpty() ? BigDecimal.ZERO : loses.get(0);

        return new CandidateStats(averageReturn, biggestLost, wonCount.intValue(), lostCount.intValue());
    }

    public CandidateStats statsFor(Candidate aCandidate) {
        List<BuySellOperation> buySellOrders = new ArrayList<BuySellOperation>();
        portfolioRepository.forEachStat(aCandidate.key(), buySellOrder -> {
            buySellOrders.add(buySellOrder);
        });

        return statsFor(buySellOrders);
    }
}
