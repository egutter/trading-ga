package com.egutter.trading.runner;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.factory.HardcodedTradingDecisionFactory;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.decision.technicalanalysis.StochasticOscillatorThreshold;
import com.egutter.trading.genetic.Experiment;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.stock.StockGroup;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.egutter.trading.stock.StockMarket.TECH_SECTOR;
import static com.egutter.trading.stock.StockMarket.techSector;
import static java.util.Arrays.asList;

public class OneLongPeriodExperimentRunner {


    public static void main(String[] args) throws UnknownHostException {
        LocalTime startTime = LocalTime.now();

        new OneLongPeriodExperimentRunner().run();

        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    private void run() {

        LocalDate fromDate = new LocalDate(2010, 1, 1);
//        LocalDate toDate = new LocalDate(2019, 12, 31);
//        LocalDate fromDate = LocalDate.now().minusMonths(6);
        LocalDate toDate = LocalDate.now();
        System.out.println("Period from " + fromDate + " to " + toDate);

//        List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators = asList(StochasticOscillatorGenerator.class,
//                ChaikinOscillatorGenerator.class);

        // With all STOCK GROUPS
//        StockMarket.onlyIndividualSectors().forEach(stockGroup -> {
//            System.out.println("=============================================");
//            System.out.println(stockGroup.getFullName());
//            System.out.println("=============================================");
//            String[] symbols = stockGroup.getStockSymbols();
//            StockMarket stockMarket = new StockMarketBuilder().build(fromDate, LocalDate.now(), symbols);
//            runOneStrategy(stockMarket, tradingDecisionGenerators);
//        });

        // One Sector
        StockMarket.allSectors().stream().forEach(stockGroup -> {

            StockMarket stockMarket = new StockMarketBuilder().build(fromDate, LocalDate.now(), false, false,
                    stockGroup.getStockSymbols());
    //        runOneStrategy(stockMarket, tradingDecisionGenerators);

            // All combinations
            ICombinatoricsVector<? extends Class<? extends ConditionalOrderConditionGenerator>> initialVector = Factory.createVector(
            asList(
                    StochasticOscillatorGenerator.class,
                    ChaikinOscillatorGenerator.class,
                    MovingAverageConvergenceDivergenceGenerator.class,
                    RelativeStrengthIndexGenerator.class,
                    AverageDirectionalIndexGenerator.class,
                    BollingerBandsGenerator.class,
                    AroonOscilatorGenerator.class,
                    MoneyFlowIndexGenerator.class,
                    UltimateOscillatorGenerator.class,
                    WilliamsRGenerator.class) );

            runAllCombinationsOf(stockMarket, initialVector, 2, stockGroup);
        });
//        EACH INDIVIDUAL
//        List<String[]> stocks = new ArrayList();
//        stocks.add(StockMarket.metals());
//        StockMarket.individualStocks().stream().forEach(stock -> {
//            System.out.println("=============================================");
//            System.out.println(stock[0]);
//            System.out.println("=============================================");
//            StockMarket stockMarket = new StockMarketBuilder().build(fromDate, LocalDate.now(), false, false, stock);
//            runOneStrategy(stockMarket, tradingDecisionGenerators);
//        });
    }

    private void oldStrategies() {
        //        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, false, false);
//        StockMarket stockMarket = new StockMarketBuilder().buildInMemory(fromDate);

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

//        ICombinatoricsVector<? extends Class<? extends BuyTradingDecisionGenerator>> initialVector = Factory.createVector(
//                asList(
//                        MovingAverageConvergenceDivergenceGenerator.class,
//                        RelativeStrengthIndexGenerator.class,
//                        AverageDirectionalIndexGenerator.class,
//                        BollingerBandsGenerator.class,
//                        AroonOscilatorGenerator.class,
//                        MoneyFlowIndexGenerator.class,
//                        UltimateOscillatorGenerator.class,
//                        WilliamsRGenerator.class) );
//
//        runAllCombinationsOf(stockMarket, initialVector, 3);
//        runAllCombinationsOf(stockMarket, initialVector, 2);
    }

    private void runAllCombinationsOf(StockMarket stockMarket, ICombinatoricsVector<? extends Class<? extends ConditionalOrderConditionGenerator>> initialVector, int numOfCombinations, StockGroup stockGroup) {
        Generator<? extends Class<? extends ConditionalOrderConditionGenerator>> gen = Factory.createSimpleCombinationGenerator(initialVector, numOfCombinations);

        for (ICombinatoricsVector<? extends Class<? extends ConditionalOrderConditionGenerator>> combination : gen) {
            runOneStrategy(stockMarket, combination.getVector(), stockGroup);
        }
    }

    private void runOneStrategy(StockMarket stockMarket, List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators, StockGroup stockGroup) {
        System.out.println("=============================================");

        printResults("2001-2020", stockMarket, new Experiment(tradingDecisionGenerators).run(stockMarket), tradingDecisionGenerators, stockGroup);
    }


    private static void printResults(String description, StockMarket stockMarket, BitString result, List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators, StockGroup stockGroup) {
        String tradingDecisionGeneratorsString = tradingDecisionGenerators.stream()
                .map(Class::getSimpleName)
                .collect(Collectors.joining(".class, "));

        String tradingDecisionGeneratorsClassList = "asList(" + tradingDecisionGeneratorsString + ".class)";
//        System.out.println("new Candidate(\"" + description + "\", \"" + result + "\", " + tradingDecisionGeneratorsClassList + "),");

        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, result, tradingDecisionGenerators);
        runner.run();

        if (runner.percentageOfOrdersWon().compareTo(new BigDecimal(0.90)) >= 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("new Candidate(\"");
            sb.append(stockGroup.getName());
            sb.append("\", \"");
            sb.append(result);
            sb.append("\",\n");
            sb.append(tradingDecisionGeneratorsClassList);
            sb.append(",\n");
            sb.append("asList(\n");
            sb.append("new StockGroup(\"");
            sb.append(stockGroup.getName());
            sb.append("\",\"");
            sb.append(runner.percentageOfOrdersWon());
            sb.append("(");
            sb.append(runner.ordersWon());
            sb.append("/");
            sb.append(runner.ordersLost());
            sb.append(")\", ");
            sb.append(stockGroup.getFullName());
            sb.append("())\n");
            sb.append(")),\n");
            System.out.println(sb.toString());
        }
    }

}
