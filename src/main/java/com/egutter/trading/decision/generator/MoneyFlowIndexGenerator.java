package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.technicalanalysis.MoneyFlowIndex;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import org.uncommons.maths.binary.BitString;

import java.util.Arrays;

/**
 * Created by egutter on 2/12/14.
 */
public class MoneyFlowIndexGenerator extends MomentumOscillatorGenerator<MoneyFlowIndex> {

    public static void main(String[] args) {
        StockPrices stocks = new StockPrices("stock");
        stocks.addAll(Arrays.asList(DailyQuote.empty()));
        BuyTradingDecision stoch = new MoneyFlowIndexGenerator(new BitString("0110101001100")).generateBuyDecision(stocks);
        System.out.println(stoch.buyDecisionToString());
    }
    public MoneyFlowIndexGenerator(BitString chromosome) {
        super(MoneyFlowIndex.class, chromosome);
    }

}
