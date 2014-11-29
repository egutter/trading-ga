package com.egutter.trading.runner;

import com.egutter.trading.decision.generator.TradingDecisionFactory;
import com.egutter.trading.genetic.Experiment;
import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.uncommons.maths.binary.BitString;

import java.net.UnknownHostException;

public class OneLongPeriodExperimentRunner {


    public static void main(String[] args) throws UnknownHostException {
        LocalTime startTime = LocalTime.now();

        new OneLongPeriodExperimentRunner().run();

        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    private void run() {

        LocalDate fromDate = new LocalDate(2013, 1, 1);
        LocalDate toDate = new LocalDate(2013, 7, 1);
//        LocalDate toDate = new LocalDate(2014, 12, 31);
        System.out.println("Period from " + fromDate + " to" + toDate);

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);
        BitString result = new Experiment().run(stockMarket);

        printResults(stockMarket, result);

    }


    private static void printResults(StockMarket stockMarket, BitString result) {
        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, result);
        runner.run();
        new PrintResult().print(runner, result);
    }

}
