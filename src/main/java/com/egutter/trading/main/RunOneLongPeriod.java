package com.egutter.trading.main;

import com.egutter.trading.decision.generator.TradingDecisionFactory;
import com.egutter.trading.genetic.StockTradingFitnessEvaluator;
import com.egutter.trading.genetic.TradingDecisionGenome;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.Trader;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.uncommons.maths.binary.BitString;
import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.factories.BitStringFactory;
import org.uncommons.watchmaker.framework.islands.IslandEvolution;
import org.uncommons.watchmaker.framework.islands.RingMigration;
import org.uncommons.watchmaker.framework.operators.BitStringCrossover;
import org.uncommons.watchmaker.framework.operators.BitStringMutation;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.Stagnation;

import java.net.UnknownHostException;
import java.util.*;

public class RunOneLongPeriod {


    public static void main(String[] args) throws UnknownHostException {
        LocalTime startTime = LocalTime.now();

        LocalDate fromDate = new LocalDate(2013, 1, 1);
        LocalDate toDate = new LocalDate(2014, 12, 31);
        System.out.println("Period from " + fromDate + " to" + toDate);

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);
        StockTradingFitnessEvaluator stockTradingFitnessEvaluator = new StockTradingFitnessEvaluator(stockMarket);

        RunOneLongPeriod main = new RunOneLongPeriod();

        BitString result = main.run(stockTradingFitnessEvaluator);
        printResults(stockMarket, stockTradingFitnessEvaluator, result);

        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    public BitString run(StockTradingFitnessEvaluator stockTradingFitnessEvaluator) {

        CandidateFactory<BitString> candidateFactory = new BitStringFactory(TradingDecisionGenome.SIZE);

        List<EvolutionaryOperator<BitString>> operators
                = new LinkedList<EvolutionaryOperator<BitString>>();
        operators.add(new BitStringCrossover());
        operators.add(new BitStringMutation(new ConstantGenerator<Probability>(new Probability(0.02)),
                new ConstantGenerator<Integer>(1)));

        EvolutionaryOperator<BitString> pipeline = new EvolutionPipeline<BitString>(operators);

        Random rng = new MersenneTwisterRNG();

        return buildEvolutionEngine(stockTradingFitnessEvaluator, candidateFactory, pipeline, rng);
    }


    private BitString buildEvolutionEngine(StockTradingFitnessEvaluator stockTradingFitnessEvaluator, CandidateFactory<BitString> candidateFactory, EvolutionaryOperator<BitString> pipeline, Random rng) {
        // You can also try an IslandEvolution with buildIslandEvolutionEngine(stockTradingFitnessEvaluator, candidateFactory, pipeline, rng)
        EvolutionEngine<BitString> engine
                = new GenerationalEvolutionEngine<BitString>(candidateFactory,
                pipeline,
                new CachingFitnessEvaluator(stockTradingFitnessEvaluator),
                getSelectionStrategy(),
                rng);

        return engine.evolve(1000, 10, new GenerationCount(100), new Stagnation(20, true));
    }

    private static void printResults(StockMarket stockMarket, StockTradingFitnessEvaluator stockTradingFitnessEvaluator, BitString result) {
        Portfolio portfolio = new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH);
        Trader trader = stockTradingFitnessEvaluator.buildTrader(portfolio, new TradingDecisionFactory(portfolio, result));
        trader.trade();

        System.out.println(result);
        System.out.println("Buy Trading Decisions " + new TradingDecisionFactory(new Portfolio(), result).generateBuyDecision(stockMarket.getMarketIndexPrices()));
        System.out.println("Sell Trading Decisions " + new TradingDecisionFactory(new Portfolio(), result).generateSellDecision(stockMarket.getMarketIndexPrices()));
        System.out.println("Final Cash $" + portfolio.getCash());
        System.out.println("Most popular 5 stocks " + portfolio.getStats().mostPopularStocks(5));
        System.out.println("Num of orders which won " + portfolio.getStats().countOrdersWon());
        System.out.println("Num of orders which lost " + portfolio.getStats().countOrdersLost());
        System.out.println("Num of orders which even " + portfolio.getStats().countOrdersEven());
        System.out.println("Percentage of orders which won " + portfolio.getStats().percentageOfOrdersWon());
        System.out.println("Order return average " + portfolio.getStats().allOrdersAverageReturn());
    }

    public SelectionStrategy<? super BitString> getSelectionStrategy() {
        // You can also try a new TournamentSelection(new Probability(0.51))
        return new RouletteWheelSelection();
    }

    private BitString buildIslandEvolutionEngine(StockTradingFitnessEvaluator stockTradingFitnessEvaluator, CandidateFactory<BitString> candidateFactory, EvolutionaryOperator<BitString> pipeline, Random rng) {
        IslandEvolution<BitString> engine
                = new IslandEvolution<BitString>(5, // Number of islands.
                new RingMigration(),
                candidateFactory,
                pipeline,
                new CachingFitnessEvaluator(stockTradingFitnessEvaluator),
                getSelectionStrategy(),
                rng);

        return engine.evolve(100, // Population size per island.
                5, // Elitism for each island.
                20, // Epoch length (no. generations).
                3, // Migrations from each island at each epoch.
                new GenerationCount(10), new Stagnation(5, true));
    }
}
