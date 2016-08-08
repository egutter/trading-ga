package com.egutter.trading.genetic;

import com.egutter.trading.decision.generator.BuyTradingDecisionGenerator;
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
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.Stagnation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by egutter on 11/29/14.
 */
public class NonGeneticExperiment {

    private List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators;
    private StockMarket stockMarket;

    List<BitString> winningCandidates = new ArrayList<>();

    public NonGeneticExperiment(List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators, StockMarket stockMarket) {
        this.tradingDecisionGenerators = tradingDecisionGenerators;
        this.stockMarket = stockMarket;
    }

    public void run(BitString candidate) {

        StockTradingFitnessEvaluator stockTradingFitnessEvaluator = new StockTradingFitnessEvaluator(stockMarket, this.tradingDecisionGenerators);
        double result = stockTradingFitnessEvaluator.getFitness(candidate);
        if (result > 0.0) {
            winningCandidates.add(candidate);
        }
    }

    public List<BitString> getWinningCandidates() {
        return winningCandidates;
    }

    private int genomeSize() {
        return (TradingDecisionGenome.LENGTH * this.tradingDecisionGenerators.size()) + TradingDecisionGenome.HEAD_LENGTH;
    }

}
