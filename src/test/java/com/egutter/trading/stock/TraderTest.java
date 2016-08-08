package com.egutter.trading.stock;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.decision.SellTradingDecision;
import com.egutter.trading.decision.factory.GeneticsTradingDecisionFactory;
import com.egutter.trading.decision.factory.TradingDecisionFactory;
import com.egutter.trading.order.OrderBook;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.egutter.trading.helper.TestHelper.aDailyQuote;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 4/9/14.
 */
public class TraderTest {

    private static final BigDecimal INITIAL_CASH = BigDecimal.valueOf(1000000);
    private StockMarket stockMarket;
    private Portfolio portfolio;
    private StockPrices ypfStockPrices = new StockPrices("YPF", asList(aDailyQuote()));
    private double ypfStockClosePrice = aDailyQuote().getAdjustedClosePrice();

    @Before
    public void setUp() throws Exception {
        List<StockPrices> stockPrices = asList(ypfStockPrices);

        StockPrices marketIndex = new StockPrices("MERV", Collections.<DailyQuote>emptyList());

        this.stockMarket = new StockMarket(stockPrices, marketIndex);
        this.portfolio = new Portfolio(INITIAL_CASH);
    }

    @Test
    public void should_generate_one_sell_order() throws Exception {
        OrderBook orderBook = new OrderBook();
        Trader trader = new Trader(stockMarket, alwaysBuyTradingDecisionGenerator(), portfolio, orderBook);

        trader.tradeAllStocksInMarket();

        assertThat(trader.ordersExecuted(), equalTo(1));
    }

    @Test
    public void should_update_porfolio_case_after_selling_one_order() throws Exception {
        Trader trader = new Trader(stockMarket, alwaysBuyTradingDecisionGenerator(), portfolio, new OrderBook());

        trader.tradeAllStocksInMarket();

        int numberOfShares = Trader.AMOUNT_TO_INVEST.divide(BigDecimal.valueOf(ypfStockClosePrice)).intValue();
        double sharesPrice = ypfStockClosePrice * numberOfShares;
        double sellCommision = sharesPrice * Portfolio.COMMISION.doubleValue();
        double amountPaid = sharesPrice + sellCommision;
        assertThat(portfolio.getCash().doubleValue(), equalTo(INITIAL_CASH.doubleValue() - amountPaid));
    }

    private TradingDecisionFactory alwaysBuyTradingDecisionGenerator() {
        return new TestGeneticsTradingDecisionGeneratorBuilder() {
            @Override
            public BuyTradingDecision generateBuyDecision(StockPrices stockPrices) {
                return new BuyTradingDecision() {
                    @Override
                    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
                        return DecisionResult.YES;
                    }

                    @Override
                    public String buyDecisionToString() {
                        return null;
                    }

                    @Override
                    public LocalDate startOn() {
                        return LocalDate.now();
                    }
                };
            }

            @Override
            public SellTradingDecision generateSellDecision(StockPrices stockPrices) {
                return new SellTradingDecision() {
                    @Override
                    public DecisionResult shouldSellOn(LocalDate tradingDate) {
                        return DecisionResult.NO;
                    }

                    @Override
                    public String sellDecisionToString() {
                        return null;
                    }
                };
            }
        };
    }

    private class TestGeneticsTradingDecisionGeneratorBuilder extends GeneticsTradingDecisionFactory {
        public TestGeneticsTradingDecisionGeneratorBuilder() {
            super();
        }
    }
}
