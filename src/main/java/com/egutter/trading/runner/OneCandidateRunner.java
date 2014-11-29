package com.egutter.trading.runner;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.generator.TradingDecisionFactory;
import com.egutter.trading.genetic.StockTradingFitnessEvaluator;
import com.egutter.trading.order.OrderBook;
import com.egutter.trading.out.PrintResult;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.Trader;
import org.joda.time.LocalDate;
import org.uncommons.maths.binary.BitString;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by egutter on 11/29/14.
 */
public class OneCandidateRunner {

    private final Portfolio portfolio;
    private final Trader trader;
    private final TradingDecisionFactory tradingDecisionFactory;
    private StockMarket stockMarket;

    public OneCandidateRunner(StockMarket stockMarket, BitString candidate) {
        this.stockMarket = stockMarket;
        this.portfolio = new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH);
        this.trader = new Trader(stockMarket, new TradingDecisionFactory(portfolio, candidate), portfolio, new OrderBook());
        this.tradingDecisionFactory = new TradingDecisionFactory(new Portfolio(), candidate);
    }

    public void run() {
        trader.trade();
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

    public BigDecimal averageReturn() {
        return portfolio.getStats().allOrdersAverageReturn();
    }

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2013, 1, 1);
        LocalDate toDate = new LocalDate(2015, 1, 1);

        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);

        BitString candidate = new BitString("101110111110101110001010000001");
        OneCandidateRunner runner = new OneCandidateRunner(stockMarket, candidate);
        runner.run();

        new PrintResult().print(runner, candidate);
    }

}
