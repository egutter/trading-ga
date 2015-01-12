package com.egutter.trading.runner;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.repository.PortfolioRepository;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.PortfolioBuilder;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.joda.time.LocalDate;

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

    public TradeOneDayRunner(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.portfolioRepository = new PortfolioRepository();
    }

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2014, 1, 1);
        LocalDate toDate = LocalDate.now();

        TradeOneDayRunner runner = new TradeOneDayRunner(fromDate, toDate);
        runner.run();
    }

    private void run() {
//        portfolioRepository.removeAll();

        Map<String, Integer> countStockBought = new HashMap<String, Integer>();

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, true);
        LocalDate lastTradingDay = stockMarket.getLastTradingDay();
        candidates().stream().forEach(candidate -> {
            System.out.println(candidate.getDescription() + ": " + candidate.key());
            Portfolio portfolio = new PortfolioBuilder(portfolioRepository).build(candidate.key());
            OneCandidateRunner oneCandidateRunner = new OneCandidateRunner(stockMarket, candidate.getChromosome(), portfolio, candidate.getTradingDecisionGenerators(), false);
            oneCandidateRunner.runOn(lastTradingDay);

            portfolioRepository.update(candidate.key(), portfolio);
            System.out.println("On " + lastTradingDay + " " + oneCandidateRunner.getOrderBook());
            System.out.println("==========================================");
            countStocksBought(countStockBought, oneCandidateRunner);
        });

        System.out.println("ALL STOCKS BOUGHT");
        countStockBought.forEach((stockName, count) -> {
            System.out.println("Stock " + stockName + " count " + count);
        });
        System.out.println("==========================================");
        System.out.println("ALL STATS");
        portfolioRepository.forEachStat((key, buySellOrder) -> {
            System.out.println("==========================================");
            System.out.println(key);
            System.out.println(buySellOrder);
        });

        System.out.println("==========================================");
        System.out.println("ALL STOCKS");
        portfolioRepository.forEachStock((key, buyOrder) -> {
            System.out.println("==========================================");
            System.out.println(key);
            System.out.println(buyOrder);
        });

    }

    private void countStocksBought(Map<String, Integer> countStockBought, OneCandidateRunner oneCandidateRunner) {
        oneCandidateRunner.getOrderBook().buyOrders().toList().stream().forEach(buyOrder -> {
            int count = 0;
            if (countStockBought.containsKey(buyOrder.getStockName())) {
                count = countStockBought.get(buyOrder.getStockName());
            }
            countStockBought.put(buyOrder.getStockName(), count+1);
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

                new Candidate("Conservative 2012-2014", "110001011100010011100001011000", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",      "111110110111011000100110010010", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",      "111110110000101000101001111010", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
//                new Candidate("Aggressive 2013-2014",   "111101000101010100100010100100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
//                new Candidate("Aggressive lastQ2014",   "010111101100011001000110111110", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110001110101011110010010011100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111101110101101010000110010011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "010001100111001111110010111110", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "111010101001011100000110100011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),

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

                new Candidate("Conservative 2012-2014", "110001111110010111010001000011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",      "111101100100101110101110100111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",      "101101100011011110101001110111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111101100110011110000010111011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "001101100011011110011001110011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
//                new Candidate("Aggressive 2013-2014",   "111100111010010000100010100101", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2012-2014", "111001001101110111010101010111", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2014",      "101010111100100100011010010111", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
//                new Candidate("Aggressive lastQ2014",   "010110111100100101101110111100", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative lastQ2014", "001110111000001000100010110100", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111101100100101110011110101111", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111101100110001110001010010011", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "010001100011011110010001101001", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "010001100101011110011110011011", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "010001100100001110010101101001", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("Conservative 2012-2014", "111101110101010110011111010010", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2014",      "100101100100111110100110011010", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111101011100000101000111110111", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111101001100101101000011100100", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "100001000011110001000010101110", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "011101000101110011000011010110", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "100101001010110111001100000100", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),

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
                    asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class))

        );
    }
}
