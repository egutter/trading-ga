package com.egutter.trading.stock;

import com.egutter.trading.genetic.StockTradingFitnessEvaluator;
import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.repository.PortfolioRepository;

/**
 * Created by egutter on 1/1/15.
 */
public class PortfolioBuilder {

    private PortfolioRepository portfolioRepository;

    public PortfolioBuilder(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public Portfolio build(String key) {
        Portfolio portfolio = new Portfolio(StockTradingFitnessEvaluator.INITIAL_CASH);
        portfolioRepository.forEachStockOn(key, (BuyOrder aBuyOrder) -> {
            portfolio.buyStock(aBuyOrder.getStockName(), aBuyOrder.amountPaid(), aBuyOrder);
        });
        portfolioRepository.forEachStatOn(key, (BuySellOperation buySellOperation) -> {
            portfolio.getStats().addStatsFor(new PortfolioAsset(buySellOperation.getBuyOrder().getNumberOfShares(), buySellOperation.getBuyOrder()), buySellOperation.getSellOrder());
        });
        return portfolio;
    }
}
