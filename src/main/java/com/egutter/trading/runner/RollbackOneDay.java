package com.egutter.trading.runner;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.repository.HistoricPriceRepository;
import com.egutter.trading.repository.PortfolioRepository;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.PortfolioBuilder;
import org.joda.time.LocalDate;

/**
 * Created by egutter on 1/17/15.
 */
public class RollbackOneDay {

    private LocalDate dayToRollback;
    private final PortfolioRepository portfolioRepository;
    private final HistoricPriceRepository historyRepository;

    public RollbackOneDay(LocalDate dayToRollback) {
        this.dayToRollback = dayToRollback;
        this.portfolioRepository = new PortfolioRepository();
        this.historyRepository = new HistoricPriceRepository();
    }

    public static void main(String[] args) {
        LocalDate dayToRollback = new LocalDate(2015, 3, 4);
        System.out.println("Rollback " + dayToRollback);
        new RollbackOneDay(dayToRollback).rollback();
        System.out.println("Rollback FINISHED");
    }

    private void rollback() {
        rollbackPortfolio();
        rollbackStats();
        rollbackPrices();
    }

    private void rollbackPrices() {
        historyRepository.removeAllAt(dayToRollback);
    }

    private void rollbackStats() {
        portfolioRepository.forEachStatCollection(key -> {
            portfolioRepository.forEachStat(key, buySellOperation -> {
                if (buySellOperation.getSellOrder().getTradingDate().equals(dayToRollback)) {
                    Portfolio portfolio = new PortfolioBuilder(portfolioRepository).build(key);
                    BuyOrder buyOrder = buySellOperation.getBuyOrder();
                    portfolio.buyStock(buyOrder.getStockName(), buyOrder.amountPaid(), buyOrder);
                    portfolio.getStats().removeStat(buySellOperation);
                    portfolioRepository.update(key, portfolio);
                }
            });
        });
        portfolioRepository.removeAllStatsSoldAt(dayToRollback);
    }

    private void rollbackPortfolio() {
        portfolioRepository.removeAllStocksAt(dayToRollback);
    }


}
