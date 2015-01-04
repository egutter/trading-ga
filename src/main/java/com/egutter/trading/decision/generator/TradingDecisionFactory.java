package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.*;
import com.egutter.trading.decision.consensus.BuyWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenAnyAgreeTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenNoOppositionsTradingDecision;
import com.egutter.trading.genetic.TradingDecisionGenome;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 2/12/14.
 */
public class TradingDecisionFactory {

    private final List<BuyTradingDecisionGenerator> buyGeneratorChain = new ArrayList<BuyTradingDecisionGenerator>();
    private final List<SellTradingDecisionGenerator> sellGeneratorChain = new ArrayList<SellTradingDecisionGenerator>();
    private Portfolio portfolio;
    private TradingDecisionGenome genome;

    public TradingDecisionFactory(Portfolio portfolio,
                                  BitString genome,
                                  List<? extends Class<? extends TradingDecisionGenerator>> tradingDecisionGenerators, boolean onExperiment) {
        this.portfolio = portfolio;
        this.genome = new TradingDecisionGenome(genome);

        this.buyGeneratorChain.add(buildDoNotBuyWhenSameStockInPortfolioGenerator());
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

    protected TradingDecisionFactory() {
    }

    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        BuyWhenNoOppositionsTradingDecision tradingDecisionComposite = new BuyWhenNoOppositionsTradingDecision();
        for (BuyTradingDecisionGenerator tradingDecisionGenerator : buyGeneratorChain) {
            tradingDecisionComposite.addBuyTradingDecision(tradingDecisionGenerator.generateBuyDecision(stockPrices));
        }
        return tradingDecisionComposite;
    }

    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        SellWhenNoOppositionsTradingDecision tradingDecisionComposite = new SellWhenNoOppositionsTradingDecision();
        for (SellTradingDecisionGenerator tradingDecisionGenerator : sellGeneratorChain) {
            tradingDecisionComposite.addSellTradingDecision(tradingDecisionGenerator.generateSellDecision(stockPrices));
        }

        SellWhenNoOppositionsTradingDecision sellAfterAFixedNumberOfDaysComposite = new SellWhenNoOppositionsTradingDecision();
        sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(buildDoNotSellWhenNoStockInPorfolioGenerator().generateSellDecision(stockPrices));
        sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(sellAfterAFixedNumberOfDaysGenerator().generateSellDecision(stockPrices));

        SellWhenAnyAgreeTradingDecision orTradingDecisionComposite = new SellWhenAnyAgreeTradingDecision();
        orTradingDecisionComposite.addSellTradingDecision(tradingDecisionComposite);
        orTradingDecisionComposite.addSellTradingDecision(sellAfterAFixedNumberOfDaysComposite);
        return orTradingDecisionComposite;
    }

    private SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator() {
        BitString sellAfterDaysChromosome = genome.extractSellAfterAFixedNumberOfDaysChromosome();
        return new SellAfterAFixedNumberOfDaysGenerator(sellAfterDaysChromosome, portfolio);
    }

    private BuyTradingDecisionGenerator buildDoNotBuyWhenSameStockInPortfolioGenerator() {
        return new DoNotBuyWhenSameStockInPortfolioGenerator(portfolio);
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
