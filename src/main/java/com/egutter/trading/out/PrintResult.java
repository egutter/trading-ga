package com.egutter.trading.out;

import com.egutter.trading.runner.OneCandidateRunner;
import com.google.common.collect.Ordering;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
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
        System.out.println("Buy Trading Decisions " + runner.buyDecisions());
        System.out.println("Sell Trading Decisions " + runner.sellDecisions());
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
            Map<LocalDate, Pair<Integer, Integer>> ordersPerDay = runner.ordersPerDay();
            Ordering.natural().sortedCopy(ordersPerDay.keySet()).stream().forEachOrdered(day -> {
                System.out.println("On " + day + " bought " + ordersPerDay.get(day).getFirst() + " sold " + ordersPerDay.get(day).getSecond());
            });
        }
    }
}
