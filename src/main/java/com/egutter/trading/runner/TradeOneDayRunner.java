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
    private final StockMarket stockMarket;
    private final PortfolioRepository portfolioRepository;

    public TradeOneDayRunner(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.stockMarket = new StockMarketBuilder().build(fromDate, toDate);
        this.portfolioRepository = new PortfolioRepository();
    }

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2014, 1, 1);
        LocalDate toDate = LocalDate.now();

        TradeOneDayRunner runner = new TradeOneDayRunner(fromDate, toDate);
        runner.run();
    }

    private void run() {
        portfolioRepository.removeAll();

        Map<String, Integer> countStockBought = new HashMap<String, Integer>();
        new YahooQuoteImporter().runImport();
        candidates().stream().forEach(candidate -> {
            System.out.println(candidate.getDescription() + ": " + candidate.key());
            Portfolio portfolio = new PortfolioBuilder(portfolioRepository).build(candidate.key());
            OneCandidateRunner oneCandidateRunner = new OneCandidateRunner(this.stockMarket, candidate.getChromosome(), portfolio, candidate.getTradingDecisionGenerators(), false);
            oneCandidateRunner.runOn(stockMarket.getLastTradingDay());

            portfolioRepository.update(candidate.key(), portfolio);
            System.out.println(oneCandidateRunner.getOrderBook());
            System.out.println("==========================================");
            countStocksBought(countStockBought, oneCandidateRunner);
        });
        countStockBought.forEach((stockName, count) -> {
            System.out.println("Stock " + stockName + " count " + count);
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

    private List<Candidate> candidates() {
        return asList(
                new Candidate("Conservative 2014",      "100000100000001110101010011101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2012-2014", "011100101110100101001101001001", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Aggressive 2014",        "111110100011000110000001101101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Aggressive 2013-2014",   "111110100110101000100001111011", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Aggressive lastQ2014",   "100110110001010111001000101000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110001100010100010011110110100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "001000100000101110000001111101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("Conservative 2014",      "101111010110010000110110001111", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-2014", "100111011001001101000100101100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Aggressive 2014",        "111110100101000000100010101010", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Aggressive 2013-2014",   "111111110100000110100010110011", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110001100010101100100010100100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "000001011001010110000001110011", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2014",      "111110110111011000100110010010", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-2014", "111110001101100101001101011101", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Aggressive 2013-2014",   "111101000101010100100010100100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Aggressive lastQ2014",   "010111101100011001000110111110", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110001110101011110010010011100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "111001011101100001101001101111", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2014",      "110000100001010101000101111010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-2014", "111001100000101100111111101000", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Aggressive 2014",        "111110100101101100001010010010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Aggressive 2013-2014",   "111111100100010000000010011000", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111001100000011010100010100101", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "010000101010111110110110001110", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 2014",        "101011111111001111011100000011", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-2014",   "010100111000100110110010100100", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Aggressive 2014",          "111110100010100000000110010001", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Aggressive 2013-2014",     "110010101110011110100001101000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Aggressive lastQ2014",     "111111000001110111011110101010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative lastQ2014",   "111110110110001111011111100101", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "101101100001111000100010101010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Consv LogE 2013-2014",     "110001100011000111111001010000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),

                new Candidate("Conservative 2014",      "00000110001011001", asList(BollingerBandsGenerator.class)),
                new Candidate("Aggressive 2014",        "11000011110011110", asList(BollingerBandsGenerator.class)),
                new Candidate("Aggressive 2013-2014",   "11110001010111011", asList(BollingerBandsGenerator.class)),
                new Candidate("Aggressive lastQ2014",   "11111011110010010", asList(BollingerBandsGenerator.class)),
                new Candidate("Conservative lastQ2014", "11101011110100010", asList(BollingerBandsGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "01000110010010111", asList(BollingerBandsGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "01000110001110111", asList(BollingerBandsGenerator.class)),

                new Candidate("Conservative 2014",      "00000111011110101", asList(RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2012-2014", "01101000101111011", asList(RelativeStrengthIndexGenerator.class)),
                new Candidate("Aggressive 2013-2014",   "11110100001111011", asList(RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "11001001101010111", asList(RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "11110111101111101", asList(RelativeStrengthIndexGenerator.class)),

                new Candidate("Conservative 2014",    "10011000000011101", asList(MoneyFlowIndexGenerator.class)),
                new Candidate("Aggressive 2013-2014", "11110011010000100", asList(MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2014",      "111101100100101110101110100111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-2014", "111010111001000110011001101111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Aggressive 2013-2014",   "111100111010010000100010100101", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111101011101111001101010010000", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "010001100011101111111110110011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Aggressive lastQ2014",   "010110111100100101101110111100", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative lastQ2014", "001110111000001000100010110100", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111101100100101110011110101111", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "010001100011011110010001101001", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("Less Consv 2013-2014",   "111101011100000101000111110111", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "100001000011110001000010101110", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 2012-2014", "11110111110100110", asList(AroonOscilatorGenerator.class))

        );
    }
}
