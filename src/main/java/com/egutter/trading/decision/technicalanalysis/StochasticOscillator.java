package com.egutter.trading.decision.technicalanalysis;

import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import com.google.common.primitives.Doubles;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class StochasticOscillator extends CrossOverOscillator {

    private final int fastKPeriod;
    private final int slowKPeriod;
    private final int slowDPeriod;
    private Map<LocalDate, Double> kValues = new HashMap<LocalDate, Double>();
    private Map<LocalDate, Double> dValues = new HashMap<LocalDate, Double>();
    private MAType slowKMaType;
    private MAType slowDMaType;

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2020, 1, 01);
        StockMarket stockMarket = new StockMarketBuilder().buildInMemory(fromDate, new String[] {"MA"});
        StockPrices stock = stockMarket.getStockPricesFor("MA");
        StochasticOscillator oscillator = new StochasticOscillator(stock,
                17, 6, 6, MAType.Ema, MAType.Sma);
        new PrintResult().printIndexValues(stockMarket, oscillator);

    }

    public StochasticOscillator(StockPrices stockPrices,
                                int fastKDays,
                                int slowKDays,
                                int slowDPeriod,
                                MAType slowKMaType,
                                MAType slowDMaType) {
        super(stockPrices);
        this.fastKPeriod = fastKDays;
        this.slowKPeriod = slowKDays;
        this.slowDPeriod = slowDPeriod;
        this.slowKMaType = slowKMaType;
        this.slowDMaType = slowDMaType;
        calculateOscillator();
    }

    @Override
    public Map<LocalDate, Double> getIndexValues() {
        return this.kValues;
    }

    @Override
    public Map<LocalDate, Double> getSignalValues() {
        return this.dValues;
    }

    private void calculateOscillator() {
        List<Double> closePrices = stockPrices.getClosePrices();
        List<Double> hiPrices = stockPrices.getHighPrices();
        List<Double> lowPrices = stockPrices.getLowPrices();

        MInteger outBegIdx = new MInteger();
        MInteger outNBElement = new MInteger();
        double outSlowK[] = new double[closePrices.size()];
        double outSlowD[] = new double[closePrices.size()];
        double outMACDHist[] = new double[closePrices.size()];
        double[] hiPricesArray = Doubles.toArray(hiPrices);
        double[] lowPricesArray = Doubles.toArray(lowPrices);
        double[] closePricesArray = Doubles.toArray(closePrices);

        CoreAnnotated coreAnnotated = new CoreAnnotated();
        RetCode returnCode = coreAnnotated.stoch(
                startIndex(),
                endIndex(closePrices),
                hiPricesArray,
                lowPricesArray,
                closePricesArray,
                this.fastKPeriod,
                this.slowKPeriod,
                this.slowKMaType,
                this.slowDPeriod,
                this.slowDMaType,
                outBegIdx,
                outNBElement,
                outSlowK,
                outSlowD);

        if (!returnCode.equals(RetCode.Success)) {
            throw new RuntimeException("Error calculating Stochastic Oscillator " + returnCode + " with decision: " + this.sellDecisionToString() + " " + this.buyDecisionToString() );
        }

        List<LocalDate> tradingDates = stockPrices.getTradingDates();
        int lookBack = coreAnnotated.stochLookback(this.fastKPeriod, this.slowKPeriod, this.slowKMaType, this.slowDPeriod, this.slowDMaType);
        if (lookBack < tradingDates.size()) this.startOnDate = tradingDates.get(lookBack);
        for (int i = 0; i < outNBElement.value; i++) {
            int offset = i + lookBack;
            LocalDate tradingDate = tradingDates.get(offset);
            this.kValues.put(tradingDate, outSlowK[i]);
            this.dValues.put(tradingDate, outSlowD[i]);
        }
    }

    private int endIndex(List<Double> closePrices) {
        return closePrices.size() - 1;
    }

    private int startIndex() {
        return 0;
    }

    @Override
    public String buyDecisionToString() {
        return decisionToString();
    }

    @Override
    public String sellDecisionToString() {
        return decisionToString();
    }

    private String decisionToString() {
        return Joiner.on(": ").join(this.getClass().getSimpleName(),
                "Fast K days",
                this.fastKPeriod,
                "Slow K days",
                this.slowKPeriod,
                "Slow D days",
                this.slowDPeriod,
                "Slow K MAType",
                this.slowKMaType,
                "Slow D MAType",
                this.slowDMaType);
    }
}
