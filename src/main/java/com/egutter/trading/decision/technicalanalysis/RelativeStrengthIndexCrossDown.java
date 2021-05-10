package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.BiFunction;

public class RelativeStrengthIndexCrossDown extends RelativeStrengthIndex {

    private Double crossDownSignal;

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2020, 1, 01);
        StockMarket stockMarket = new StockMarketBuilder().buildInMemory(fromDate, StockMarket.aapl());
        StockPrices stock = stockMarket.getStockPricesFor("AAPL");
        RelativeStrengthIndexCrossDown rsi = new RelativeStrengthIndexCrossDown(stock, 14, 30.0);
        new PrintResult().printIndexValues(stockMarket, rsi);
    }

    public static RelativeStrengthIndexCrossDown defaultValues(StockPrices stockPrices) {
        return new RelativeStrengthIndexCrossDown(stockPrices, 14, 30.0);
    }

    public RelativeStrengthIndexCrossDown(StockPrices stockPrices, int days, Double crossDownSignal) {
        super(stockPrices, days);
        this.crossDownSignal = crossDownSignal;
    }


    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        return this.hasCrossDown(timeFrameQuote);
    }

    public boolean hasCrossDown(TimeFrameQuote timeFrameQuote) {
        return hasCrossSignal(timeFrameQuote, (previousRsi, currentRsi) -> previousRsi >= crossDownSignal && currentRsi < crossDownSignal);
    }

    private Boolean hasCrossSignal(TimeFrameQuote timeFrameQuote, BiFunction<Double, Double, Boolean> signalFunction) {
        LocalDate previousDate = timeFrameQuote.getQuoteAtPreviousDay().getTradingDate();
        Double previousRsi = getMomentumOscillatorIndex().get(previousDate);

        LocalDate currentDate = timeFrameQuote.getQuoteAtDay().getTradingDate();
        Double currentRsi = getMomentumOscillatorIndex().get(currentDate);

        if (previousRsi == null || currentRsi == null) return false;

        return signalFunction.apply(previousRsi, currentRsi);
    }

    protected RetCode calculateOscillatorValues(List<Double> closePrices, MInteger outBegIdx, MInteger outNBElement, double[] outReal, double[] hiPricesArray, double[] lowPricesArray, double[] closePricesArray, double[] volumeArray, CoreAnnotated coreAnnotated) {
        return coreAnnotated.rsi(startIndex(),
                endIndex(closePrices),
                closePricesArray,
                days(),
                outBegIdx,
                outNBElement,
                outReal);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RelativeStrengthIndexCrossDown.class.getSimpleName() + "[", "]")
                .add("days=" + days)
                .add("crossDownSignal=" + crossDownSignal)
                .toString();
    }
}
