package com.egutter.trading.decision.generator;

import com.egutter.trading.decision.BollingerBands;
import com.egutter.trading.decision.DoNotBuyWhenSameStockInPortfolio;
import com.egutter.trading.decision.DoNotSellWhenNoStockInPorfolio;
import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.stock.StockPortfolio;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Range;
import com.tictactec.ta.lib.MAType;

/**
 * Created by egutter on 2/12/14.
 */
public class TradingDecisionGenerator {
    private StockPortfolio portfolio;

    public TradingDecision generate(StockPrices stockPrices) {
        // generate trading decisions from genome

        BollingerBands bollingerBands = new BollingerBands(stockPrices,
                Range.atMost(0.1),
                Range.atLeast(0.9),
                20,
                MAType.Sma);

        DoNotBuyWhenSameStockInPortfolio doNotBuyWhenSameStockInPortfolio = new DoNotBuyWhenSameStockInPortfolio(portfolio,
                stockPrices,
                bollingerBands);

        return new DoNotSellWhenNoStockInPorfolio(portfolio, stockPrices, doNotBuyWhenSameStockInPortfolio);
    }
}
