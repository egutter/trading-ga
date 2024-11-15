package com.egutter.trading.decision;

import com.egutter.trading.decision.generator.FibonacciRetracementGenerator;
import com.egutter.trading.decision.technicalanalysis.RelativeStrengthIndexCrossDown;
import com.egutter.trading.decision.technicalanalysis.TriggerBuyConditionalOrderDecision;
import com.egutter.trading.genetic.TradingDecisionGenome;
import com.egutter.trading.order.ConditionalOrderBuyDecision;
import com.egutter.trading.order.condition.BuyDecisionConditionsFactory;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

import java.util.ArrayList;
import java.util.List;

public class FibonacciRetracementTriggerBuyConditionalOrderDecisionStrategyFactory implements TriggerBuyConditionalOrderDecisionStrategyFactory {

    private static final int TRIGGER_COND_ORDER_BUY_DECISION = 0;
    private static final int BUY_STOCH_INDEX = 1;
    private static final int BUY_CHAIKIN_INDEX = 2;
    private static final int BUY_STOCH_THRESHOLD_INDEX = 2;
    private static final int SELL_MONEY_FLOW_INDEX = 2;
    private static final int TRAILING_STOP_INDEX = 3;

    private final Portfolio portfolio;
    private final TradingDecisionGenome genome;
    private List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators;

    public FibonacciRetracementTriggerBuyConditionalOrderDecisionStrategyFactory(Portfolio portfolio,
                                                                                 BitString genome,
                                                                                 List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators) {
        this.portfolio = portfolio;
        this.genome = new TradingDecisionGenome(genome);
        this.tradingDecisionGenerators = tradingDecisionGenerators;
    }

    public TriggerBuyConditionalOrderDecision generateBuyDecision(StockPrices stockPrices) {
        return generateFibonacciRetracementBuyDecision(stockPrices);
    }

    private TriggerBuyConditionalOrderDecision generateFibonacciRetracementBuyDecision(StockPrices stockPrices) {
        BitString chromosome = genome.extractChromosome(TRIGGER_COND_ORDER_BUY_DECISION);

        BuyDecisionConditionsFactory buyDecisionConditionsFactory = new BuyDecisionConditionsFactory(buyDecisionGenerators(), portfolio, stockPrices);
        FibonacciRetracementGenerator fibonacciRetracementGenerator = new FibonacciRetracementGenerator(chromosome, buyDecisionConditionsFactory);
        return (TriggerBuyConditionalOrderDecision) fibonacciRetracementGenerator.generateBuyDecision(stockPrices);
    }

    private List<ConditionalOrderConditionGenerator> buyDecisionGenerators() {
        int index = 1;
        List<ConditionalOrderConditionGenerator> generators = new ArrayList<>();
        for (Class<? extends ConditionalOrderConditionGenerator> tradingDecisionGeneratorClass : tradingDecisionGenerators) {
            ConditionalOrderConditionGenerator tradingDecisionGenerator = getGenerator(tradingDecisionGeneratorClass, this.genome.extractChromosome(index));
            generators.add(tradingDecisionGenerator);
            index++;
        }

        return generators;
    }

    public ConditionalOrderConditionGenerator getGenerator(Class<? extends ConditionalOrderConditionGenerator> generatorClass, BitString chromosome) {
        try {
            return generatorClass.getConstructor(BitString.class).newInstance(chromosome);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
