package com.egutter.trading.runner;

import com.egutter.trading.decision.generator.*;
import com.egutter.trading.genetic.Experiment;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.uncommons.maths.binary.BitString;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class OneLongPeriodExperimentRunner {


    public static void main(String[] args) throws UnknownHostException {
        LocalTime startTime = LocalTime.now();

        new OneLongPeriodExperimentRunner().run();

        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    private void run() {

        LocalDate fromDate = new LocalDate(2014, 1, 1);
//        LocalDate toDate = new LocalDate(2013, 7, 1);
//        LocalDate fromDate = LocalDate.now().minusMonths(6);
        LocalDate toDate = LocalDate.now();
        System.out.println("Period from " + fromDate + " to " + toDate);

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, false, false);

//        runOneStrategy(stockMarket, asList(
//                        AverageDirectionalIndexGenerator.class,
//                        BollingerBandsGenerator.class,
//                        MoneyFlowIndexGenerator.class));
//
//        runOneStrategy(stockMarket, asList(
//                RelativeStrengthIndexGenerator.class,
//                BollingerBandsGenerator.class,
//                AroonOscilatorGenerator.class));
//
//        runOneStrategy(stockMarket, asList(
//                MovingAverageConvergenceDivergenceGenerator.class,
//                RelativeStrengthIndexGenerator.class,
//                BollingerBandsGenerator.class));
//
//        runOneStrategy(stockMarket, asList(
//                MovingAverageConvergenceDivergenceGenerator.class,
//                RelativeStrengthIndexGenerator.class,
//                MoneyFlowIndexGenerator.class));
//
//        runOneStrategy(stockMarket, asList(
//                MovingAverageConvergenceDivergenceGenerator.class,
//                RelativeStrengthIndexGenerator.class,
//                WilliamsRGenerator.class));
//
//        runOneStrategy(stockMarket, asList(
//                RelativeStrengthIndexGenerator.class,
//                AverageDirectionalIndexGenerator.class,
//                BollingerBandsGenerator.class));
//
//        runOneStrategy(stockMarket, asList(
//                RelativeStrengthIndexGenerator.class,
//                BollingerBandsGenerator.class,
//                WilliamsRGenerator.class));
//
//        runOneStrategy(stockMarket, asList(
//                RelativeStrengthIndexGenerator.class,
//                AroonOscilatorGenerator.class,
//                WilliamsRGenerator.class));
//
//        runOneStrategy(stockMarket, asList(
//                RelativeStrengthIndexGenerator.class,
//                UltimateOscillatorGenerator.class,
//                WilliamsRGenerator.class));
//
//        runOneStrategy(stockMarket, asList(
//                RelativeStrengthIndexGenerator.class,
//                BollingerBandsGenerator.class));
////

        ICombinatoricsVector<? extends Class<? extends BuyTradingDecisionGenerator>> initialVector = Factory.createVector(
                asList(
                        MovingAverageConvergenceDivergenceGenerator.class,
                        RelativeStrengthIndexGenerator.class,
                        AverageDirectionalIndexGenerator.class,
                        BollingerBandsGenerator.class,
                        AroonOscilatorGenerator.class,
                        MoneyFlowIndexGenerator.class,
                        UltimateOscillatorGenerator.class,
                        WilliamsRGenerator.class) );

        runAllCombinationsOf(stockMarket, initialVector, 3);
        runAllCombinationsOf(stockMarket, initialVector, 2);

    }

    private void runAllCombinationsOf(StockMarket stockMarket, ICombinatoricsVector<? extends Class<? extends BuyTradingDecisionGenerator>> initialVector, int numOfCombinations) {
        Generator<? extends Class<? extends BuyTradingDecisionGenerator>> gen = Factory.createSimpleCombinationGenerator(initialVector, numOfCombinations);

        for (ICombinatoricsVector<? extends Class<? extends BuyTradingDecisionGenerator>> combination : gen) {
            runOneStrategy(stockMarket, combination.getVector());
        }
    }

    private void runOneStrategy(StockMarket stockMarket, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        System.out.println("=============================================");

        printResults("OnlyFalls 2014-2016", stockMarket, new Experiment(tradingDecisionGenerators).run(stockMarket), tradingDecisionGenerators);
    }


    private static void printResults(String description, StockMarket stockMarket, BitString result, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        String tradingDecisionGeneratorsString = tradingDecisionGenerators.stream()
                .map(Class::getSimpleName)
                .collect(Collectors.joining(".class, "));

        System.out.println("new Candidate(\"" + description + "\", \"" + result + "\", asList(" + tradingDecisionGeneratorsString + ".class)),");

        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, result, tradingDecisionGenerators);
        runner.run();
    }

}
