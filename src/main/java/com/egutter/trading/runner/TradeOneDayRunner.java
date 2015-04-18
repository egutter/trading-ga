package com.egutter.trading.runner;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.order.MarketOrder;
import com.egutter.trading.out.StatsPrinter;
import com.egutter.trading.repository.PortfolioRepository;
import com.egutter.trading.stats.CandidateRanker;
import com.egutter.trading.stats.CandidateStatsCollector;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.PortfolioBuilder;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 1/1/15.
 */
public class TradeOneDayRunner {

    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final PortfolioRepository portfolioRepository;
    private final List<String> resultBuffer;
    private final CandidateRanker ranker;

    public TradeOneDayRunner(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.portfolioRepository = new PortfolioRepository();
        this.resultBuffer = new ArrayList<String>();
        this.ranker = new CandidateRanker(new CandidateStatsCollector(this.portfolioRepository));
    }

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2014, 1, 1);
        LocalDate toDate = LocalDate.now();
        TradeOneDayRunner runner = new TradeOneDayRunner(fromDate, toDate);
        runner.run(false);
        System.out.println(runner.runOutput("\n"));
    }

    public void run(boolean printStats) {

        LocalDate currentLastTradingDate = currentLastTradingDate();
        resultBuffer.add("Current last trading date " + currentLastTradingDate);

        Map<String, Pair<Integer, List<Candidate>>> countStockBought = new HashMap<String, Pair<Integer, List<Candidate>>>();
        Map<String, Pair<Integer, List<Candidate>>> countStockSold = new HashMap<String, Pair<Integer, List<Candidate>>>();

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, true, true);
        LocalDate lastTradingDay = stockMarket.getLastTradingDay(); //new LocalDate(2015, 3, 17);//

        resultBuffer.add("New last trading date " + lastTradingDay);

        if (!lastTradingDay.isAfter(currentLastTradingDate)) {
            resultBuffer.add("Nothing imported. Returning without running the trader");
            return;
        }

        candidates().stream().forEach(candidate -> {
            Portfolio portfolio = new PortfolioBuilder(portfolioRepository).build(candidate.key());
            OneCandidateRunner oneCandidateRunner = new OneCandidateRunner(stockMarket, candidate.getChromosome(), portfolio, candidate.getTradingDecisionGenerators(), false);
            oneCandidateRunner.runOn(lastTradingDay);

            portfolioRepository.update(candidate.key(), portfolio);
            if (!oneCandidateRunner.getOrderBook().getOrders().isEmpty()) {
                resultBuffer.add(candidateName(candidate));
                resultBuffer.add("On " + lastTradingDay + " " + oneCandidateRunner.getOrderBook());
                resultBuffer.add("==========================================");
            }
            countStocksBought(countStockBought, oneCandidateRunner, candidate);
            countStocksSold(countStockSold, oneCandidateRunner, candidate);
        });

        resultBuffer.add("==========================================");
        resultBuffer.add("ALL STOCKS BOUGHT");
        countStockBought.forEach((stockName, pair) -> {
            resultBuffer.add("Stock " + stockName + " count " + pair.getFirst() + " Candidates: " + candidateListNames(pair.getSecond()));
        });
        resultBuffer.add("==========================================");
        resultBuffer.add("ALL STOCKS SOLD");
        countStockSold.forEach((stockName, pair) -> {
            resultBuffer.add("Stock " + stockName + " count " + pair.getFirst() + " Candidates: " + candidateListNames(pair.getSecond()));
        });
        if (printStats) {
            new StatsPrinter(portfolioRepository, stockMarket, candidates()).printStatsAndPortfolio(lastTradingDay);
        }
    }

    private String candidateName(Candidate candidate) {
        return ranker.rank(candidate) + candidate.toString();
    }
    private String candidateListNames(List<Candidate> candidates) {
        return Joiner.on(" - ").join(candidates.stream().map(candidate -> candidateName(candidate)).iterator());
    }

    private LocalDate currentLastTradingDate() {
        return new StockMarketBuilder().build(fromDate, toDate).getLastTradingDay();
    }

    private void countStocksBought(Map<String, Pair<Integer, List<Candidate>>> countStockBought, OneCandidateRunner oneCandidateRunner, Candidate candidate) {
        countMarketOrders(countStockBought, oneCandidateRunner.getOrderBook().buyOrders(), candidate);
    }
    private void countStocksSold(Map<String, Pair<Integer, List<Candidate>>> countStockSold, OneCandidateRunner oneCandidateRunner, Candidate candidate) {
        countMarketOrders(countStockSold, oneCandidateRunner.getOrderBook().sellOrders(), candidate);
    }

    private void countMarketOrders(Map<String, Pair<Integer, List<Candidate>>> countMarketOrders, FluentIterable<? extends MarketOrder> marketOrders, Candidate candidate) {
        marketOrders.toList().stream().forEach(sellOrder -> {
            int count = 0;
            List<Candidate> candidates = new ArrayList<Candidate>();
            if (countMarketOrders.containsKey(sellOrder.getStockName())) {
                count = countMarketOrders.get(sellOrder.getStockName()).getFirst();
                candidates = countMarketOrders.get(sellOrder.getStockName()).getSecond();
            }
            candidates.add(candidate);
            countMarketOrders.put(sellOrder.getStockName(), new Pair<Integer, List<Candidate>>(count+1, candidates));
        });
    }

    public List<Candidate> candidates() {
        return asList(
                new Candidate("Conservative 2012-2014", "110011100011001000111101100101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2014",      "100000100000001110101010011101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2014",      "110100011001000010110010010100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
//                new Candidate("Aggressive 2014",        "111110100011000110000001101101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
//                new Candidate("Aggressive 2013-2014",   "111110100110101000100001111011", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
//                new Candidate("Aggressive lastQ2014",   "100110110001010111001000101000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110001100010100010011110110100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110010011111001010011010111100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "011100111001111110100110101011", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("Conservative 2012-2014", "111000101001000000111011010100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",      "101111010110010000110110001111", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",      "100011101011010111000010110101", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
//                new Candidate("Aggressive 2014",        "111110100101000000100010101010", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
//                new Candidate("Aggressive 2013-2014",   "111111110100000110100010110011", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110001100010101100100010100100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110000010111101100010010000100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "000001011001010110000001110011", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "110001100001001100000110111100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "010101100001100110000110101100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2012-2014",   "110001011100010011100001011000", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",        "111110110111011000100110010010", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",        "111110110000101000101001111010", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
//                new Candidate("Aggressive 2013-2014",   "111101000101010100100010100100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
//                new Candidate("Aggressive lastQ2014",   "010111101100011001000110111110", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "110001110101011110010010011100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101110101101010000110010011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "010001100111001111110010111110", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "111010101001011100000110100011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "101110011011001100010010110101", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2012-2014", "111001100000101100111111101000", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-2014", "100100100010000000111111101110", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2014",      "110000100001010101000101111010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2014",      "110001100010100011000001010010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
//                new Candidate("Aggressive 2014",        "111110100101101100001010010010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
//                new Candidate("Aggressive 2013-2014",   "111111100100010000000010011000", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111001100000011010100010100101", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "011101011001000100000110010001", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "010000101010111110110110001110", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "000111010111001010000010111010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "000100001100101110100011000010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 2012-2014",   "010100111000100110110010100100", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-2014",   "111010101101110010110110011011", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2014",        "101011111111001111011100000011", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2014",        "101111100111011100110011110011", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
//                new Candidate("Aggressive 2014",          "111110100010100000000110010001", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
//                new Candidate("Aggressive 2013-2014",     "110010101110011110100001101000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
//                new Candidate("Aggressive lastQ2014",     "111111000001110111011110101010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "101101100001111000100010101010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "110000011101000011101101111000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Consv LogE 2013-2014",     "110001100011000111111001010000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "000010011111110010100001010000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("90 over market 2013-2014", "101111001010000101011100111100", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),

                new Candidate("Conservative 2014", "00100101011111111", asList(MovingAverageConvergenceDivergenceGenerator.class)),

                new Candidate("Conservative 2012-2014", "11110111110010011", asList(BollingerBandsGenerator.class)),
                new Candidate("Conservative 2014",      "00000110001011001", asList(BollingerBandsGenerator.class)),
                new Candidate("Conservative 2014",      "10010110001100111", asList(BollingerBandsGenerator.class)),
//                new Candidate("Aggressive 2014",        "11000011110011110", asList(BollingerBandsGenerator.class)),
//                new Candidate("Aggressive 2013-2014",   "11110001010111011", asList(BollingerBandsGenerator.class)),
//                new Candidate("Aggressive lastQ2014",   "11111011110010010", asList(BollingerBandsGenerator.class)),

                new Candidate("Conservative 2014",      "00000111011110101", asList(RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2014",      "00000111011110110", asList(RelativeStrengthIndexGenerator.class)),
//                new Candidate("Aggressive 2013-2014",   "11110100001111011", asList(RelativeStrengthIndexGenerator.class)),

                new Candidate("Conservative 2014",    "10011000000011101", asList(MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",    "10001000001011101", asList(MoneyFlowIndexGenerator.class)),
//                new Candidate("Aggressive 2013-2014", "11110011010000100", asList(MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2012-2014",   "110001111110010111010001000011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",        "111101100100101110101110100111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",        "101101100011011110101001110111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101100110011110000010111011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "001101100011011110011001110011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "101101101001101110000110110101", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
//                new Candidate("Aggressive 2013-2014",   "111100111010010000100010100101", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2012-2014",   "111001001101110111010101010111", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2014",        "101010111100100100011010010111", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
//                new Candidate("Aggressive lastQ2014",   "010110111100100101101110111100", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative lastQ2014",   "001110111000001000100010110100", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101100100101110011110101111", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101100110001110001010010011", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",     "010001100011011110010001101001", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "010001100101011110011110011011", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "010001100100001110010101101001", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "110001101110111110000110000100", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("Conservative 2012-2014",   "111101110101010110011111010010", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2014",        "100101100100111110100110011010", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101011100000101000111110111", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101001100101101000011100100", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv LogE 2013-2014",     "100001000011110001000010101110", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "011101000101110011000011010110", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "100101001010110111001100000100", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("90 over market 2013-2014", "101010111101000100100111111000", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 2012-2014", "11110111110100110", asList(AroonOscilatorGenerator.class)),


                new Candidate("3 Decisions <75 filter 2013-2014", "1100001111000100101010011001010010010110100",
                    asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1011011000111000100011010110000000010011110",
                    asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1100011000101110100011000010000011010001100",
                    asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1110011001000111100101101010110001110101101",
                    asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1110011000101111100011010000100100010010011",
                    asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1110011001101011100010001011010001110010011",
                    asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1111100010111111100010100100000011111111111",
                    asList(AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1100111101000001101100010101000100010001100",
                    asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1100000101001111101100010110000100010001100",
                    asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1100000101001000001100001100110000101011000",
                    asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("90 over market 2013-2014", "1011101000000001001111000101110011110110101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1100010101101111111011011011000100010010100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("90 over market 2013-2014", "1011001100100010110001010100010100110110101",
                    asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1100011011001011100100000111010001110011100",
                    asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1011011010001011101000000011000011010101101",
                    asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1100011011000011100101001011000000110011100",
                    asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1111111001111100001110101001010100001000100",
                    asList(AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1011011010010111111111101001110001010101101",
                    asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1011101111101101010110000111100010110000101",
                    asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1011110101100101001110111101110000110110101",
                    asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1110010111110000010001101000100100010000011",
                    asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1011000011010001110011010011110100010101101",
                    asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1110011001001001001000100010110110111110101",
                    asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1101011010100011111010010010110001110101101",
                    asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1111011010010011111011110000100000101111011",
                    asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1100110010111010101100000111110001110110100",
                    asList(AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1110011011111011111000001100000000110101101",
                    asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1011011011110011110111000101010100010110100",
                    asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1011001001010100001011111011101100000010000",
                    asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Cons w/Commision 2013-2014", "111100011111010000111110100000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "111100010110010100111110101101", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "101101101000011110001010101101", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "111001101010001110000110101011", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "111110010011011100001010110011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "101101011111110101100010000111", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "001101011111111101100001110000", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "110101011110001111001110100000", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class))

        );
    }

    public List<String> getResultBuffer() {
        return resultBuffer;
    }

    public String runOutput(String separator) {
        return String.join(separator, this.getResultBuffer());
    }

}
