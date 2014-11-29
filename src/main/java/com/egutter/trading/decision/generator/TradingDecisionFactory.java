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
//    private final SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator;
    private Portfolio portfolio;
    private TradingDecisionGenome genome;

    private List<? extends Class<? extends BuyTradingDecisionGenerator>> availableTradingDecisionGenerators =
            asList(InactiveTradingDecisionGenerator.class,
                    BollingerBandsGenerator.class,
                    MoneyFlowIndexGenerator.class,
                    AroonOscilatorGenerator.class);

    public TradingDecisionFactory(Portfolio portfolio, BitString genome) {
        this.portfolio = portfolio;
        this.genome = new TradingDecisionGenome(genome);

        this.buyGeneratorChain.add(buildDoNotBuyWhenSameStockInPortfolioGenerator());
        this.buyGeneratorChain.add(buildDoNotBuyInTheLastBuyTradingDaysGenerator());

        this.buyGeneratorChain.add((BuyTradingDecisionGenerator) buildBollingerBandsGenerator(this.genome.extractFirstDecisionChromosome()));
        this.buyGeneratorChain.add((BuyTradingDecisionGenerator) buildMoneyFlowGenerator(this.genome.extractSecondDecisionChromosome()));


        this.sellGeneratorChain.add(buildDoNotSellWhenNoStockInPorfolioGenerator());

        this.sellGeneratorChain.add((SellTradingDecisionGenerator) buildBollingerBandsGenerator(this.genome.extractFirstDecisionChromosome()));
        this.sellGeneratorChain.add((SellTradingDecisionGenerator) buildMoneyFlowGenerator(this.genome.extractSecondDecisionChromosome()));
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

    private TradingDecisionGenerator buildBollingerBandsGenerator(BitString chromosome) {
        return new BollingerBandsGenerator(chromosome);
    }

    private TradingDecisionGenerator buildMoneyFlowGenerator(BitString chromosome) {
        return new MoneyFlowIndexGenerator(chromosome);
    }

    private TradingDecisionGenerator buildAroonGenerator(BitString chromosome) {
        return new AroonOscilatorGenerator(chromosome);
    }

    private InactiveTradingDecisionGenerator buildInactiveGenerator() {
        return new InactiveTradingDecisionGenerator(null);
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

    public List<SellTradingDecisionGenerator> getSellGeneratorChain() {
        return sellGeneratorChain;
    }

    public TradingDecisionGenerator getGenerator(BitString chromosome) {
        int index = new Random().nextInt(availableTradingDecisionGenerators.size());
        try {
            return availableTradingDecisionGenerators.get(index).getConstructor(BitString.class).newInstance(chromosome);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
