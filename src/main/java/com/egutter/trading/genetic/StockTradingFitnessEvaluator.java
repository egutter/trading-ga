package com.egutter.trading.genetic;

import com.egutter.trading.decision.factory.GeneticsTradingDecisionFactory;
import com.egutter.trading.decision.factory.TradingDecisionFactory;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.stats.CandidateRanker;
import com.egutter.trading.stats.CandidateStats;
import com.egutter.trading.stock.PortfolioStats;
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
        return getFitness(candidate);
    }

    public double getFitness(BitString candidate) {
        Portfolio portfolio = new Portfolio(INITIAL_CASH);

        GeneticsTradingDecisionFactory tradingDecisionFactory = tradingDecisionFactory(portfolio, candidate);
        if (candidateValidator(tradingDecisionFactory).isInvalid()) {
            return 0;
        }

        buildTrader(portfolio, tradingDecisionFactory).tradeAllStocksInMarket();

        seeOtherWaysOfEvaluatePortfolioNotUsed();

        if (shouldDiscardCandidate(portfolio)) {
            return 0;
        }
        CandidateRanker candidateRanker = new CandidateRanker(null);
        PortfolioStats porfolioStats = portfolio.getStats();
        CandidateStats stats = new CandidateStats(porfolioStats.average30daysReturn(),
                porfolioStats.biggestLost().profitPctg30(),
                porfolioStats.countOrdersWon(),
                porfolioStats.countOrdersLost());

//        if (!candidateRanker.rank(stats).isHighRank()) return 0;
//        if (discardWhenBellowMarket(portfolio, 0.9)) return 0;

        if (discardWhenOrdersLost(portfolio)) return 0;

//        Fitness by Cash
//        return portfolio.getCash().doubleValue();

//      Fitness Cash weighted by Log10(Orders Won)
//        BigDecimal ordersWonCountWeight = BigDecimal.valueOf(Math.log10(portfolio.getStats().countOrdersWon()));
//        return portfolio.getCash().multiply(ordersWonCountWeight).doubleValue();

//      Fitness Cash weighted by Orders Won
        BigDecimal ordersWonCountWeight = BigDecimal.valueOf(porfolioStats.countOrdersWon());
        return portfolio.getCash().multiply(ordersWonCountWeight).doubleValue();
    }

    private boolean discardWhenOrdersLost(Portfolio portfolio) {
        return (portfolio.getStats().countOrdersLost() > 0);
    }

    private boolean discardWhenBellowMarket(Portfolio portfolio, double requiredPctgAboveMarket) {
        BigDecimal ordersWonPctgWeight = portfolio.getStats().percentageOfOrdersAboveMarket();
        if (ordersWonPctgWeight.compareTo(BigDecimal.valueOf(requiredPctgAboveMarket)) < 0) {
            return true;
        }
        return false;
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

    private GeneticsTradingDecisionFactory tradingDecisionFactory(Portfolio portfolio, BitString candidate) {
        return new GeneticsTradingDecisionFactory(portfolio, candidate, this.tradingDecisionGenerators, false);
    }

    private GenomeCandidateValidator candidateValidator(GeneticsTradingDecisionFactory tradingDecisionGenerator) {
        return new GenomeCandidateValidator(tradingDecisionGenerator);
    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
