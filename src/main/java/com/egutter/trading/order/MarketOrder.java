package com.egutter.trading.order;

import com.egutter.trading.stock.StockPortfolio;

/**
 * Created by egutter on 2/12/14.
 */
public interface MarketOrder {
    void execute(StockPortfolio stockPortfolio);
}
