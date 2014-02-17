package com.egutter.trading;

import com.egutter.trading.decision.BollingerBands;
import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.genetic.StockTradingFitnessEvaluator;
import com.egutter.trading.genetic.TradingDecisionGenome;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Range;
import com.mongodb.*;
import com.tictactec.ta.lib.MAType;
import org.joda.time.LocalDate;
import org.uncommons.maths.binary.BitString;
import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.factories.BitStringFactory;
import org.uncommons.watchmaker.framework.operators.BitStringCrossover;
import org.uncommons.watchmaker.framework.operators.BitStringMutation;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

import java.net.UnknownHostException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
        CandidateFactory<BitString> candidateFactory = new BitStringFactory(TradingDecisionGenome.SIZE);

        List<EvolutionaryOperator<BitString>> operators
                = new LinkedList<EvolutionaryOperator<BitString>>();
        operators.add(new BitStringCrossover());
        operators.add(new BitStringMutation(new ConstantGenerator<Probability>(new Probability(0.02)),
                new ConstantGenerator<Integer>(1)));
        EvolutionaryOperator<BitString> pipeline = new EvolutionPipeline<BitString>(operators);

        StockMarket stockMarket = new StockMarket(null, null);

        SelectionStrategy<Object> selectionStrategy = new RouletteWheelSelection();

        Random rng = new MersenneTwisterRNG();
        EvolutionEngine<BitString> engine
                = new GenerationalEvolutionEngine<BitString>(candidateFactory,
                pipeline,
                new StockTradingFitnessEvaluator(stockMarket),
                selectionStrategy,
                rng);

        BitString result = engine.evolve(100, 1, new GenerationCount(10));
        System.out.println(result);
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
        TradingDecision tradingDecision = new BollingerBands(stockPrices,
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
//            percentageBs.add(pctB);
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
