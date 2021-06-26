package com.egutter.trading.decision;

import com.egutter.trading.decision.generator.MovingAverageCrossOverGenerator;
import com.egutter.trading.decision.generator.RelativeStrengthIndexCrossDownGenerator;
import com.egutter.trading.decision.generator.TrailingStopSellDecisionGenerator;
import com.egutter.trading.decision.technicalanalysis.TriggerBuyConditionalOrderDecision;
import com.egutter.trading.genetic.StockTradingFitnessEvaluator;
import com.egutter.trading.genetic.TradingDecisionGenome;
import com.egutter.trading.order.ConditionalOrderBuyDecision;
import com.egutter.trading.order.condition.BuyDecisionConditionsFactory;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import org.uncommons.maths.binary.BitString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class CrossOverTriggerBuyConditionalOrderDecisionStrategyFactory implements TriggerBuyConditionalOrderDecisionStrategyFactory {

    private static final int TRIGGER_COND_ORDER_BUY_DECISION = 0;
    private static final int BUY_STOCH_INDEX = 1;
    private static final int BUY_CHAIKIN_INDEX = 2;
    private static final int BUY_STOCH_THRESHOLD_INDEX = 2;
    private static final int SELL_MONEY_FLOW_INDEX = 2;
    private static final int TRAILING_STOP_INDEX = 3;

    private final Portfolio portfolio;
    private final TradingDecisionGenome genome;
    private Class<? extends ConditionalOrderConditionGenerator> crossOverTriggerDecisionGenerator;
    private List<? extends Class<? extends ConditionalOrderConditionGenerator>> conditionalBuyDecisionGenerators;

    public static void main(String[] args) {

        Portfolio portfolio = new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH);

        BitString genome = new BitString("00010010000111011101111001010101010111100");
        TriggerBuyConditionalOrderDecision tbcod = new CrossOverTriggerBuyConditionalOrderDecisionStrategyFactory(portfolio, genome,
                RelativeStrengthIndexCrossDownGenerator.class,
                Arrays.asList(MovingAverageCrossOverGenerator.class)).generateBuyDecision(StockPrices.empty());

        System.out.println(tbcod.buyDecisionToString());
    }
    public CrossOverTriggerBuyConditionalOrderDecisionStrategyFactory(Portfolio portfolio,
                                                                      BitString genome,
                                                                      Class<? extends ConditionalOrderConditionGenerator> crossOverTriggerDecisionGenerator,
                                                                      List<? extends Class<? extends ConditionalOrderConditionGenerator>> conditionalBuyDecisionGenerators) {
        this.portfolio = portfolio;
        this.genome = new TradingDecisionGenome(genome);
        this.crossOverTriggerDecisionGenerator = crossOverTriggerDecisionGenerator;
        this.conditionalBuyDecisionGenerators = conditionalBuyDecisionGenerators;
    }

    public TriggerBuyConditionalOrderDecision generateBuyDecision(StockPrices stockPrices) {
        return generateConditionalOrderBuyDecision(stockPrices);
    }

    private ConditionalOrderBuyDecision generateConditionalOrderBuyDecision(StockPrices stockPrices) {
        BitString buyTriggerChromosome = genome.extractChromosome(TRIGGER_COND_ORDER_BUY_DECISION);
        Function<TimeFrameQuote, Boolean> conditionalBuyTrigger = getGenerator(crossOverTriggerDecisionGenerator, buyTriggerChromosome).generateCondition(stockPrices);

        List<ConditionalOrderConditionGenerator> conditionGenerators = buyDecisionGenerators();
        BuyDecisionConditionsFactory buyDecisionConditionsFactory = new BuyDecisionConditionsFactory(conditionGenerators, portfolio, stockPrices);

        int sellDecisionIndex = conditionalBuyDecisionGenerators.size() + 1;
        BitString sellDecisionChromosome = genome.extractChromosome(sellDecisionIndex);
        TrailingStopSellDecisionGenerator stopTrailingLossGenerator = new TrailingStopSellDecisionGenerator(sellDecisionChromosome, genome.extractTail(sellDecisionIndex + 1));

        return new ConditionalOrderBuyDecision(stockPrices, conditionalBuyTrigger, buyDecisionConditionsFactory, stopTrailingLossGenerator.getExpireInDays(), stopTrailingLossGenerator);
    }

    private List<ConditionalOrderConditionGenerator> buyDecisionGenerators() {
        int index = 1;
        List<ConditionalOrderConditionGenerator> generators = new ArrayList<>();
        for (Class<? extends ConditionalOrderConditionGenerator> tradingDecisionGeneratorClass : conditionalBuyDecisionGenerators) {
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
