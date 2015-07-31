package com.egutter.trading.runner;

import com.egutter.trading.candidates.MixOne;
import com.egutter.trading.decision.Candidate;
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
        try {
            String candidateFactory = System.getenv().get("CANDIDATE_FACTORY");
            return (List<Candidate>) Class.forName("com.egutter.trading.candidates." + candidateFactory).getMethod("candidates").invoke(null);
        } catch (Exception e) {
            return MixOne.candidates();
        }
    }

    public List<String> getResultBuffer() {
        return resultBuffer;
    }

    public String runOutput(String separator) {
        return String.join(separator, this.getResultBuffer());
    }

}
