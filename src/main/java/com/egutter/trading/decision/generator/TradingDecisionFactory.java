package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.*;
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

    private final List<TradingDecisionGenerator> buyGeneratorChain = new ArrayList<TradingDecisionGenerator>();
    private final List<TradingDecisionGenerator> sellGeneratorChain = new ArrayList<TradingDecisionGenerator>();
    private Portfolio portfolio;
    private TradingDecisionGenome genome;

    public TradingDecisionFactory(Portfolio portfolio, BitString genome) {
        this.portfolio = portfolio;
        this.genome = new TradingDecisionGenome(genome);
        this.buyGeneratorChain.add(buildBollingerBandsGenerator());
        this.buyGeneratorChain.add(buildMoneyFlowGenerator());
        this.sellGeneratorChain.add(buildBollingerBandsGenerator());
        this.sellGeneratorChain.add(buildMoneyFlowGenerator());
        this.sellGeneratorChain.add(sellAfterAFixedNumberOfDaysGenerator());
    }

    protected TradingDecisionFactory() {
    }

    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
        BuyTradingDecisionComposite tradingDecisionComposite = new BuyTradingDecisionComposite();
        for (TradingDecisionGenerator tradingDecisionGenerator: buyGeneratorChain) {
            tradingDecisionComposite.addBuyTradingDecision(buildBuyWrappedTradingDecision(stockPrices,
                    tradingDecisionGenerator.generateBuyDecision(stockPrices)));
        }
        return tradingDecisionComposite;
    }

    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        SellTradingDecisionComposite tradingDecisionComposite = new SellTradingDecisionComposite();
        for (TradingDecisionGenerator tradingDecisionGenerator: sellGeneratorChain) {
            tradingDecisionComposite.addSellTradingDecision(buildSellWrappedTradingDecision(stockPrices,
                    tradingDecisionGenerator.generateSellDecision(stockPrices)));
        }
        return tradingDecisionComposite;
    }

    private BuyTradingDecision buildBuyWrappedTradingDecision(StockPrices stockPrices, BuyTradingDecision tradingDecision) {
        DoNotBuyWhenSameStockInPortfolio doNotBuyWhenSameStockInPortfolio = new DoNotBuyWhenSameStockInPortfolio(portfolio,
                stockPrices,
                tradingDecision);

        return new DoNotBuyInTheLastBuyTradingDays(stockPrices,
                sellAfterAFixedNumberOfDaysGenerator(),
                doNotBuyWhenSameStockInPortfolio);
    }

    private SellTradingDecision buildSellWrappedTradingDecision(StockPrices stockPrices, SellTradingDecision tradingDecision) {
        return new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices, tradingDecision);
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


    private SellAfterAFixedNumberOfDaysGenerator sellAfterAFixedNumberOfDaysGenerator() {
        BitString sellAfterDaysChromosome = genome.extractSellAfterAFixedNumberOfDaysChromosome();
        return new SellAfterAFixedNumberOfDaysGenerator(sellAfterDaysChromosome, portfolio);
    }

    public List<TradingDecisionGenerator> getBuyGeneratorChain() {
        return buyGeneratorChain;
    }

    public List<TradingDecisionGenerator> getSellGeneratorChain() {
        return sellGeneratorChain;
    }

}
