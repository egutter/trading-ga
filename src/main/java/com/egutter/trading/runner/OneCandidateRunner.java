package com.egutter.trading.runner;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.decision.technicalanalysis.MovingAverageConvergenceDivergence;
import com.egutter.trading.decision.technicalanalysis.RelativeStrengthIndex;
import com.egutter.trading.genetic.StockTradingFitnessEvaluator;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.OrderBook;
import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.Trader;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 11/29/14.
 */
public class OneCandidateRunner {

    private final Portfolio portfolio;
    private final Trader trader;
    private final TradingDecisionFactory tradingDecisionFactory;
    private StockMarket stockMarket;
    private final BitString candidate;

    public OneCandidateRunner(StockMarket stockMarket, BitString candidate, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators) {
        this(stockMarket, candidate, new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH), tradingDecisionGenerators, true);
    }

    public OneCandidateRunner(StockMarket stockMarket, BitString candidate, Portfolio portfolio, List<? extends Class<? extends BuyTradingDecisionGenerator>> tradingDecisionGenerators, boolean onExperiment) {
        this.stockMarket = stockMarket;
        this.candidate = candidate;
        this.portfolio = portfolio;
        this.trader = new Trader(stockMarket, new TradingDecisionFactory(portfolio, candidate, tradingDecisionGenerators, onExperiment), portfolio, new OrderBook());
        this.tradingDecisionFactory = new TradingDecisionFactory(new Portfolio(), candidate, tradingDecisionGenerators, onExperiment);
    }

    public void run() {
        trader.trade();
        new PrintResult().print(this, candidate);
    }

    public void runOn(LocalDate tradingDate) {
        trader.tradeOn(tradingDate);
    }

    public BuyTradingDecision buyDecisions() {
        return tradingDecisionFactory.generateBuyDecision(this.stockMarket.getMarketIndexPrices());
    }

    public SellTradingDecision sellDecisions() {
        return tradingDecisionFactory.generateSellDecision(stockMarket.getMarketIndexPrices());
    }

    public BigDecimal finalCash() {
        return this.portfolio.getCash();
    }
    public BigDecimal profit() {
        return this.portfolio.getProfit();
    }

    public List<String> mostPopularFiveStocks() {
        return this.portfolio.getStats().mostPopularStocks(5);
    }

    public int ordersWon() {
        return this.portfolio.getStats().countOrdersWon();
    }

    public int ordersLost() {
        return this.portfolio.getStats().countOrdersLost();
    }

    public int ordersEven() {
        return this.portfolio.getStats().countOrdersEven();
    }

    public BigDecimal percentageOfOrdersWon() {
        return this.portfolio.getStats().percentageOfOrdersWon();
    }

    public BuySellOperation biggestLost() {
        return this.portfolio.getStats().biggestLost();
    }

    public BuySellOperation biggestWin() {
        return this.portfolio.getStats().biggestWin();
    }

    public BigDecimal percentageOfOrdersNotLost() {
        return this.portfolio.getStats().percentageOfOrdersNotLost();
    }

    public Map<LocalDate, Pair<Integer, Integer>> ordersPerDay() {
        return this.portfolio.getStats().ordersPerDay();
    }

    public BigDecimal averageReturn() {
        return portfolio.getStats().allOrdersAverageReturn();
    }

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2013, 1, 1);
        LocalDate toDate = LocalDate.now();

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);

        BitString candidate = new BitString("110011000110001101100100100001");
        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, candidate, asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class));
        runner.run();
    }

    public OrderBook getOrderBook() {
        return trader.getOrderBook();
    }
}
