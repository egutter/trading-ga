package com.egutter.trading.runner;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.order.BuyOrderWithPendingSellOrders;
import com.egutter.trading.out.CandidatesFileHandler;
import com.egutter.trading.stock.StockGroup;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;

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
    private List<String> stockSymbols;
    private List<Candidate> candidates;

    public OneDayCandidateRunner(LocalDate fromDate, LocalDate toDate) {
        this(fromDate,
                toDate,
                StockMarket.allSectorsStockSymbols(),
                new CandidatesFileHandler().fromJson("rsi_cross_down_all_candidates.json"));
    }

    public OneDayCandidateRunner(LocalDate fromDate, LocalDate toDate, List<String> stockSymbols, List<Candidate> candidates) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.resultBuffer = new ArrayList<String>();
        this.stockSymbols = stockSymbols;
        this.candidates = candidates;
    }

    public static void main(String[] args) {
        LocalTime startTime = LocalTime.now();
        LocalDate fromDate = new LocalDate(2020, 1, 1);
//        LocalDate toDate = LocalDate.now();
        LocalDate tradeOn = new LocalDate(2021, 6, 11);
        OneDayCandidateRunner runner = new OneDayCandidateRunner(fromDate, tradeOn,
                StockMarket.allSmallMedCapSymbols(),
                new CandidatesFileHandler().fromJson("rsi_cross_down_mid_small_cap_candidates.json"));
        runner.run(tradeOn);
        System.out.println(runner.runOutput("\n"));
        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    public Map<String, List<BuyOrderWithPendingSellOrders>> run(LocalDate tradeOn) {

        Map<String, Pair<Integer, List<Candidate>>> countStockBought = new HashMap<String, Pair<Integer, List<Candidate>>>();

        System.out.println("Generating orders for [ " + stockSymbols.size() + " ] stocks");
        Map<String, List<BuyOrderWithPendingSellOrders>> allBuyOrderWithPendingSellOrders = new HashMap<>();
        stockSymbols.stream().forEach(stockSymbol -> {

            StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, true, true, new String[]{stockSymbol});


            candidates().stream().forEach(candidate -> {

                Optional<StockGroup> candidateStockGroup = candidate.getStockGroups().stream()
                        .filter(candidateGroup -> candidateGroup.containsStockSymbol(stockSymbol))
                        .findAny();
                candidateStockGroup.ifPresent(candidateGroup -> {
                    OneCandidateRunner oneCandidateRunner = OneCandidateRunner.buildRunnerWithCrossOverTriggerFor(stockMarket, candidate.getChromosome());
                    oneCandidateRunner.runOn(tradeOn);

                    Map<String, BuyOrderWithPendingSellOrders> buyOrderWithPendingSellOrders = oneCandidateRunner.getOrderBook().ordersWithPendingOrdersAt(tradeOn);
                    if (!buyOrderWithPendingSellOrders.isEmpty()) {
                        resultBuffer.add("==========================================");
                        resultBuffer.add(candidateGroup.getFullName());
                        resultBuffer.add("New last trading date " + tradeOn);
                        resultBuffer.add(candidateName(candidate));
                        resultBuffer.add(buyOrderWithPendingSellOrders.toString());
                        resultBuffer.add("==========================================");

                        buyOrderWithPendingSellOrders.keySet().stream().forEach(stockName -> {
                            BuyOrderWithPendingSellOrders aBuyOrderWithPendingSells = buyOrderWithPendingSellOrders.get(stockName);
                            aBuyOrderWithPendingSells.setStockGroup(candidateGroup);
                            aBuyOrderWithPendingSells.setCandidate(candidate.toString());
                            if (allBuyOrderWithPendingSellOrders.containsKey(stockName)) {
                                allBuyOrderWithPendingSellOrders.get(stockName).add(aBuyOrderWithPendingSells);
                            } else {
                                List<BuyOrderWithPendingSellOrders> buyOrderWithPendingSellOrderList = new ArrayList<>();
                                buyOrderWithPendingSellOrderList.add(aBuyOrderWithPendingSells);
                                allBuyOrderWithPendingSellOrders.put(stockName, buyOrderWithPendingSellOrderList);
                            }
                        });
                    }
                    countStocksBought(countStockBought, buyOrderWithPendingSellOrders, candidate);
                });
            });
        });

        resultBuffer.add("==========================================");
        resultBuffer.add("ALL STOCKS BOUGHT");
        countStockBought.forEach((stockName, pair) -> {
            resultBuffer.add("Stock " + stockName + " count " + pair.getFirst() + " Candidates: " + candidateListNames(pair.getSecond()));
        });
        resultBuffer.add("==========================================");

        return allBuyOrderWithPendingSellOrders;
    }

    private String candidateName(Candidate candidate) {
        return candidate.toString();
    }

    private String candidateListNames(List<Candidate> candidates) {
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
        return this.candidates;
    }

    public List<String> getResultBuffer() {
        return resultBuffer;
    }

    public String runOutput(String separator) {
        return String.join(separator, this.getResultBuffer());
    }

}
