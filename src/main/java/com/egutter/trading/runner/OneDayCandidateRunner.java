package com.egutter.trading.runner;

import com.egutter.trading.candidates.GlobalStockMarketCandidates;
import com.egutter.trading.decision.Candidate;
import com.egutter.trading.order.MarketOrder;
import com.egutter.trading.stock.StockGroup;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.google.common.collect.FluentIterable;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by egutter on 1/1/15.
 */
public class OneDayCandidateRunner {

    private final LocalDate fromDate;
    private final LocalDate toDate;
    private final List<String> resultBuffer;

    public OneDayCandidateRunner(LocalDate fromDate, LocalDate toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.resultBuffer = new ArrayList<String>();
    }

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2020, 1, 1);
        LocalDate toDate = LocalDate.now();
        OneDayCandidateRunner runner = new OneDayCandidateRunner(fromDate, toDate);
        LocalDate tradeOn = new LocalDate(2020, 9, 11);
        runner.run(tradeOn);
        System.out.println(runner.runOutput("\n"));
    }

    public void run(LocalDate tradeOn) {

        Map<String, Pair<Integer, List<Candidate>>> countStockBought = new HashMap<String, Pair<Integer, List<Candidate>>>();
        Map<String, Pair<Integer, List<Candidate>>> countStockSold = new HashMap<String, Pair<Integer, List<Candidate>>>();

        List<StockGroup> sectors = StockMarket.allSectors();
        sectors.stream().forEach(stockGroup -> {

            StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, true, true, stockGroup.getStockSymbols());

            candidates().stream().forEach(candidate -> {

                Optional<StockGroup> candidateStockGroup = candidate.getStockGroups().stream()
                        .filter(candidateGroup -> candidateGroup.equals(stockGroup))
                        .findAny();
                candidateStockGroup.ifPresent(candidateGroup -> {
                    OneCandidateRunner oneCandidateRunner = new OneCandidateRunner(stockMarket, candidate.getChromosome());
                    oneCandidateRunner.runOn(tradeOn);

                    if (!oneCandidateRunner.getOrderBook().getOrders().isEmpty()) {
                        resultBuffer.add("==========================================");
                        resultBuffer.add(candidateGroup.getFullName());
                        resultBuffer.add("New last trading date " + tradeOn);
                        resultBuffer.add(candidateName(candidate));
                        resultBuffer.add("On " + tradeOn + " " + oneCandidateRunner.getOrderBook());
                        resultBuffer.add("==========================================");
                    }
                    countStocksBought(countStockBought, oneCandidateRunner, candidate);
                    countStocksSold(countStockSold, oneCandidateRunner, candidate);
                });
            });
        });

        resultBuffer.add("==========================================");
        resultBuffer.add("ALL STOCKS BOUGHT");
        countStockBought.forEach((stockName, pair) -> {
            resultBuffer.add("Stock " + stockName + " count " + pair.getFirst() + " Candidates: " + candidateListNames(stockName, pair.getSecond()));
        });
        resultBuffer.add("==========================================");
    }

    private String candidateName(Candidate candidate) {
        return candidate.toString();
    }

    private String candidateListNames(String stockName, List<Candidate> candidates) {
        return candidateListNamesWithNameFunction(candidates, candidate -> candidateName(candidate));
    }


    private String candidateListNamesWithNameFunction(List<Candidate> candidates, Function<Candidate, String> extractName) {
        return candidates.stream()
                .map(extractName)
                .collect(Collectors.joining(" - "));
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
        return GlobalStockMarketCandidates.newCandidates();
    }

    public List<String> getResultBuffer() {
        return resultBuffer;
    }

    public String runOutput(String separator) {
        return String.join(separator, this.getResultBuffer());
    }

}
