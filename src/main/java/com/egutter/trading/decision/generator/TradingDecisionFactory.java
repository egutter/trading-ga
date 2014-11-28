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

/**
 * Created by egutter on 2/12/14.
 */
public class TradingDecisionFactory {

    private final List<BuyTradingDecisionGenerator> buyGeneratorChain = new ArrayList<BuyTradingDecisionGenerator>();
    private final List<SellTradingDecisionGenerator> sellGeneratorChain = new ArrayList<SellTradingDecisionGenerator>();
    private final SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator;
    private Portfolio portfolio;
    private TradingDecisionGenome genome;

    public TradingDecisionFactory(Portfolio portfolio, BitString genome) {
        this.portfolio = portfolio;
        this.genome = new TradingDecisionGenome(genome);
        this.sellAfterAFixedNumberOfDaysGenerator = sellAfterAFixedNumberOfDaysGenerator();

        this.buyGeneratorChain.add(buildDoNotBuyWhenSameStockInPortfolioGenerator());
        this.buyGeneratorChain.add(buildDoNotBuyInTheLastBuyTradingDaysGenerator());
        this.buyGeneratorChain.add((BuyTradingDecisionGenerator)buildBollingerBandsGenerator());
        this.buyGeneratorChain.add((BuyTradingDecisionGenerator) buildMoneyFlowGenerator());
        this.buyGeneratorChain.add((BuyTradingDecisionGenerator) buildAroonGenerator());

        this.sellGeneratorChain.add(buildDoNotSellWhenNoStockInPorfolioGenerator());
        this.sellGeneratorChain.add((SellTradingDecisionGenerator) buildBollingerBandsGenerator());
        this.sellGeneratorChain.add((SellTradingDecisionGenerator) buildMoneyFlowGenerator());
        this.sellGeneratorChain.add((SellTradingDecisionGenerator) buildAroonGenerator());

    }

    protected TradingDecisionFactory() {
        sellAfterAFixedNumberOfDaysGenerator = sellAfterAFixedNumberOfDaysGenerator();
    }

    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        BuyWhenNoOppositionsTradingDecision tradingDecisionComposite = new BuyWhenNoOppositionsTradingDecision();
        for (BuyTradingDecisionGenerator tradingDecisionGenerator: buyGeneratorChain) {
            tradingDecisionComposite.addBuyTradingDecision(tradingDecisionGenerator.generateBuyDecision(stockPrices));
        }
        return tradingDecisionComposite;
    }

    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        SellWhenNoOppositionsTradingDecision tradingDecisionComposite = new SellWhenNoOppositionsTradingDecision();
        for (SellTradingDecisionGenerator tradingDecisionGenerator: sellGeneratorChain) {
            tradingDecisionComposite.addSellTradingDecision(tradingDecisionGenerator.generateSellDecision(stockPrices));
        }

        SellWhenNoOppositionsTradingDecision sellAfterAFixedNumberOfDaysComposite = new SellWhenNoOppositionsTradingDecision();
        sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(buildDoNotSellWhenNoStockInPorfolioGenerator().generateSellDecision(stockPrices));
        sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(sellAfterAFixedNumberOfDaysGenerator.generateSellDecision(stockPrices));

        SellWhenAnyAgreeTradingDecision orTradingDecisionComposite = new SellWhenAnyAgreeTradingDecision();
        orTradingDecisionComposite.addSellTradingDecision(tradingDecisionComposite);
        orTradingDecisionComposite.addSellTradingDecision(sellAfterAFixedNumberOfDaysComposite);
        return orTradingDecisionComposite;
    }

    private TradingDecisionGenerator buildBollingerBandsGenerator() {
        BitString bbChromosome = genome.extractBollingerBandsChromosome();
        BollingerBandsGenerator bbGenerator = new BollingerBandsGenerator(bbChromosome);
        return new InactiveTradingDecisionGenerator(bbGenerator, bbChromosome);
    }

    private TradingDecisionGenerator buildMoneyFlowGenerator() {
        BitString mfiChromosome = genome.extractMoneyFlowIndexChromosome();
        MoneyFlowIndexGenerator mfiGenerator = new MoneyFlowIndexGenerator(mfiChromosome);
        return new InactiveTradingDecisionGenerator(mfiGenerator, mfiChromosome);
    }

    private TradingDecisionGenerator buildAroonGenerator() {
        BitString aroonChromosome = genome.extractAroonChromosome();
        AroonOscilatorGenerator aroonGenerator = new AroonOscilatorGenerator(aroonChromosome);
        return new InactiveTradingDecisionGenerator(aroonGenerator, aroonChromosome);
    }

    private SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator() {
        BitString sellAfterDaysChromosome = genome.extractSellAfterAFixedNumberOfDaysChromosome();
        return new SellAfterAFixedNumberOfDaysGenerator(sellAfterDaysChromosome, portfolio);
    }

    private BuyTradingDecisionGenerator buildDoNotBuyWhenSameStockInPortfolioGenerator() {
        return new DoNotBuyWhenSameStockInPortfolioGenerator(portfolio);
    }

    private BuyTradingDecisionGenerator buildDoNotBuyInTheLastBuyTradingDaysGenerator() {
        return new DoNotBuyInTheLastBuyTradingDaysGenerator(sellAfterAFixedNumberOfDaysGenerator);
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

}
