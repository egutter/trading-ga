package com.egutter.trading.runner;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.order.BuyOrderWithPendingSellOrders;
import com.egutter.trading.out.CandidatesFileHandler;
import com.egutter.trading.stock.StockGroup;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
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
//        LocalDate toDate = LocalDate.now();
        LocalDate tradeOn = new LocalDate(2020, 12, 9);
        OneDayCandidateRunner runner = new OneDayCandidateRunner(fromDate, tradeOn);
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
                    OneCandidateRunner oneCandidateRunner = new OneCandidateRunner(stockMarket, candidate.getChromosome(), candidate.getTradingDecisionGenerators());
                    oneCandidateRunner.runOn(tradeOn);

                    Map<String, BuyOrderWithPendingSellOrders> buyOrderWithPendingSellOrders = oneCandidateRunner.getOrderBook().ordersWithPendingOrdersAt(tradeOn);
                    if (!buyOrderWithPendingSellOrders.isEmpty()) {
                        resultBuffer.add("==========================================");
                        resultBuffer.add(candidateGroup.getFullName());
                        resultBuffer.add("New last trading date " + tradeOn);
                        resultBuffer.add(candidateName(candidate));
                        resultBuffer.add(buyOrderWithPendingSellOrders.toString());
//                        resultBuffer.add("On " + tradeOn + " " + oneCandidateRunner.getOrderBook());
                        resultBuffer.add("==========================================");
                    }
                    countStocksBought(countStockBought, buyOrderWithPendingSellOrders, candidate);
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

    private void countStocksBought(Map<String, Pair<Integer, List<Candidate>>> countStockBought, Map<String, BuyOrderWithPendingSellOrders> buyOrderWithPendingSellOrders, Candidate candidate) {
        countMarketOrders(countStockBought, buyOrderWithPendingSellOrders.values(), candidate);
    }

    private void countMarketOrders(Map<String, Pair<Integer, List<Candidate>>> countMarketOrders,
                                   Collection<BuyOrderWithPendingSellOrders> marketOrders,
                                   Candidate candidate) {
        marketOrders.stream().forEach(order -> {
            int count = 0;
            List<Candidate> candidates = new ArrayList<Candidate>();
            if (countMarketOrders.containsKey(order.getStockName())) {
                count = countMarketOrders.get(order.getStockName()).getFirst();
                candidates = countMarketOrders.get(order.getStockName()).getSecond();
            }
            candidates.add(candidate);
            countMarketOrders.put(order.getStockName(), new Pair<Integer, List<Candidate>>(count+1, candidates));
        });
    }

    public List<Candidate> candidates() {
        return new CandidatesFileHandler().fromJson();

//        return GlobalStockMarketCandidates.allNewCandidates();
    }

    public List<String> getResultBuffer() {
        return resultBuffer;
    }

    public String runOutput(String separator) {
        return String.join(separator, this.getResultBuffer());
    }

}
