package com.egutter.trading.genetic;

import com.egutter.trading.decision.generator.*;
import com.egutter.trading.genetic.cache.CachedFitnesseEvaluatorDecorator;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
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
import org.uncommons.watchmaker.framework.operators.Replacement;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.SigmaScaling;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.selection.TruncationSelection;
import org.uncommons.watchmaker.framework.termination.ElapsedTime;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.Stagnation;

import java.util.*;

import static com.egutter.trading.candidates.GlobalStockMarketCandidates.chromosomeCandidates;
import static com.egutter.trading.candidates.GlobalStockMarketCandidates.rsiCrossDownChromosomeCandidates;
import static java.util.Arrays.asList;

/**
 * Created by egutter on 11/29/14.
 */
public class Experiment {

    private List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators;

    public Experiment(List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators) {
        this.tradingDecisionGenerators = tradingDecisionGenerators;
    }

    public BitString run(StockMarket stockMarket) {

//        FitnessEvaluator stockTradingFitnessEvaluator = new CachedFitnesseEvaluatorDecorator(new StockTradingFitnessEvaluator(stockMarket, tradingDecisions()),
//                stockMarket, tradingDecisions());
        FitnessEvaluator stockTradingFitnessEvaluator = new StockTradingFitnessEvaluator(stockMarket, this.tradingDecisionGenerators);

        CandidateFactory<BitString> candidateFactory = new BitStringFactory(genomeSize());

        List<EvolutionaryOperator<BitString>> operators
                = new LinkedList<EvolutionaryOperator<BitString>>();
        operators.add(new BitStringCrossover());
        operators.add(new BitStringMutation(new ConstantGenerator<Probability>(new Probability(0.1)),
                new ConstantGenerator<Integer>(5)));
        operators.add(new Replacement<BitString>(candidateFactory, new Probability(0.02)));

        EvolutionaryOperator<BitString> pipeline = new EvolutionPipeline<BitString>(operators);

        Random rng = new MersenneTwisterRNG();

//        return runIslandEvolutionEngine(stockTradingFitnessEvaluator, candidateFactory, pipeline, rng);
        return runEvolutionExperiment(stockTradingFitnessEvaluator, candidateFactory, pipeline, rng);
    }

    private int genomeSize() {
        return (TradingDecisionGenome.LENGTH * (this.tradingDecisionGenerators.size() + 1)) + TradingDecisionGenome.HEAD_LENGTH;
    }

    private BitString runEvolutionExperiment(FitnessEvaluator stockTradingFitnessEvaluator, CandidateFactory<BitString> candidateFactory, EvolutionaryOperator<BitString> pipeline, Random rng) {
        EvolutionEngine<BitString> engine
                = new GenerationalEvolutionEngine<BitString>(candidateFactory,
                pipeline,
                new CachingFitnessEvaluator(stockTradingFitnessEvaluator),
                getSelectionStrategy(),
                rng);

        List<BitString> candidateSeeds = rsiCrossDownChromosomeCandidates();
        return engine.evolve(1000, 10, candidateSeeds, new ElapsedTime(1200000), new GenerationCount(200), new Stagnation(20, true));
        // With candidateSeeds
//        return engine.evolve(candidateSeeds.size(), candidateSeeds.size()/10, candidateSeeds, new ElapsedTime(1200000), new GenerationCount(200), new Stagnation(20, true));
    }

    public SelectionStrategy<? super BitString> getSelectionStrategy() {
        // You can also try a new TournamentSelection(new Probability(0.51))
//        return new TournamentSelection(new Probability(0.51));
        return new RouletteWheelSelection();
//        return new SigmaScaling();
    }

    private BitString runIslandEvolutionEngine(FitnessEvaluator stockTradingFitnessEvaluator, CandidateFactory<BitString> candidateFactory, EvolutionaryOperator<BitString> pipeline, Random rng) {
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
