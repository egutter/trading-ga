package com.egutter.trading.genetic;

import com.egutter.trading.stock.StockMarket;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by egutter on 11/29/14.
 */
public class Experiment {

    public BitString run(StockMarket stockMarket) {

        StockTradingFitnessEvaluator stockTradingFitnessEvaluator = new StockTradingFitnessEvaluator(stockMarket);

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
