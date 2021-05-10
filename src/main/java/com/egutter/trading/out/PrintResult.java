package com.egutter.trading.out;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.technicalanalysis.*;
import com.egutter.trading.runner.OneCandidateRunner;
import com.egutter.trading.stats.MetricsRecorderFactory;
import com.egutter.trading.stock.StockMarket;
import com.google.common.collect.Ordering;
import org.joda.time.LocalDate;
import org.uncommons.maths.binary.BitString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by egutter on 11/29/14.
 */
public class PrintResult {

    private boolean printDayDetails;

    public PrintResult() {
        this(false);
    }

    public PrintResult(boolean printDayDetails) {
        this.printDayDetails = printDayDetails;
    }

    public void print(OneCandidateRunner runner, BitString result) {
        System.out.println("Best candidate is " + result);
        TriggerBuyConditionalOrderDecision triggerBuyConditionalOrderDecision = runner.getTriggerBuyConditionalOrderDecisionFactory().generateBuyDecision(runner.getStockMarket().getStockPrices().get(0));
        System.out.println("Best candidate is " + triggerBuyConditionalOrderDecision.buyDecisionToString());
//        System.out.println("Buy Trading Decisions " + runner.buyDecisions());
//        System.out.println("Sell Trading Decisions " + runner.sellDecisions());
        System.out.println("Final Cash $" + runner.finalCash());
        System.out.println("Profit $" + runner.profit());
        System.out.println("Most popular 5 stocks " + runner.mostPopularFiveStocks());
        System.out.println("Num of orders which won " + runner.ordersWon());
        System.out.println("Num of orders which lost " + runner.ordersLost());
        System.out.println("Num of orders which even " + runner.ordersEven());
        System.out.println("Biggest Lost " + runner.biggestLost());
        System.out.println("Biggest Win " + runner.biggestWin());

        System.out.println("Percentage of orders which won " + runner.percentageOfOrdersWon());
        System.out.println("Percentage of orders which didn't loose " + runner.percentageOfOrdersNotLost());
        System.out.println("Order return average " + runner.averageReturn());

        if (this.printDayDetails) {
//            Map<LocalDate, Pair<Integer, Integer>> ordersPerDay = runner.ordersPerDay();
//            Ordering.natural().sortedCopy(ordersPerDay.keySet()).stream().forEachOrdered(day -> {
//                System.out.println("On " + day +
//                        " bought " + ordersPerDay.get(day).getFirst() +
//                        " sold " + ordersPerDay.get(day).getSecond());
//            });

            System.out.println("ALL ORDERS");
            runner.allOrders().forEach(order -> System.out.println(order));
        }

        System.out.println("METRICS");
        System.out.println(MetricsRecorderFactory.getInstance().getMetricsAsString());


    }

    public void printIndexValues(StockMarket stockMarket, MomentumOscillator oscillator) {
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        stockMarket.getStockPrices().get(0).getDailyQuotes().forEach(quote -> {
            DecisionResult result = oscillator.shouldBuyOn(quote.getTradingDate());
            System.out.println(quote.getTradingDate() + " = " + result);
        });
        Map<LocalDate, Double> indexes = oscillator.getMomentumOscillatorIndex();
        for (LocalDate aDate : Ordering.natural().sortedCopy(indexes.keySet())) {
            System.out.println("Oscillator value " + aDate + " = " + indexes.get(aDate));
        }
        maxes.add(Ordering.natural().max(indexes.values()));
        minis.add(Ordering.natural().min(indexes.values()));
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
    }

    public void printIndexValues(StockMarket stockMarket, CrossOverOscillator oscillator) {
        List<Double> maxes = new ArrayList<Double>();
        List<Double> minis = new ArrayList<Double>();
        stockMarket.getStockPrices().get(0).getDailyQuotes().forEach(quote -> {
            DecisionResult result = oscillator.shouldBuyOn(quote.getTradingDate());
            System.out.println(quote.getTradingDate() + " = " + result);
        });
        Map<LocalDate, Double> indexes = oscillator.getIndexValues();
        for (LocalDate aDate : Ordering.natural().sortedCopy(indexes.keySet())) {
            System.out.println("Oscillator index value " + aDate + " = " + indexes.get(aDate) + " | signal " + oscillator.getSignalValue(aDate));
        }
        maxes.add(Ordering.natural().max(indexes.values()));
        minis.add(Ordering.natural().min(indexes.values()));
        System.out.println("Max value " + Ordering.natural().max(maxes));
        System.out.println("Min value " + Ordering.natural().min(minis));
    }
}
