package com.egutter.trading.genetic;

import com.egutter.trading.decision.generator.*;
import com.egutter.trading.stock.Trader;
import com.egutter.trading.order.OrderBook;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.Portfolio;
import org.uncommons.maths.binary.BitString;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by egutter on 2/12/14.
 */
public class StockTradingFitnessEvaluator implements FitnessEvaluator<BitString> {

    public static final BigDecimal INITIAL_CASH = new BigDecimal(1000000.00);


    private StockMarket stockMarket;

    private List<? extends Class<? extends TradingDecisionGenerator>> tradingDecisionGenerators;

    public StockTradingFitnessEvaluator(StockMarket stockMarket, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionsGenerators) {
        this.stockMarket = stockMarket;
        this.tradingDecisionGenerators = tradingDecisionsGenerators;

    }

    @Override
    public double getFitness(BitString candidate, List<? extends BitString> population) {
        Portfolio portfolio = new Portfolio(INITIAL_CASH);

        TradingDecisionFactory tradingDecisionFactory = tradingDecisionFactory(portfolio, candidate);
        if (candidateValidator(tradingDecisionFactory).isInvalid()) {
            return 0;
        }

        buildTrader(portfolio, tradingDecisionFactory).trade();

        seeOtherWaysOfEvaluatePortfolioNotUsed();

        if (shouldDiscardCandidate(portfolio)) {
            return 0;
        }
        BigDecimal ordersWonPctgWeight = portfolio.getStats().percentageOfOrdersWon();
        if (ordersWonPctgWeight.compareTo(BigDecimal.valueOf(0.75)) < 0) {
            return 0;
        }
//        BigDecimal ordersWonCountWeight = BigDecimal.valueOf(Math.log10(portfolio.getStats().countOrdersWon()));
//        return portfolio.getCash().multiply(ordersWonCountWeight).doubleValue();
        return portfolio.getCash().doubleValue();
    }

    private boolean shouldDiscardCandidate(Portfolio portfolio) {
        if (portfolio.getCash().doubleValue() <= 0 || portfolio.getStats().countOrdersWon() == 0 || portfolio.getProfit().compareTo(BigDecimal.ZERO) < 1)
            return true;
        return false;
    }

    private void seeOtherWaysOfEvaluatePortfolioNotUsed() {
        // Portfolio cash weight by orders performed on total orders and also weighted by ordersWon divided by orders performed
        // int maxBuyOrders = stockMarket.getStockPrices().size() * stockMarket.getTradingDays().size();
        // BigDecimal weightByOrdersPerformedPctg = BigDecimal.valueOf(portfolio.getStats().countOrdersWon() / maxBuyOrders()).add(BigDecimal.ONE);
        // BigDecimal weightByOrdersWonPctg = portfolio.getStats().percentageOfOrdersWon().add(BigDecimal.ONE);
        // return portfolio.getCash().multiply(weightByOrdersWonPctg).multiply(weightByOrdersPerformedPctg).doubleValue();

        // Weight portfolio value by a risk profile (e.g. Sortino ratio)
        // TODO: Not done

        // Final Portfolio Cash divided by Average Return of Lost Orders
        // return portfolio.getCash().divide(portfolio.getStats().lostOrdersAverageReturn().abs().add(BigDecimal.ONE), RoundingMode.HALF_EVEN).doubleValue();

        // Average Return of Lost Orders
        // return portfolio.getStats().lostOrdersAverageReturn().abs().doubleValue();

    }

    public Trader buildTrader(Portfolio portfolio, TradingDecisionFactory tradingDecisionFactory) {
        return new Trader(stockMarket, tradingDecisionFactory, portfolio, new OrderBook());
    }

    private TradingDecisionFactory tradingDecisionFactory(Portfolio portfolio, BitString candidate) {
        return new TradingDecisionFactory(portfolio, candidate, this.tradingDecisionGenerators, true);
    }

    private GenomeCandidateValidator candidateValidator(TradingDecisionFactory tradingDecisionGenerator) {
        return new GenomeCandidateValidator(tradingDecisionGenerator);
    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
