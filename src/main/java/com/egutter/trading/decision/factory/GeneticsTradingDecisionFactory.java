package com.egutter.trading.decision.factory;

import com.egutter.trading.decision.*;
import com.egutter.trading.decision.consensus.BuyWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenAnyAgreeTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.genetic.TradingDecisionGenome;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egutter on 2/12/14.
 */
public class GeneticsTradingDecisionFactory implements TradingDecisionFactory {

    private final List<BuyTradingDecisionGenerator> buyGeneratorChain = new ArrayList<BuyTradingDecisionGenerator>();
    private final List<SellTradingDecisionGenerator> sellGeneratorChain = new ArrayList<SellTradingDecisionGenerator>();
    private boolean sellByProfitThreshold;
    private Portfolio portfolio;
    private TradingDecisionGenome genome;

    public GeneticsTradingDecisionFactory(Portfolio portfolio,
                                          BitString genome,
                                          List<? extends Class<? extends TradingDecisionGenerator>> tradingDecisionGenerators,
                                          boolean onExperiment) {
        this(portfolio, genome, tradingDecisionGenerators, onExperiment, Boolean.valueOf(System.getenv().get("SELL_BY_PROFIT_THRESHOLD")));
    }

    public GeneticsTradingDecisionFactory(Portfolio portfolio,
                                          BitString genome,
                                          List<? extends Class<? extends TradingDecisionGenerator>> tradingDecisionGenerators,
                                          boolean onExperiment,
                                          boolean sellByProfitThreshold) {
        this.portfolio = portfolio;
        this.genome = new TradingDecisionGenome(genome);
        this.sellByProfitThreshold = sellByProfitThreshold;

        this.buyGeneratorChain.add(buildDoNotBuyWhenSameStockInPortfolioGenerator());
        if (onlyBuyStockPriceFalls()) this.buyGeneratorChain.add(buildOnlyBuyStockPriceFallsGenerator());
        if (onExperiment) {
            this.buyGeneratorChain.add(buildDoNotBuyInTheLastBuyTradingDaysGenerator());
        }

        this.sellGeneratorChain.add(buildDoNotSellWhenNoStockInPorfolioGenerator());

        int index = 0;
        for (Class<? extends TradingDecisionGenerator> tradingDecisionGeneratorClass : tradingDecisionGenerators) {
            TradingDecisionGenerator tradingDecisionGenerator = getGenerator(tradingDecisionGeneratorClass, this.genome.extractChromosome(index));
            this.buyGeneratorChain.add((BuyTradingDecisionGenerator) tradingDecisionGenerator);
            this.sellGeneratorChain.add((SellTradingDecisionGenerator) tradingDecisionGenerator);
            index++;
        }
    }

    protected GeneticsTradingDecisionFactory() {
    }

    @Override
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        BuyWhenNoOppositionsTradingDecision tradingDecisionComposite = new BuyWhenNoOppositionsTradingDecision();
        for (BuyTradingDecisionGenerator tradingDecisionGenerator : buyGeneratorChain) {
            tradingDecisionComposite.addBuyTradingDecision(tradingDecisionGenerator.generateBuyDecision(stockPrices));
        }
        return tradingDecisionComposite;
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        SellWhenNoOppositionsTradingDecision tradingDecisionComposite = new SellWhenNoOppositionsTradingDecision();
        for (SellTradingDecisionGenerator tradingDecisionGenerator : sellGeneratorChain) {
            tradingDecisionComposite.addSellTradingDecision(tradingDecisionGenerator.generateSellDecision(stockPrices));
        }
        if (doNotSellAfterAFixedNumberOfDays()) return tradingDecisionComposite;

        SellWhenNoOppositionsTradingDecision sellAfterAFixedNumberOfDaysComposite = new SellWhenNoOppositionsTradingDecision();
        sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(buildDoNotSellWhenNoStockInPorfolioGenerator().generateSellDecision(stockPrices));
        sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(sellAfterAFixedNumberOfDaysGenerator().generateSellDecision(stockPrices));

        if (sellByProfitThreshold)
            sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(sellByProfitThresholdGenerator().generateSellDecision(stockPrices));

        SellWhenAnyAgreeTradingDecision orTradingDecisionComposite = new SellWhenAnyAgreeTradingDecision();
        orTradingDecisionComposite.addSellTradingDecision(tradingDecisionComposite);
        orTradingDecisionComposite.addSellTradingDecision(sellAfterAFixedNumberOfDaysComposite);
        return orTradingDecisionComposite;
    }

    private boolean doNotSellAfterAFixedNumberOfDays() {
        return Boolean.valueOf(System.getenv().get("DO_NOT_SELL_AFTER_A_FIXED_NUMBER_OF_DAYS"));
    }

    private boolean onlyBuyStockPriceFalls() {
        return Boolean.valueOf(System.getenv().get("ONLY_BUY_STOCK_WHEN_PRICE_FALLS"));
    }

    private SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator() {
        BitString sellAfterDaysChromosome = genome.extractSellAfterAFixedNumberOfDaysChromosome();
        return new SellAfterAFixedNumberOfDaysGenerator(sellAfterDaysChromosome, portfolio);
    }

    private SellByProfitThresholdGenerator sellByProfitThresholdGenerator() {
        BitString sellAfterDaysChromosome = genome.extractSellAfterAFixedNumberOfDaysChromosome();
        return new SellByProfitThresholdGenerator(sellAfterDaysChromosome, portfolio);
    }

    private BuyTradingDecisionGenerator buildDoNotBuyWhenSameStockInPortfolioGenerator() {
        return new DoNotBuyWhenSameStockInPortfolioGenerator(portfolio);
    }

    private BuyTradingDecisionGenerator buildOnlyBuyStockPriceFallsGenerator() {
        return new OnlyBuyWhenStockPriceFallsGenerator();
    }

    private BuyTradingDecisionGenerator buildDoNotBuyInTheLastBuyTradingDaysGenerator() {
        return new DoNotBuyInTheLastBuyTradingDaysGenerator(sellAfterAFixedNumberOfDaysGenerator());
    }

    private SellTradingDecisionGenerator buildDoNotSellWhenNoStockInPorfolioGenerator() {
        return new DoNotSellWhenNoStockInPorfolioGenerator(portfolio);
    }

    public List<BuyTradingDecisionGenerator> getBuyGeneratorChain() {
        return buyGeneratorChain;
    }

    public TradingDecisionGenerator getGenerator(Class<? extends TradingDecisionGenerator> generatorClass, BitString chromosome) {
        try {
            return generatorClass.getConstructor(BitString.class).newInstance(chromosome);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
