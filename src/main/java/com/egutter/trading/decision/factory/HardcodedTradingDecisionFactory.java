package com.egutter.trading.decision.factory;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.consensus.BuyWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenAnyAgreeTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.constraint.*;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.decision.technicalanalysis.ChaikinOscillator;
import com.egutter.trading.genetic.TradingDecisionGenome;
import com.egutter.trading.order.condition.BuyDecisionConditionsFactory;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

public class HardcodedTradingDecisionFactory implements TradingDecisionFactory {

    private static final int BUY_FIB_RETR_INDEX = 0;
    private static final int BUY_STOCH_INDEX = 1;
    private static final int BUY_STOCH_THRESHOLD_INDEX = 2;
    private static final int SELL_MONEY_FLOW_INDEX = 2;
    private static final int TRAILING_STOP_INDEX = 3;
    private final Portfolio portfolio;
    private final TradingDecisionGenome genome;

    public HardcodedTradingDecisionFactory(Portfolio portfolio,
                                           BitString genome) {
        this.portfolio = portfolio;
        this.genome = new TradingDecisionGenome(genome);
    }

    @Override
    public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {

        BuyWhenNoOppositionsTradingDecision tradingDecisionComposite = new BuyWhenNoOppositionsTradingDecision();
        tradingDecisionComposite.addBuyTradingDecision(new DoNotBuyWhenSameStockInPortfolio(portfolio, stockPrices));

        BuyWhenNoOppositionsTradingDecision innerComposite = new BuyWhenNoOppositionsTradingDecision();

        innerComposite.addBuyTradingDecision(
                new KeepDecisionForFewDays(stockPrices,
                        new FibonacciRetracementGenerator(genome.extractChromosome(BUY_FIB_RETR_INDEX), BuyDecisionConditionsFactory.empty()).generateBuyDecision(stockPrices),
                        3)
        );
        BuyTradingDecision stochasticBuyDecision = new StochasticOscillatorGenerator(genome.extractChromosome(BUY_STOCH_INDEX)).generateBuyDecision(stockPrices);
        innerComposite.addBuyTradingDecision(
                new KeepDecisionForFewDays(stockPrices,
                        stochasticBuyDecision,
                        3)
        );
        BuyTradingDecision chaikingBuyDecision = new ChaikinOscillator(stockPrices, 3, 10);
        innerComposite.addBuyTradingDecision(
                new KeepDecisionForFewDays(stockPrices,
                        chaikingBuyDecision,
                        3)
        );
//        Comment me to disable Stoch Threshold
//        innerComposite.addBuyTradingDecision(
//                new KeepDecisionForFewDays(stockPrices,
//                        new StochasticOscillatorThresholdGenerator((StochasticOscillator) stochasticBuyDecision, genome.extractChromosome(BUY_STOCH_THRESHOLD_INDEX)).generateBuyDecision(stockPrices),
//                        3)
//        );


        tradingDecisionComposite.addBuyTradingDecision(new DelayDecisionToPriceChange(stockPrices, innerComposite, 5));
//        tradingDecisionComposite.addBuyTradingDecision(innerComposite);

//        BuyWhenNoOppositionsTradingDecision tradingDecisionComposite = new BuyWhenNoOppositionsTradingDecision();
//        tradingDecisionComposite.addBuyTradingDecision(new DoNotBuyWhenSameStockInPortfolio(portfolio, stockPrices));
//        tradingDecisionComposite.addBuyTradingDecision(new MovingAverageCrossOverGenerator(genome.extractChromosome(BUY_MOV_AVG_CROSS_INDEX)).generateBuyDecision(stockPrices));

        return tradingDecisionComposite;
    }

    @Override
    public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
        SellWhenNoOppositionsTradingDecision sellWhenNoOppositionsTradingDecision = new SellWhenNoOppositionsTradingDecision();
        sellWhenNoOppositionsTradingDecision.addSellTradingDecision(new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices));

        SellWhenAnyAgreeTradingDecision sellTrailingStopComposite = new SellWhenAnyAgreeTradingDecision();
        sellTrailingStopComposite.addSellTradingDecision(new TakeProfitPartialSell(portfolio, stockPrices));

//        Uncomment me to activate MFI
//        sellTrailingStopComposite.addSellTradingDecision(new MoneyFlowIndexGenerator(genome.extractChromosome(SELL_MONEY_FLOW_INDEX)).generateSellDecision(stockPrices));

        //                new MoneyFlowIndex(stockPrices, Range.atMost(20.0), Range.atLeast(80.0), 14));

//        sellTrailingStopComposite.addSellTradingDecision(new MovingAverageCrossOverGenerator(genome.extractChromosome(SELL_MOV_AVG_CROSS_INDEX)).generateSellDecision(stockPrices));
        sellTrailingStopComposite.addSellTradingDecision(new TrailingStopGenerator(portfolio, genome.extractChromosome(TRAILING_STOP_INDEX)).generateSellDecision(stockPrices));
        sellTrailingStopComposite.addSellTradingDecision(new SellLastDayOfMarket(portfolio, stockPrices));

        sellWhenNoOppositionsTradingDecision.addSellTradingDecision(sellTrailingStopComposite);
        return sellWhenNoOppositionsTradingDecision;
    }
}
