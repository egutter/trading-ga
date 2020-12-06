package com.egutter.trading.runner;

import com.egutter.trading.candidates.GlobalStockMarketCandidates;
import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.FibonacciRetracementStrategyFactory;
import com.egutter.trading.decision.consensus.SellWhenAnyAgreeTradingDecision;
import com.egutter.trading.decision.constraint.SellLastDayOfMarket;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.consensus.BuyWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.consensus.SellWhenNoOppositionsTradingDecision;
import com.egutter.trading.decision.constraint.*;
import com.egutter.trading.decision.factory.TradingDecisionFactory;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.decision.technicalanalysis.*;
import com.egutter.trading.genetic.StockTradingFitnessEvaluator;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.OrderBook;
import com.egutter.trading.order.condition.BuyDecisionConditionsFactory;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.out.PrintResult;
import com.egutter.trading.out.adapters.BitStringGsonAdapter;
import com.egutter.trading.out.adapters.ClassTypeAdapterFactory;
import com.egutter.trading.out.adapters.JodaLocalDateGsonAdapter;
import com.egutter.trading.stock.*;
import com.google.common.collect.Range;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tictactec.ta.lib.MAType;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;
import org.uncommons.maths.binary.BitString;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static com.egutter.trading.stock.StockMarket.*;
import static com.egutter.trading.stock.StockMarket.innovationSector;
import static java.util.Arrays.asList;

/**
 * Created by egutter on 11/29/14.
 */
public class OneCandidateRunner {

    private final Portfolio portfolio;
    private final Trader trader;
    private TradingDecisionFactory tradingDecisionFactory;
    private final OrderBook orderBook;
    private FibonacciRetracementStrategyFactory fibTradingDecisionFactory;
    private StockMarket stockMarket;
    private final BitString candidate;

    public OneCandidateRunner(StockMarket stockMarket, BitString candidate, List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators) {
        this(stockMarket, candidate, new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH), tradingDecisionGenerators, true);
    }

    public OneCandidateRunner(StockMarket stockMarket, BitString candidate, Portfolio portfolio, List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators, boolean onExperiment) {
        this.stockMarket = stockMarket;
        this.candidate = candidate;
        this.portfolio = portfolio;
        this.orderBook = new OrderBook();
        this.fibTradingDecisionFactory = new FibonacciRetracementStrategyFactory(portfolio, candidate, tradingDecisionGenerators);
        this.trader = new Trader(stockMarket, fibTradingDecisionFactory, portfolio, orderBook);
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
        this(stockMarket, result, Arrays.asList(StochasticOscillatorGenerator.class,
                ChaikinOscillatorGenerator.class));
    }

    public void run() {
        run(true);
    }

    public void run(boolean printResults) {
        trader.tradeAllStocksInMarket();
        if (printResults) {
            new PrintResult(false).print(this, candidate);
            whenWonOver90Percent(() -> new PrintResult(true).print(this, candidate));
        }
    }

    private void whenWonOver90Percent(Runnable r) {
        if (this.percentageOfOrdersWon().compareTo(new BigDecimal(0.90)) >= 0) {
            r.run();
        }
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

    public static void main(String[] args) {
//        LocalDate fromDate = new LocalDate(2001, 1, 1);
        LocalDate fromDate = new LocalDate(2010, 1, 1);

        String[] sector = StockMarket.innovationSector();
////        String[] nvda = new String[]{"NVDA"};
        runOneSectorWithOneCandidate(fromDate, sector, "100101001010110010010011110100100110011",
                Arrays.asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator .class));

//        runAllSectorsOnAllCandidates(fromDate);
    }

    private static void runOneSectorWithOneCandidate(LocalDate fromDate, String[] sector, String candidateString, List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators) {
        Map<String, Candidate> selectedCandidates = new HashMap<String, Candidate>();

        LocalDate toDate = LocalDate.now(); //new LocalDate(2020, 11, 19);
        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, false, false, sector);
        BitString chromosome = new BitString(candidateString);
        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, chromosome, tradingDecisionGenerators);
        runner.run();
        Candidate candidate = new Candidate("INNOVATION_SECTOR", "100101001010110010010011110100100110011",
                asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                asList(
                        new StockGroup(INNOVATION_SECTOR, "1.00(4/0)", innovationSector())
                ));
        appendToCandidates(selectedCandidates, runner, candidate, new StockGroup(INNOVATION_SECTOR, "1.00(4/0)", innovationSector()), fromDate, toDate);
        System.out.println("==========================================");
        System.out.println("******************************************");
        System.out.println("==========================================");
        writeToFile(selectedCandidates);
    }

    private static void runAllSectorsOnAllCandidates(LocalDate fromDate) {
        Map<String, Candidate> selectedCandidates = new HashMap<String, Candidate>();
        StockMarket.allSectors().stream().forEach(stockGroup -> {
            System.out.println("==========================================");
            System.out.println("******************************************");
            System.out.println(stockGroup.getFullName());

            LocalDate toDate = LocalDate.now();
            StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, stockGroup.getStockSymbols());

            GlobalStockMarketCandidates.allNewCandidates().stream().forEach(candidate -> {
                OneCandidateRunner runner = new OneCandidateRunner(stockMarket, candidate.getChromosome(), candidate.getTradingDecisionGenerators());
                runner.run(false);
                runner.whenWonOver90Percent(() -> appendToCandidates(selectedCandidates, runner, candidate, stockGroup, fromDate, toDate));

            });
            System.out.println("Completed at: "+ LocalDateTime.now());
            System.out.println("******************************************");
            System.out.println("==========================================");
        });
        writeToFile(selectedCandidates);
    }

    private static void writeToFile(Map<String, Candidate> selectedCandidates) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(LocalDate.now().toString() + "_all_candidates.json"));
            GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
            gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
            gsonBuilder.registerTypeAdapter(BitString.class, new BitStringGsonAdapter());
            gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaLocalDateGsonAdapter());
            Gson gson = gsonBuilder.create();
            writer.write(gson.toJson(selectedCandidates.values()));

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendToCandidates(Map<String, Candidate> selectedCandidates, OneCandidateRunner runner, Candidate candidate, StockGroup stockGroup, LocalDate fromDate, LocalDate toDate) {
        String stockGroupDescription = new StringBuffer(runner.percentageOfOrdersWon().toString()).
                append("(").append(runner.ordersWon()).append("/").append(runner.ordersLost()).append(")").toString();
        StockGroup newStockGroup = new StockGroup(stockGroup.getName(),
                stockGroupDescription,
                stockGroup.getStockSymbols(),
                runner.percentageOfOrdersWon(),
                runner.ordersWon(),
                runner.ordersLost(),
                fromDate,
                toDate
                );

        if (selectedCandidates.containsKey(candidate.key())) {
            Candidate resultCandidate = selectedCandidates.get(candidate.key());
            resultCandidate.addStockGroup(newStockGroup);
        } else {
            List<StockGroup> stockGroups = new ArrayList();
            stockGroups.add(newStockGroup);
            Candidate newCandidate = new Candidate(candidate.getDescription(), candidate.getChromosome().toString(),
                    candidate.getTradingDecisionGenerators(),
                    stockGroups);
            selectedCandidates.put(candidate.key(), newCandidate);
        }
    }

    public static void mainOri(String[] args) {
//        LocalDate fromDate = new LocalDate(2001, 1, 1);
//        LocalDate fromDate = new LocalDate(2010, 1, 1);
//
//        StockMarket stockMarket = new StockMarketBuilder().buildInMemory(fromDate);
//        Portfolio portfolio = new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH);
//        HardcodedTradingDecisionFactory tradingDecisionFactory = new HardcodedTradingDecisionFactory(portfolio, new BitString("011010101010010011100000101101111010001"));
//
//
//        TradingDecisionFactory tradingDecisionFactory2 = fibonacciDecision(portfolio);
//        Candidate candidate = new Candidate("XXX", "0100100101110011100000101011110110111011011110000010", Arrays.asList(FibonacciRetracementGenerator.class, StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class, TrailingStopGenerator.class));
//        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, candidate.getChromosome(), portfolio, tradingDecisionFactory2   );
//        runner.run();
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
                                        FibonacciRetracementBuyDecision.FIB_EXT_1_618, FibonacciRetracementBuyDecision.FIB_EXT_1_786,
                                        BuyDecisionConditionsFactory.empty()),
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

    public FibonacciRetracementStrategyFactory getFibTradingDecisionFactory() {
        return fibTradingDecisionFactory;
    }

    public StockMarket getStockMarket() {
        return stockMarket;
    }
}
