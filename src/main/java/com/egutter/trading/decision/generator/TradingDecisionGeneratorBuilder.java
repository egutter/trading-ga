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
public class TradingDecisionGeneratorBuilder implements TradingDecisionGenerator {
    private final List<TradingDecisionGenerator> generatorChain;
    private Portfolio portfolio;
    private TradingDecisionGenome genome;

    public TradingDecisionGeneratorBuilder(Portfolio portfolio, BitString genome) {
        this.portfolio = portfolio;
        this.genome = new TradingDecisionGenome(genome);

        generatorChain = new ArrayList<TradingDecisionGenerator>();
        generatorChain.add(buildBollingerBandsGenerator());
        generatorChain.add(sellAfterAFixedNumberOfDaysGenerator());
    }

    public TradingDecision generate(StockPrices stockPrices) {
        TradingDecisionComposite tradingDecisionComposite = new TradingDecisionComposite(portfolio, stockPrices);
        for (TradingDecisionGenerator tradingDecisionGenerator: generatorChain) {

            tradingDecisionComposite.add(buildWrappedTradingDecision(stockPrices, tradingDecisionGenerator.generate(stockPrices)));
        }

        return tradingDecisionComposite;
        // generate trading decisions from genome

//        BollingerBands bollingerBands = new BollingerBands(stockPrices,
//                Range.atMost(0.1),
//                Range.atLeast(0.9),
//                20,
//                MAType.Sma);
//
//        DoNotBuyWhenSameStockInPortfolio doNotBuyWhenSameStockInPortfolio = new DoNotBuyWhenSameStockInPortfolio(portfolio,
//                stockPrices,
//                bollingerBands);
//
//        return new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices, doNotBuyWhenSameStockInPortfolio);
    }

    private DoNotSellWhenNoStockInPorfolio buildWrappedTradingDecision(StockPrices stockPrices, TradingDecision tradingDecision) {
        DoNotBuyWhenSameStockInPortfolio doNotBuyWhenSameStockInPortfolio = new DoNotBuyWhenSameStockInPortfolio(portfolio,
                stockPrices,
                tradingDecision);

        return new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices, doNotBuyWhenSameStockInPortfolio);
    }

    private TradingDecisionGenerator buildBollingerBandsGenerator() {
        BitString bbChromosome = genome.extractBollingerBandsChromosome();
        BollingerBandsGenerator bbGenerator = new BollingerBandsGenerator(bbChromosome);
        return new InactiveTradingDecisionGenerator(bbGenerator, bbChromosome);
    }

    private TradingDecisionGenerator sellAfterAFixedNumberOfDaysGenerator() {
        BitString sellAfterDaysChromosome = genome.extractSellAfterAFixedNumberOfDaysChromosome();
        TradingDecisionGenerator sellAfterDaysGenerator = new SellAfterAFixedNumberOfDaysGenerator(sellAfterDaysChromosome, portfolio);
        return new InactiveTradingDecisionGenerator(sellAfterDaysGenerator, sellAfterDaysChromosome);
    }

}
