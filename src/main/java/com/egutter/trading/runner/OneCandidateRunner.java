package com.egutter.trading.runner;

import com.egutter.trading.candidates.GlobalStockMarketCandidates;
import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.consensus.SellWhenAnyAgreeTradingDecision;
import com.egutter.trading.decision.constraint.SellLastDayOfMarket;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.consensus.BuyWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.constraint.*;
import com.egutter.trading.decision.factory.GeneticsTradingDecisionFactory;
import com.egutter.trading.decision.factory.HardcodedTradingDecisionFactory;
import com.egutter.trading.decision.factory.TradingDecisionFactory;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.decision.technicalanalysis.*;
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

    public OneCandidateRunner(StockMarket stockMarket, BitString result) {
        this.stockMarket = stockMarket;
        this.candidate = result;
        this.portfolio = new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH);
        this.orderBook = new OrderBook();
        this.tradingDecisionFactory = new HardcodedTradingDecisionFactory(portfolio, result);
        this.trader = new Trader(stockMarket, tradingDecisionFactory, portfolio, orderBook);
    }

    public void run() {
        trader.tradeAllStocksInMarket();
        new PrintResult(true).print(this, candidate);
    }

    public void runOn(LocalDate tradingDate) {
        trader.tradeOn(tradingDate);
    }

    public BuyTradingDecision buyDecisions() {
        return tradingDecisionFactory.generateBuyDecision(this.stockMarket.getStockPrices().get(0));
    }

    public SellTradingDecision sellDecisions() {
        return tradingDecisionFactory.generateSellDecision(stockMarket.getStockPrices().get(0));
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

    public List<BuySellOperation> allOrders() {
        return this.portfolio.getStats().allOrders();
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
                sellAfterAFixedNumberOfDaysComposite.addSellTradingDecision(new SellLastDayOfMarket(portfolio, stockPrices));

                return sellAfterAFixedNumberOfDaysComposite;

            }
        };
        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, candidate.getChromosome(), portfolio, tradingDecisionFactory);
        runner.run();
    }
    public static void main3(String[] args) {
        LocalDate fromDate = new LocalDate(2019, 1, 1);
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

    public static void main(String[] args) {
//        LocalDate fromDate = new LocalDate(2001, 1, 1);
        LocalDate fromDate = new LocalDate(2010, 1, 1);

        runOneSectorWithOneCandidate(fromDate, StockMarket.developedMarkets(), "1000010001110011011110111110011100011111111101110001");

//        runAllSectorsOnAllCandidates(fromDate);
    }

    private static void runOneSectorWithOneCandidate(LocalDate fromDate, String[] sector, String candidateString) {
        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, LocalDate.now(), sector);
        BitString candidate = new BitString(candidateString);
        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, candidate);
        runner.run();
        System.out.println("==========================================");
        System.out.println("******************************************");
        System.out.println("==========================================");
    }

    private static void runAllSectorsOnAllCandidates(LocalDate fromDate) {
        StockMarket.allSectors().stream().forEach(sectors -> {
            System.out.println("==========================================");
            System.out.println("******************************************");
            System.out.println(sectors.getFullName());
            System.out.println("******************************************");
            System.out.println("==========================================");

            StockMarket stockMarket = new StockMarketBuilder().build(fromDate, LocalDate.now(), sectors.getStockSymbols());

            GlobalStockMarketCandidates.candidates().stream().forEach(candidate -> {
                OneCandidateRunner runner = new OneCandidateRunner(stockMarket, candidate.getChromosome());
                runner.run();
                System.out.println("==========================================");
                System.out.println("******************************************");
                System.out.println("==========================================");
            });
        });
    }

    public static void mainOri(String[] args) {
//        LocalDate fromDate = new LocalDate(2001, 1, 1);
        LocalDate fromDate = new LocalDate(2010, 1, 1);

        StockMarket stockMarket = new StockMarketBuilder().buildInMemory(fromDate);
        Portfolio portfolio = new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH);
        HardcodedTradingDecisionFactory tradingDecisionFactory = new HardcodedTradingDecisionFactory(portfolio, new BitString("011010101010010011100000101101111010001"));


        TradingDecisionFactory tradingDecisionFactory2 = fibonacciDecision(portfolio);
        Candidate candidate = new Candidate("XXX", "0100100101110011100000101011110110111011011110000010", Arrays.asList(FibonacciRetracementGenerator.class, StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class, TrailingStopGenerator.class));
        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, candidate.getChromosome(), portfolio, tradingDecisionFactory2   );
        runner.run();
    }

    private static TradingDecisionFactory movingAvgCrossOverTradingDecision(Portfolio portfolio) {
        return new TradingDecisionFactory() {
            @Override
            public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
                BuyWhenNoOppositionsTradingDecision tradingDecisionComposite = new BuyWhenNoOppositionsTradingDecision();
                tradingDecisionComposite.addBuyTradingDecision(new DoNotBuyWhenSameStockInPortfolio(portfolio, stockPrices));
                tradingDecisionComposite.addBuyTradingDecision(new MovingAverageCrossOver(stockPrices, 12, 26, MAType.Sma, MAType.Sma));
                return tradingDecisionComposite;
            }

            @Override
            public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
                SellWhenNoOppositionsTradingDecision sellTrailingStopComposite = new SellWhenNoOppositionsTradingDecision();
                sellTrailingStopComposite.addSellTradingDecision(new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices));
                sellTrailingStopComposite.addSellTradingDecision(new MovingAverageCrossOver(stockPrices, 12, 26, MAType.Sma, MAType.Sma));
                sellTrailingStopComposite.addSellTradingDecision(new TrailingStop(portfolio, stockPrices, new BigDecimal(5), new BigDecimal(13)));
                sellTrailingStopComposite.addSellTradingDecision(new SellLastDayOfMarket(portfolio, stockPrices));

                return sellTrailingStopComposite;

            }
        };
    }

    private static TradingDecisionFactory macdTradingDecision(Portfolio portfolio) {
        return new TradingDecisionFactory() {
            @Override
            public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
                BuyWhenNoOppositionsTradingDecision tradingDecisionComposite = new BuyWhenNoOppositionsTradingDecision();
                tradingDecisionComposite.addBuyTradingDecision(new DoNotBuyWhenSameStockInPortfolio(portfolio, stockPrices));
                tradingDecisionComposite.addBuyTradingDecision(
                        new DelayDecisionToPriceChange(stockPrices, new MacdCrossOver(stockPrices, 12, 26, 9),
                                3)
                );
                return tradingDecisionComposite;
            }

            @Override
            public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
                SellWhenNoOppositionsTradingDecision sellTrailingStopComposite = new SellWhenNoOppositionsTradingDecision();
                sellTrailingStopComposite.addSellTradingDecision(new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices));
                sellTrailingStopComposite.addSellTradingDecision(
                        new DelayDecisionToPriceChange(stockPrices, new MacdCrossOver(stockPrices, 12, 26, 9), 3));
                sellTrailingStopComposite.addSellTradingDecision(new TrailingStop(portfolio, stockPrices, new BigDecimal(5), new BigDecimal(13)));
                sellTrailingStopComposite.addSellTradingDecision(new SellLastDayOfMarket(portfolio, stockPrices));

                return sellTrailingStopComposite;

            }
        };
    }
    private static TradingDecisionFactory fibonacciDecision(Portfolio portfolio) {
        return new TradingDecisionFactory() {
            @Override
            public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
                BuyWhenNoOppositionsTradingDecision tradingDecisionComposite = new BuyWhenNoOppositionsTradingDecision();
                tradingDecisionComposite.addBuyTradingDecision(new DoNotBuyWhenSameStockInPortfolio(portfolio, stockPrices));

                BuyWhenNoOppositionsTradingDecision innerComposite = new BuyWhenNoOppositionsTradingDecision();

                innerComposite.addBuyTradingDecision(
                        new KeepDecisionForFewDays(stockPrices,
                                new FibonacciRetracementBuyDecision(stockPrices,
                                        Range.open(new BigDecimal(0.7), new BigDecimal(0.8)),
                                        FibonacciRetracementBuyDecision.FIB_RETR_0_5, 20, 30,
                                        FibonacciRetracementBuyDecision.FIB_EXT_1_618, FibonacciRetracementBuyDecision.FIB_EXT_1_786),
                                    3)
                );
                innerComposite.addBuyTradingDecision(
                        new KeepDecisionForFewDays(stockPrices,
//                                new MacdCrossOver(stockPrices, 12, 26, 9),
//                                new MoneyFlowIndex(stockPrices, Range.atMost(20.0), Range.atLeast(80.0), 14),
//                                new UltimateOscillator(stockPrices, Range.atMost(30.0), Range.atLeast(70.0), 7),
                                new StochasticOscillator(stockPrices, 12, 6, 4, MAType.Ema, MAType.Sma),
                                    3)
                );
                tradingDecisionComposite.addBuyTradingDecision(new DelayDecisionToPriceChange(stockPrices, innerComposite, 5));



//                tradingDecisionComposite.addBuyTradingDecision(
//                        new KeepDecisionForFewDays(stockPrices,
//                                new DelayDecisionToPriceChange(stockPrices, new FibonacciRetracementBuyDecision(stockPrices,
//                                        Range.open(new BigDecimal(0.6), new BigDecimal(0.7)), 0.5, 20, 40),
//                                        //                                Range.open(new BigDecimal(0.60), new BigDecimal(0.70)), 20, 40),
//                                        5),
//                                5)
//                );
//                tradingDecisionComposite.addBuyTradingDecision(
//                        new KeepDecisionForFewDays(stockPrices,
//                                new DelayDecisionToPriceChange(stockPrices,
//                                        new MoneyFlowIndex(stockPrices, Range.atMost(20.0), Range.atLeast(80.0), 14),
//                                        5),
//                                5)
//                );
//                tradingDecisionComposite.addBuyTradingDecision(
//                        new KeepDecisionForFewDays(stockPrices,
//                                new MacdCrossOver(stockPrices, 12, 26, 9),
//                                3)
//                );
                return tradingDecisionComposite;
            }

            @Override
            public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
                SellWhenNoOppositionsTradingDecision sellWhenNoOppositionsTradingDecision = new SellWhenNoOppositionsTradingDecision();
                sellWhenNoOppositionsTradingDecision.addSellTradingDecision(new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices));

                SellWhenAnyAgreeTradingDecision sellTrailingStopComposite = new SellWhenAnyAgreeTradingDecision();
                sellTrailingStopComposite.addSellTradingDecision(new TakeProfitPartialSell(portfolio, stockPrices));
                sellTrailingStopComposite.addSellTradingDecision(new MoneyFlowIndex(stockPrices, Range.atMost(20.0), Range.atLeast(70.0), 16));
//                sellTrailingStopComposite.addSellTradingDecision(
//                        new StochasticOscillator(stockPrices, 14, 3, 3, MAType.Sma, MAType.Sma));
//                sellTrailingStopComposite.addSellTradingDecision(
//                        new DelayDecisionToPriceChange(stockPrices, new MacdCrossOver(stockPrices, 12, 26, 9), 3));
                sellTrailingStopComposite.addSellTradingDecision(new TrailingStop(portfolio, stockPrices, new BigDecimal(6.80), new BigDecimal(9.29)));
                sellTrailingStopComposite.addSellTradingDecision(new SellLastDayOfMarket(portfolio, stockPrices));

                sellWhenNoOppositionsTradingDecision.addSellTradingDecision(sellTrailingStopComposite);
                return sellWhenNoOppositionsTradingDecision;

            }
        };
    }

    public OrderBook getOrderBook() {
        return this.orderBook;
    }
}
