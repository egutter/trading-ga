package com.egutter.trading.genetic;

import com.egutter.trading.decision.generator.TradingDecisionFactory;
import com.egutter.trading.stock.Trader;
import com.egutter.trading.order.OrderBook;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.Portfolio;
import org.uncommons.maths.binary.BitString;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by egutter on 2/12/14.
 */
public class StockTradingFitnessEvaluator implements FitnessEvaluator<BitString> {

    public static final BigDecimal INITIAL_CASH = new BigDecimal(1000000.00);

    private StockMarket stockMarket;

    public StockTradingFitnessEvaluator(StockMarket stockMarket) {
        this.stockMarket = stockMarket;
    }

    @Override
    public double getFitness(BitString candidate, List<? extends BitString> population) {
        Portfolio portfolio = new Portfolio(INITIAL_CASH);

        if (candidateValidator(tradingDecisionFactory(candidate)).isInvalid()) {
            return 0;
        }

        buildTrader(portfolio, candidate).trade();

        // TODO: Weight portfolio value by a risk profile (e.g. Sortino ratio)
//        return portfolio.getCash().divide(portfolio.getStats().lostOrdersAverageReturn().abs().add(BigDecimal.ONE), RoundingMode.HALF_EVEN).doubleValue();
        return portfolio.getStats().lostOrdersAverageReturn().abs().doubleValue();
    }

    public Trader buildTrader(Portfolio portfolio, BitString candidate) {
        OrderBook orderBook = new OrderBook();

        return new Trader(stockMarket, tradingDecisionFactory(portfolio, candidate), portfolio, orderBook);
    }


    private TradingDecisionFactory tradingDecisionFactory(BitString candidate) {
        return tradingDecisionFactory(new Portfolio(INITIAL_CASH), candidate);
    }

    private TradingDecisionFactory tradingDecisionFactory(Portfolio portfolio, BitString candidate) {
        return new TradingDecisionFactory(portfolio, candidate);
    }

    private GenomeCandidateValidator candidateValidator(TradingDecisionFactory tradingDecisionGenerator) {
        return new GenomeCandidateValidator(tradingDecisionGenerator);
    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
