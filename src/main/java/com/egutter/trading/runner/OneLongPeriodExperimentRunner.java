package com.egutter.trading.runner;

import com.egutter.trading.decision.generator.*;
import com.egutter.trading.genetic.Experiment;
import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.uncommons.maths.binary.BitString;

import java.net.UnknownHostException;
import java.util.List;

import static java.util.Arrays.asList;

public class OneLongPeriodExperimentRunner {


    public static void main(String[] args) throws UnknownHostException {
        LocalTime startTime = LocalTime.now();

        new OneLongPeriodExperimentRunner().run();

        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    private void run() {

        LocalDate fromDate = new LocalDate(2012, 12, 1);
//        LocalDate toDate = new LocalDate(2013, 7, 1);
//        LocalDate fromDate = LocalDate.now().minusMonths(26);
        LocalDate toDate = LocalDate.now();
        System.out.println("Period from " + fromDate + " to " + toDate);

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);

        runOneStrategy(stockMarket, asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class));

        runOneStrategy(stockMarket, asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class));

        runOneStrategy(stockMarket, asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class));

        runOneStrategy(stockMarket, asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class));

        runOneStrategy(stockMarket, asList(MovingAverageConvergenceDivergenceGenerator.class));

        runOneStrategy(stockMarket, asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class));

        runOneStrategy(stockMarket, asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class));

        runOneStrategy(stockMarket, asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class));

        runOneStrategy(stockMarket, asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class));

        runOneStrategy(stockMarket, asList(BollingerBandsGenerator.class));

        runOneStrategy(stockMarket, asList(RelativeStrengthIndexGenerator.class));

        runOneStrategy(stockMarket, asList(MoneyFlowIndexGenerator.class));

        runOneStrategy(stockMarket, asList(AroonOscilatorGenerator.class));
    }

    private void runOneStrategy(StockMarket stockMarket, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        System.out.println("=============================================");

        printResults(stockMarket, new Experiment(tradingDecisionGenerators).run(stockMarket), tradingDecisionGenerators);
    }


    private static void printResults(StockMarket stockMarket, BitString result, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, result, tradingDecisionGenerators);
        runner.run();
    }

}
