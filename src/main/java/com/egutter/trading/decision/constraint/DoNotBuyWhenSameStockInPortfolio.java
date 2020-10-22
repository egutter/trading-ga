package com.egutter.trading.decision.constraint;

import com.egutter.trading.decision.BuyTradingDecision;
import com.egutter.trading.decision.DecisionResult;
import com.egutter.trading.stats.MetricsRecorder;
import com.egutter.trading.stats.MetricsRecorderFactory;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import com.egutter.trading.stock.TimeFrameQuote;
import org.joda.time.LocalDate;

import java.util.function.Function;

/**
 * Created by egutter on 2/12/14.
 */
public class DoNotBuyWhenSameStockInPortfolio implements BuyTradingDecision, Function<TimeFrameQuote, Boolean> {


    private Portfolio portfolio;
    private StockPrices stockPrices;

    public DoNotBuyWhenSameStockInPortfolio(Portfolio portfolio,
                                            StockPrices stockPrices) {
        this.portfolio = portfolio;
        this.stockPrices = stockPrices;
    }


    @Override
    public Boolean apply(TimeFrameQuote timeFrameQuote) {
        if (portfolio.hasStock(stockPrices.getStockName())) {
            MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.HAS_SAME_STOCK_IN_PORTFOLIO);
            return false;
        }
        MetricsRecorderFactory.getInstance().incEvent(MetricsRecorder.HAS_NOT_SAME_STOCK_IN_PORTFOLIO);
        return true;
    }

    @Override
    public DecisionResult shouldBuyOn(LocalDate tradingDate) {
        if (portfolio.hasStock(stockPrices.getStockName())) {
            return DecisionResult.NO;
        }
        return DecisionResult.NEUTRAL;
    }

    @Override
    public String buyDecisionToString() {
        return null;
    }

    @Override
    public LocalDate startOn() {
        return LocalDate.now();
    }

}
