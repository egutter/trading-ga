package com.egutter.trading.runner;

import com.egutter.trading.decision.generator.TradingDecisionFactory;
import com.egutter.trading.genetic.StockTradingFitnessEvaluator;
import com.egutter.trading.genetic.TradingDecisionGenome;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.Trader;
import com.mongodb.*;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.uncommons.maths.binary.BitString;
import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.factories.BitStringFactory;
import org.uncommons.watchmaker.framework.islands.IslandEvolution;
import org.uncommons.watchmaker.framework.islands.RingMigration;
import org.uncommons.watchmaker.framework.operators.BitStringCrossover;
import org.uncommons.watchmaker.framework.operators.BitStringMutation;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.Stagnation;

import java.net.UnknownHostException;
import java.util.*;

public class Copy {


    public static void main(String[] args) throws UnknownHostException {
        LocalTime startTime = LocalTime.now();

        List<Pair<LocalDate, LocalDate>> years = new ArrayList<Pair<LocalDate, LocalDate>>();
//        years.add(new Pair(new LocalDate(2012, 1, 1), new LocalDate(2012, 4, 30)));
//        years.add(new Pair(new LocalDate(2012, 5, 1), new LocalDate(2012, 8, 31)));
//        years.add(new Pair(new LocalDate(2012, 9, 1), new LocalDate(2012, 12, 31)));
//
//        years.add(new Pair(new LocalDate(2013, 1, 1), new LocalDate(2013, 4, 30)));
//        years.add(new Pair(new LocalDate(2013, 5, 1), new LocalDate(2013, 8, 31)));
//        years.add(new Pair(new LocalDate(2013, 9, 1), new LocalDate(2013, 12, 31)));
//        years.add(new Pair(new LocalDate(2012, 1, 1), new LocalDate(2012, 12, 31)));
//        years.add(new Pair(new LocalDate(2013, 1, 1), new LocalDate(2013, 12, 31)));
        years.add(new Pair(new LocalDate(2013, 1, 1), new LocalDate(2014, 12, 31)));

        List<BitString> winners = new ArrayList<BitString>();
        for (Pair<LocalDate, LocalDate> quarter : years) {
            System.out.println("Quarter "+ quarter.getFirst() + " - " + quarter.getSecond());

            StockMarket stockMarket = new StockMarketBuilder().build(quarter.getFirst(), quarter.getSecond());
            StockTradingFitnessEvaluator stockTradingFitnessEvaluator = new StockTradingFitnessEvaluator(stockMarket);

            Copy main = new Copy();

//            BitString result1 = runner.run(stockTradingFitnessEvaluator, new TournamentSelection(new Probability(0.51)), true);
//            winners.add(result1);

//            printResults(startTime, stockMarket, stockTradingFitnessEvaluator, result1);

            BitString result2 = main.run(stockTradingFitnessEvaluator, new RouletteWheelSelection(), false);
            winners.add(result2);
//            printResults(startTime, stockMarket, stockTradingFitnessEvaluator, result2);
        }

        System.out.println("Running winners from every quarter");
        for (BitString winnerResult : winners) {
            StockMarket stockMarket = new StockMarketBuilder().build(new LocalDate(2014, 1, 1), LocalDate.now());
            StockTradingFitnessEvaluator stockTradingFitnessEvaluator = new StockTradingFitnessEvaluator(stockMarket);
            printResults(stockMarket, stockTradingFitnessEvaluator, winnerResult);
        }

//        System.out.println("Running winners from past queries");
//        List<BitString> pastWinners = Arrays.asList(new BitString("01100110110011011101010110100001110"), new BitString("01111110110011011101010110100001110"), new BitString("11011000110011011101010110100011111"));
//        for (BitString winnerResult : pastWinners) {
//            StockMarket stockMarket = new StockMarketBuilder().build(new LocalDate(2014, 1, 1), LocalDate.now());
//            StockTradingFitnessEvaluator stockTradingFitnessEvaluator = new StockTradingFitnessEvaluator(stockMarket);
//            printResults(startTime, stockMarket, stockTradingFitnessEvaluator, winnerResult);
//        }

        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    public Copy() {
    }

    public BitString run(StockTradingFitnessEvaluator stockTradingFitnessEvaluator, SelectionStrategy<Object> selectionStrategy, boolean islandEvolution) {

        CandidateFactory<BitString> candidateFactory = new BitStringFactory(TradingDecisionGenome.SIZE);

        List<EvolutionaryOperator<BitString>> operators
                = new LinkedList<EvolutionaryOperator<BitString>>();
        operators.add(new BitStringCrossover());
        operators.add(new BitStringMutation(new ConstantGenerator<Probability>(new Probability(0.02)),
                new ConstantGenerator<Integer>(1)));

        EvolutionaryOperator<BitString> pipeline = new EvolutionPipeline<BitString>(operators);

        Random rng = new MersenneTwisterRNG();

        if (islandEvolution) {
            IslandEvolution<BitString> engine
                    = new IslandEvolution<BitString>(5, // Number of islands.
                    new RingMigration(),
                    candidateFactory,
                    pipeline,
                    new CachingFitnessEvaluator(stockTradingFitnessEvaluator),
                    selectionStrategy,
                    rng);

//            IslandEvolutionObserver<? super BitString> observer = new TraderEvolutionObserver();
//            engine.addEvolutionObserver(observer);

            return engine.evolve(100, // Population size per island.
                    5, // Elitism for each island.
                    20, // Epoch length (no. generations).
                    3, // Migrations from each island at each epoch.
                    new GenerationCount(10), new Stagnation(5, true));
        } else {
            EvolutionEngine<BitString> engine
                    = new GenerationalEvolutionEngine<BitString>(candidateFactory,
                    pipeline,
                    new CachingFitnessEvaluator(stockTradingFitnessEvaluator),
                    selectionStrategy,
                    rng);

            return engine.evolve(1000, 10, new GenerationCount(100), new Stagnation(20, true));
        }


    }

    private static void printResults(StockMarket stockMarket, StockTradingFitnessEvaluator stockTradingFitnessEvaluator, BitString result) {
        Portfolio portfolio = new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH);
        Trader trader = stockTradingFitnessEvaluator.buildTrader(portfolio, new TradingDecisionFactory(portfolio, result));
        trader.trade();

        System.out.println(result);
        System.out.println("Buy Trading Decisions "+ new TradingDecisionFactory(new Portfolio(), result).generateBuyDecision(stockMarket.getMarketIndexPrices()));
        System.out.println("Sell Trading Decisions "+ new TradingDecisionFactory(new Portfolio(), result).generateSellDecision(stockMarket.getMarketIndexPrices()));
        System.out.println("Final Cash $"+ portfolio.getCash());
        System.out.println("Most popular 5 stocks "+ portfolio.getStats().mostPopularStocks(5));
        System.out.println("Num of orders which won "+ portfolio.getStats().countOrdersWon());
        System.out.println("Num of orders which lost "+ portfolio.getStats().countOrdersLost());
        System.out.println("Num of orders which even "+ portfolio.getStats().countOrdersEven());
        System.out.println("Percentage of orders which won "+ portfolio.getStats().percentageOfOrdersWon());
        System.out.println("Order return average "+ portfolio.getStats().allOrdersAverageReturn());
    }

    public static void main2(String[] args) throws UnknownHostException {


        Mongo client = new Mongo();
        DB db = client.getDB("merval-stats");
        Set<String> colls = db.getCollectionNames();

//        for (String name : colls) {
//            DBCollection coll = db.getCollection(name);
//            DBObject first = coll.findOne();
//            System.out.println("name " + name + " date: "+ first.get("date"));
//        }

        List<Double> closePrices = new ArrayList<Double>();
        DBCollection coll = db.getCollection("YPFD.BA");
        DBCursor cursor = coll.find();
        try {
            while(cursor.hasNext()) {
                DBObject object = cursor.next();
                closePrices.add(Double.valueOf(((String) object.get("adjusted_close"))));
                LocalDate date = LocalDate.fromDateFields((Date) object.get("date"));
                System.out.println(date);
            }
        } finally {
            cursor.close();
        }
/*
        StockPrices stockPrices = new StockPrices(coll.getName(), closePrices);
        BuyTradingDecision tradingDecision = new BollingerBands(stockPrices,
                Range.atMost(0.2),
                Range.atLeast(0.8), 20, MAType.Sma);

        for (int dayNumber = 0; dayNumber < closePrices.size(); dayNumber++) {
            System.out.println("Day "+ dayNumber + " Close Price "+ closePrices.get(dayNumber) + " should buy?" + tradingDecision.shouldBuyOn(dayNumber) + " should sell? " + tradingDecision.shouldSellOn(dayNumber));
        }
*/
//        MInteger outBegIdx = new MInteger();
//        MInteger outNBElement = new MInteger();
//        double outRealUpperBand[] = new double[closePrices.size()];
//        double outRealMiddleBand[] = new double[closePrices.size()];
//        double outRealLowerBand[] = new double[closePrices.size()];
//        double[] closePricesArray = Doubles.toArray(closePrices);
//        RetCode returnCode = new CoreAnnotated().bbands(0,
//                closePrices.size() - 1,
//                closePricesArray, 20, 2, 2,
//                MAType.Sma,
//                outBegIdx,
//                outNBElement,
//                outRealUpperBand,
//                outRealMiddleBand,
//                outRealLowerBand);
//
//        List<Double> percentageBs = new ArrayList<Double>();
//        for (int i = 0; i < outNBElement.value; i++) {
//            double pctB = (closePricesArray[i]-outRealLowerBand[i])/(outRealUpperBand[i]-outRealLowerBand[i]);
//            percentageBs.addBuyTradingDecision(pctB);
//        }
//        System.out.println("outBegIdx is "+outBegIdx.value + " and outNBElement "+ outNBElement.value);
//        System.out.println("Prices are "+Doubles.join(", ", closePricesArray));
//
//        System.out.println("RetCode is "+returnCode.toString());
//        System.out.println("outRealUpperBand is "+Doubles.join(", ", outRealUpperBand));
//        System.out.println("outRealMiddleBand is "+Doubles.join(", ", outRealMiddleBand));
//        System.out.println("outRealLowerBand is "+Doubles.join(", ", outRealLowerBand));
//        System.out.println("PercentageB is "+Doubles.join(", ", Doubles.toArray(percentageBs)));

    }
}
