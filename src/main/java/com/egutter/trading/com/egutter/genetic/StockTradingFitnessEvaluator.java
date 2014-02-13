package com.egutter.trading.com.egutter.genetic;

import com.egutter.trading.Trader;
import com.egutter.trading.decision.TradingDecision;
import com.egutter.trading.decision.generator.TradingDecisionGenerator;
import com.egutter.trading.order.MarketOrder;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockPortfolio;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by egutter on 2/12/14.
 */
public class StockTradingFitnessEvaluator implements FitnessEvaluator<TradingDecision> {

    private static final BigDecimal INITIAL_CASH = new BigDecimal(1000000.00);

    private StockMarket stockMarket;

    @Override
    public double getFitness(TradingDecision candidate, List<? extends TradingDecision> population) {
        StockPortfolio stockPortfolio = new StockPortfolio(INITIAL_CASH);
        List<MarketOrder> orderBook = new ArrayList<MarketOrder>();

        Trader trader = new Trader(stockMarket, new TradingDecisionGenerator(), stockPortfolio, orderBook);
        trader.trade();

        // TODO: Weight portfolio value by a risk profile (e.g. Sortino ratio)
        return stockPortfolio.getCash().doubleValue();
    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
