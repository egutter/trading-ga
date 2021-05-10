package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Range;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by egutter on 2/10/14.
 */
public class RelativeStrengthIndex extends MomentumOscillator {

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2016, 1, 01);
        StockMarket stockMarket = new StockMarketBuilder().buildInMemory(fromDate, StockMarket.aapl());
        StockPrices stock = stockMarket.getStockPricesFor("AAPL");
        RelativeStrengthIndex rsi = new RelativeStrengthIndex(stock, Range.atMost(30.0), Range.atLeast(70.0), 14);
        new PrintResult().printIndexValues(stockMarket, rsi);
    }

    public RelativeStrengthIndex(StockPrices stockPrices, Range buyThreshold, Range sellThreshold, Integer days) {
        super(stockPrices, buyThreshold, sellThreshold, days);
    }

    public RelativeStrengthIndex(StockPrices stockPrices, int days) {
        this(stockPrices, Range.atLeast(1.0), Range.atMost(1.0), days);
    }

    public static RelativeStrengthIndex empty(StockPrices stockPrices) {
        return new RelativeStrengthIndex(stockPrices, Range.atLeast(1.0), Range.atMost(1.0), 14);
    }

    @Override
    protected int calculateOscillatorLookback(CoreAnnotated coreAnnotated) {
        return coreAnnotated.rsiLookback(days());
    }

    @Override
    protected RetCode calculateOscillatorValues(List<Double> closePrices, MInteger outBegIdx, MInteger outNBElement, double[] outReal, double[] hiPricesArray, double[] lowPricesArray, double[] closePricesArray, double[] volumeArray, CoreAnnotated coreAnnotated) {
        return coreAnnotated.rsi(startIndex(),
                endIndex(closePrices),
                closePricesArray,
                days(),
                outBegIdx,
                outNBElement,
                outReal);
    }
}
