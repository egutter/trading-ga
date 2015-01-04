package com.egutter.trading.runner;

import com.egutter.trading.decision.generator.BuyTradingDecisionGenerator;
import com.egutter.trading.decision.generator.MovingAverageConvergenceDivergenceGenerator;
import com.egutter.trading.genetic.Experiment;
import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Doubles;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;

public class SeveralSmallPeriodExperimentRunner {


    public static void main(String[] args) throws UnknownHostException {
        LocalTime startTime = LocalTime.now();

        new SeveralSmallPeriodExperimentRunner().run();

        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    private void run() {

        List<Pair<LocalDate, LocalDate>> periods = new ArrayList<Pair<LocalDate, LocalDate>>();
        periods.add(new Pair(new LocalDate(2011, 12, 1), new LocalDate(2012, 3, 31)));
        periods.add(new Pair(new LocalDate(2012, 2, 1), new LocalDate(2012, 5, 30)));
        periods.add(new Pair(new LocalDate(2012, 4, 1), new LocalDate(2012, 7, 31)));
        periods.add(new Pair(new LocalDate(2012, 6, 1), new LocalDate(2012, 9, 30)));
        periods.add(new Pair(new LocalDate(2012, 8, 1), new LocalDate(2012, 11, 30)));
        periods.add(new Pair(new LocalDate(2012, 10, 1), new LocalDate(2013, 1, 31)));

        periods.add(new Pair(new LocalDate(2012, 12, 1), new LocalDate(2013, 3, 31)));
        periods.add(new Pair(new LocalDate(2013, 2, 1), new LocalDate(2013, 5, 30)));
        periods.add(new Pair(new LocalDate(2013, 4, 1), new LocalDate(2013, 7, 31)));
        periods.add(new Pair(new LocalDate(2013, 6, 1), new LocalDate(2013, 9, 30)));
        periods.add(new Pair(new LocalDate(2013, 8, 1), new LocalDate(2013, 11, 30)));
        periods.add(new Pair(new LocalDate(2013, 10, 1), new LocalDate(2014, 1, 31)));

        periods.add(new Pair(new LocalDate(2013, 12, 1), new LocalDate(2014, 3, 31)));
        periods.add(new Pair(new LocalDate(2014, 2, 1), new LocalDate(2014, 5, 30)));
        periods.add(new Pair(new LocalDate(2014, 4, 1), new LocalDate(2014, 7, 31)));
        periods.add(new Pair(new LocalDate(2014, 6, 1), new LocalDate(2014, 9, 30)));
        periods.add(new Pair(new LocalDate(2014, 8, 1), new LocalDate(2014, 11, 30)));
        periods.add(new Pair(new LocalDate(2014, 10, 1), new LocalDate(2015, 1, 31)));

        List<BitString> bestCandidates = new ArrayList<BitString>();
        List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators = asList(MovingAverageConvergenceDivergenceGenerator.class);
        System.out.println("BEST CANDIDATES");
        periods.stream().forEach(pair -> {
            System.out.println("Period from " + pair.getFirst() + " to " + pair.getSecond());
            StockMarket stockMarket = new StockMarketBuilder().build(pair.getFirst(), pair.getSecond());
            bestCandidates.add(runOneStrategy(stockMarket, tradingDecisionGenerators));
        });
        System.out.println("==============================================");
        System.out.println("WINNER");

        LocalDate fromDate = new LocalDate(2012, 1, 1);
        LocalDate toDate = new LocalDate(2014, 12, 31);
        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);
        List<Pair<BitString, BigDecimal>> bestCandidatesEvaluated = from(bestCandidates).transform(new Function<BitString, Pair<BitString, BigDecimal>>() {
            @Override
            public Pair<BitString, BigDecimal> apply(BitString result) {
                OneCandidateRunner runner = new OneCandidateRunner(stockMarket, result, tradingDecisionGenerators);
                runner.run();
                return new Pair(result, runner.finalCash());
            }
        }).toList();

        BitString winner = Ordering.natural().onResultOf(new Function<Pair, Comparable>() {
            @Override
            public Comparable apply(Pair pair) {
                return (Comparable) pair.getSecond();
            }
        }).max(bestCandidatesEvaluated).getFirst();
        printResults(stockMarket, winner, tradingDecisionGenerators);
    }

    private BitString runOneStrategy(StockMarket stockMarket, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        System.out.println("=============================================");

        BitString bestCandidate = new Experiment(tradingDecisionGenerators).run(stockMarket);
        printResults(stockMarket, bestCandidate, tradingDecisionGenerators);
        return bestCandidate;
    }


    private static void printResults(StockMarket stockMarket, BitString result, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, result, tradingDecisionGenerators);
        runner.run();
        new PrintResult().print(runner, result);
    }

}
