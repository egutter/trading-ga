package com.egutter.trading.runner;

import com.egutter.trading.decision.generator.*;
import com.egutter.trading.genetic.NonGeneticExperiment;
import com.egutter.trading.genetic.TradingDecisionGenome;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.uncommons.maths.binary.BitString;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.factories.BitStringFactory;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class NonGeneticExperimentRunner {


    public static void main(String[] args) throws UnknownHostException {
        LocalTime startTime = LocalTime.now();
        long startNano = System.nanoTime();

        new NonGeneticExperimentRunner().run();

        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
        System.out.println("total time elapsed " +  (System.nanoTime()-startNano) + " miliseconds");
    }

    private void run() {

        LocalDate fromDate = new LocalDate(2016, 1, 1);
        LocalDate toDate = new LocalDate(2016, 7, 1);
//        LocalDate fromDate = LocalDate.now().minusMonths(6);
//        LocalDate toDate = LocalDate.now();
        System.out.println("Period from " + fromDate + " to " + toDate);

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);

//        runOneStrategy(stockMarket, asList(AroonOscilatorGenerator.class, AverageDirectionalIndexGenerator.class));

//        Conservative1stSem2015.candidates().parallelStream().forEach(candidate -> {
//            runOneStrategy(stockMarket, candidate.getTradingDecisionGenerators());
//        });

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
        runAllCombinationsOf(stockMarket, initialVector, 1);


    }

    private void runAllCombinationsOf(StockMarket stockMarket, ICombinatoricsVector<? extends Class<? extends BuyTradingDecisionGenerator>> initialVector, int numOfCombinations) {
        Generator<? extends Class<? extends BuyTradingDecisionGenerator>> gen = Factory.createSimpleCombinationGenerator(initialVector, numOfCombinations);

        for (ICombinatoricsVector<? extends Class<? extends BuyTradingDecisionGenerator>> combination : gen) {
            runOneStrategy(stockMarket, combination.getVector());
        }
    }

    private void runOneStrategy(StockMarket stockMarket, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {

        NonGeneticExperiment nonGeneticExperiment = new NonGeneticExperiment(tradingDecisionGenerators, stockMarket);

        CandidateFactory<BitString> candidateFactory = new BitStringFactory(genomeSize(tradingDecisionGenerators));

        Random rng = new MersenneTwisterRNG();
//        System.out.println("Generating random population");
        List<BitString> candidates = candidateFactory.generateInitialPopulation(100_000, rng);
//        System.out.println("Running candidates");
        candidates.forEach(bitCandidate -> {
//            Candidate aCandidate = new Candidate("", bitCandidate, tradingDecisionGenerators);
//            if (!FirstSemester2016Candidates.contains(aCandidate)) {
                nonGeneticExperiment.run(bitCandidate);
//            }
        });

        List<BitString> winningCandidates = nonGeneticExperiment.getWinningCandidates();

        String tradingDecisionGeneratorsString = tradingDecisionGenerators.stream()
                .map(Class::getSimpleName)
                .collect(Collectors.joining(".class, "));

        for (BitString candidate : winningCandidates) {
            printResults("2016-2016", tradingDecisionGeneratorsString, stockMarket, candidate, tradingDecisionGenerators);
        }
    }


    private static void printResults(String description, String tradingDecisionGeneratorsString, StockMarket stockMarket, BitString result, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {

        System.out.println("new Candidate(\"" + description + "\", \"" + result + "\", asList(" + tradingDecisionGeneratorsString + ".class)),");

//        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, result, tradingDecisionGenerators);
//        runner.run();
    }

    private int genomeSize(List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        return (TradingDecisionGenome.LENGTH * tradingDecisionGenerators.size()) + TradingDecisionGenome.HEAD_LENGTH;
    }

}
