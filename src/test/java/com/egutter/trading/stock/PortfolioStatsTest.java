package com.egutter.trading.stock;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.SellOrder;
import org.junit.Test;

import java.math.BigDecimal;

import static com.egutter.trading.helper.TestHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by egutter on 5/19/14.
 */
public class PortfolioStatsTest {

    private PortfolioStats stats = new PortfolioStats();

    @Test
    public void percentage_of_orders_won_should_be_50_percent() throws Exception {
        addStatsForWonOperation();
        addStatsForWonOperation();
        addStatsForLostOperation();
        addStatsForLostOperation();


        assertThat(stats.percentageOfOrdersWon(), equalTo(BigDecimal.valueOf(50, 2)));
    }

    private void addStatsForLostOperation() {
        BuyOrder buyOrder = new BuyOrder(aStockName(), aDailyQuote(15), twoThousandPesos());
        int sharesToSell = 100;
        SellOrder sellOrder = new SellOrder(aStockName(), aDailyQuote(10), sharesToSell);

        PortfolioAsset portfolioAsset = new PortfolioAsset(buyOrder.getNumberOfShares(), buyOrder);
        stats.addStatsFor(portfolioAsset, sellOrder);
    }

    private void addStatsForWonOperation() {
        BuyOrder buyOrder = new BuyOrder(aStockName(), aDailyQuote(10), oneThousandPesos());
        int sharesToSell = 100;
        SellOrder sellOrder = new SellOrder(aStockName(), aDailyQuote(15), sharesToSell);

        PortfolioAsset portfolioAsset = new PortfolioAsset(buyOrder.getNumberOfShares(), buyOrder);
        stats.addStatsFor(portfolioAsset, sellOrder);
    }
}
