package com.egutter.trading.runner;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.consensus.BuyWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.constraint.*;
import com.egutter.trading.decision.factory.GeneticsTradingDecisionFactory;
import com.egutter.trading.decision.factory.TradingDecisionFactory;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.decision.technicalanalysis.BollingerBands;
import com.egutter.trading.decision.technicalanalysis.RelativeStrengthIndex;
import com.egutter.trading.decision.technicalanalysis.WilliamsR;
import com.egutter.trading.genetic.StockTradingFitnessEvaluator;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.OrderBook;
import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.*;
import com.google.common.collect.Range;
import com.tictactec.ta.lib.MAType;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by egutter on 11/29/14.
 */
public class OneCandidateRunner {

    private final Portfolio portfolio;
    private final Trader trader;
    private final TradingDecisionFactory tradingDecisionFactory;
    private final OrderBook orderBook;
    private StockMarket stockMarket;
    private final BitString candidate;

    public OneCandidateRunner(StockMarket stockMarket, BitString candidate, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        this(stockMarket, candidate, new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH), tradingDecisionGenerators, true);
    }

    public OneCandidateRunner(StockMarket stockMarket, BitString candidate, Portfolio portfolio, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators, boolean onExperiment) {
        this.stockMarket = stockMarket;
        this.candidate = candidate;
        this.portfolio = portfolio;
        this.orderBook = new OrderBook();
        this.trader = new Trader(stockMarket, new GeneticsTradingDecisionFactory(portfolio, candidate, tradingDecisionGenerators, onExperiment), portfolio, orderBook);
        this.tradingDecisionFactory = new GeneticsTradingDecisionFactory(new Portfolio(), candidate, tradingDecisionGenerators, onExperiment);
    }

    public OneCandidateRunner(StockMarket stockMarket, BitString candidate, Portfolio portfolio, TradingDecisionFactory tradingDecisionFactory) {
        this.stockMarket = stockMarket;
        this.candidate = candidate;
        this.portfolio = portfolio;
        this.orderBook = new OrderBook();
        this.trader = new Trader(stockMarket, tradingDecisionFactory, portfolio, orderBook);
        this.tradingDecisionFactory = tradingDecisionFactory;
    }

    public void run() {
        trader.tradeAllStocksInMarket();
        new PrintResult().print(this, candidate);
    }

    public void runOn(LocalDate tradingDate) {
        trader.tradeOn(tradingDate);
    }

    public BuyTradingDecision buyDecisions() {
        return tradingDecisionFactory.generateBuyDecision(this.stockMarket.getMarketIndexPrices());
    }

    public SellTradingDecision sellDecisions() {
        return tradingDecisionFactory.generateSellDecision(stockMarket.getMarketIndexPrices());
    }

    public BigDecimal finalCash() {
        return this.portfolio.getCash();
    }
    public BigDecimal profit() {
        return this.portfolio.getProfit();
    }

    public List<String> mostPopularFiveStocks() {
        return this.portfolio.getStats().mostPopularStocks(5);
    }

    public int ordersWon() {
        return this.portfolio.getStats().countOrdersWon();
    }

    public int ordersLost() {
        return this.portfolio.getStats().countOrdersLost();
    }

    public int ordersEven() {
        return this.portfolio.getStats().countOrdersEven();
    }

    public BigDecimal percentageOfOrdersWon() {
        return this.portfolio.getStats().percentageOfOrdersWon();
    }

    public BuySellOperation biggestLost() {
        return this.portfolio.getStats().biggestLost();
    }

    public BuySellOperation biggestWin() {
        return this.portfolio.getStats().biggestWin();
    }

    public BigDecimal percentageOfOrdersNotLost() {
        return this.portfolio.getStats().percentageOfOrdersNotLost();
    }

    public Map<LocalDate, Pair<Integer, Integer>> ordersPerDay() {
        return this.portfolio.getStats().ordersPerDay();
    }

    public BigDecimal averageReturn() {
        return portfolio.getStats().allOrdersAverageReturn();
    }

    public static void main2(String[] args) {
        LocalDate fromDate = new LocalDate(2014, 12, 1);
        LocalDate toDate = LocalDate.now();

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);

        System.out.println("==========================================");
        System.out.println("******************************************");
        System.out.println("==========================================");
        Candidate candidate = new Candidate("XXX", "1111100110011000011010110110101010100110111", Arrays.asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class));
        System.out.println(candidate.getDescription() + ": " + candidate.key());
        Portfolio portfolio = new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH);
        TradingDecisionFactory tradingDecisionFactory = new TradingDecisionFactory() {
            @Override
            public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
                BuyWhenNoOppositionsTradingDecision tradingDecisionComposite = new BuyWhenNoOppositionsTradingDecision();
                tradingDecisionComposite.addBuyTradingDecision(new DoNotBuyWhenSameStockInPortfolio(portfolio, stockPrices));
                tradingDecisionComposite.addBuyTradingDecision(new DoNotBuyInTheLastBuyTradingDays(stockPrices, 10));
                tradingDecisionComposite.addBuyTradingDecision(new RelativeStrengthIndex(stockPrices, Range.atLeast(75.0), Range.atMost(50.0), 14));
                tradingDecisionComposite.addBuyTradingDecision(new BollingerBands(stockPrices, Range.atLeast(1.0), Range.atMost(0.5), 20, MAType.Sma));
                tradingDecisionComposite.addBuyTradingDecision(new WilliamsR(stockPrices, Range.atMost(-70.0), Range.atLeast(-50.0), 14));
                return tradingDecisionComposite;
            }

            @Override
            public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
                SellWhenNoOppositionsTradingDecision sellAfterAFixedNumberOfDaysComposite = new SellWhenNoOppositionsTradingDecision();
                sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices));
                sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(new SellAfterAFixedNumberOFDays(portfolio, stockPrices, 10));

                sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(new SellByProfitThreshold(portfolio, stockPrices, BigDecimal.TEN));

                return sellAfterAFixedNumberOfDaysComposite;

            }
        };
        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, candidate.getChromosome(), portfolio, tradingDecisionFactory);
        runner.run();
    }
    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2012, 12, 1);
        LocalDate toDate = LocalDate.now();

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);

        new TradeOneDayRunner(fromDate, toDate).candidates().forEach(candidate -> {
            System.out.println("==========================================");
            System.out.println("******************************************");
            System.out.println("==========================================");
            System.out.println(candidate.getDescription() + ": " + candidate.key());
            OneCandidateRunner runner = new OneCandidateRunner(stockMarket, candidate.getChromosome(), candidate.getTradingDecisionGenerators());
            runner.run();
        });
//        BitString candidate = new BitString("10011000000011101");
    }

    public OrderBook getOrderBook() {
        return this.orderBook;
    }
}
