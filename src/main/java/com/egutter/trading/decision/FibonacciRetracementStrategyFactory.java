package com.egutter.trading.decision;

import com.egutter.trading.decision.generator.*;
import com.egutter.trading.decision.technicalanalysis.FibonacciRetracementBuyDecision;
import com.egutter.trading.genetic.TradingDecisionGenome;
import com.egutter.trading.order.condition.BuyDecisionConditionsFactory;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

import java.util.Arrays;
import java.util.List;

public class FibonacciRetracementStrategyFactory {

    private static final int BUY_FIB_RETR_INDEX = 0;
    private static final int BUY_STOCH_INDEX = 1;
    private static final int BUY_CHAIKIN_INDEX = 2;
    private static final int BUY_STOCH_THRESHOLD_INDEX = 2;
    private static final int SELL_MONEY_FLOW_INDEX = 2;
    private static final int TRAILING_STOP_INDEX = 3;

    private final Portfolio portfolio;
    private final TradingDecisionGenome genome;
    private List<? extends Class<? extends TradingDecisionGenerator>> tradingDecisionGenerators;

    public FibonacciRetracementStrategyFactory(Portfolio portfolio,
                                               BitString genome, List<? extends Class<? extends TradingDecisionGenerator>> tradingDecisionGenerators) {
        this.portfolio = portfolio;
        this.genome = new TradingDecisionGenome(genome);
        this.tradingDecisionGenerators = tradingDecisionGenerators;
    }

    public FibonacciRetracementBuyDecision generateBuyDecision(StockPrices stockPrices) {
        BitString chromosome = genome.extractChromosome(BUY_FIB_RETR_INDEX);

        BuyDecisionConditionsFactory buyDecisionConditionsFactory = new BuyDecisionConditionsFactory(buyDecisionGenerators(), portfolio, stockPrices);
        FibonacciRetracementGenerator fibonacciRetracementGenerator = new FibonacciRetracementGenerator(chromosome, buyDecisionConditionsFactory);
        return (FibonacciRetracementBuyDecision) fibonacciRetracementGenerator.generateBuyDecision(stockPrices);
    }

    private List<ConditionalOrderConditionGenerator> buyDecisionGenerators() {
//        return Arrays.asList(new StochasticOscillatorGenerator(genome.extractChromosome(BUY_STOCH_INDEX)));
//        return Arrays.asList(new ChaikinOscillatorGenerator(genome.extractChromosome(BUY_STOCH_INDEX)));
        return Arrays.asList(
                new StochasticOscillatorGenerator(genome.extractChromosome(BUY_STOCH_INDEX)),
                new ChaikinOscillatorGenerator(genome.extractChromosome(BUY_CHAIKIN_INDEX)));
    }
}
