package com.egutter.trading.runner;

import com.egutter.trading.decision.constraint.TrailingStopSellDecision;
import com.egutter.trading.order.*;
import com.egutter.trading.out.BuyBracketOrdersFileHandler;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockGroup;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

public class DailyTraderTest {

    private DailyTrader dailyTrader = new DailyTrader(BigDecimal.ZERO);

    @Test
    public void shouldWriteFileWithBestOrders() {
        BuyOrderWithPendingSellOrders orderOne = buildBuyOrderWithPendingSellOrders("NET", 84.360001, BigDecimal.valueOf(1.00), 1);
        BuyOrderWithPendingSellOrders orderTwo = buildBuyOrderWithPendingSellOrders("NET", 90.500721236, BigDecimal.valueOf(1.00), 2);
        BuyOrderWithPendingSellOrders orderThree = buildBuyOrderWithPendingSellOrders("NET", 84.360001, BigDecimal.valueOf(1.00), 2);

        List<BuyOrderWithPendingSellOrders> sellOrders = Arrays.asList(orderOne, orderTwo, orderThree);

        Map<String, List<BuyOrderWithPendingSellOrders>> buyOrders = new HashMap<>();
        buyOrders.put("NET", sellOrders);
        dailyTrader.generateDailyBestBuyOrdersFile(buyOrders);

        List<BuyBracketOrder> result = new BuyBracketOrdersFileHandler().fromJson();
        BuyBracketOrder actual = result.stream().findFirst().get();
        assertThat(actual.getSellResistancePrice(), closeTo(orderTwo.getStopLossPrice(), new BigDecimal(0.01)));
        assertThat(actual.getSellTargetPrice(), closeTo(orderTwo.getSellTargetPrice(), new BigDecimal(0.01)));
    }

    @Test
    public void shouldRetrieveOrderWithMoreOrdersWon() {
        BuyOrderWithPendingSellOrders orderOne = buildBuyOrderWithPendingSellOrders("NET", 84.360001, BigDecimal.valueOf(1.00), 1);
        BuyOrderWithPendingSellOrders orderTwo = buildBuyOrderWithPendingSellOrders("NET", 90.500721236, BigDecimal.valueOf(1.00), 2);
        BuyOrderWithPendingSellOrders orderThree = buildBuyOrderWithPendingSellOrders("NET", 84.360001, BigDecimal.valueOf(1.00), 2);

        List<BuyOrderWithPendingSellOrders> sellOrders = Arrays.asList(orderOne, orderTwo, orderThree);
        assertThat(dailyTrader.bestSellOrder(sellOrders).get(), equalTo(orderThree));
    }

    @Test
    public void shouldRetrieveOrderWithBiggestPercentage() {
        BuyOrderWithPendingSellOrders orderOne = buildBuyOrderWithPendingSellOrders("NET", 84.360001, BigDecimal.valueOf(1.00), 1);
        BuyOrderWithPendingSellOrders orderTwo = buildBuyOrderWithPendingSellOrders("NET", 90.500721236, BigDecimal.valueOf(0.90), 2);
        BuyOrderWithPendingSellOrders orderThree = buildBuyOrderWithPendingSellOrders("NET", 84.360001, BigDecimal.valueOf(0.90), 2);

        List<BuyOrderWithPendingSellOrders> sellOrders = Arrays.asList(orderOne, orderTwo, orderThree);
        assertThat(dailyTrader.bestSellOrder(sellOrders).get(), equalTo(orderOne));
    }

    @Test
    public void shouldRetrieveOrderWithMoreWons() {
        BuyOrderWithPendingSellOrders orderOne = buildBuyOrderWithPendingSellOrders("NET", 84.360001, BigDecimal.valueOf(1.00), 1);
        BuyOrderWithPendingSellOrders orderTwo = buildBuyOrderWithPendingSellOrders("NET", 90.500721236, BigDecimal.valueOf(0.90), 2);
        BuyOrderWithPendingSellOrders orderThree = buildBuyOrderWithPendingSellOrders("NET", 84.360001, BigDecimal.valueOf(1.00), 2);

        List<BuyOrderWithPendingSellOrders> sellOrders = Arrays.asList(orderOne, orderTwo, orderThree);
        assertThat(dailyTrader.bestSellOrder(sellOrders).get(), equalTo(orderThree));
    }

    private BuyOrderWithPendingSellOrders buildBuyOrderWithPendingSellOrders(String stockName, double sellTargetPrice, BigDecimal percentageOfOrdersWon, int ordersWon) {
        DailyQuote dailyQuote = new DailyQuote(LocalDate.now(), 80.050003, 84.050003, 84.050003, 74.050003, 94.050003, 100);
        BuyOrder order = new BuyOrder(stockName, dailyQuote, 119, BigDecimal.valueOf(80.050003));

        TrailingStopSellDecision trailingStopSellDecision = new TrailingStopSellDecision(new BigDecimal(80.050003), BigDecimal.TEN, BigDecimal.TEN);
        ConditionalOrder sellTrailingStopSellSellConditionalOrder = new SellConditionalOrder(stockName, order, 119, trailingStopSellDecision);;
        sellTrailingStopSellSellConditionalOrder.addCondition(trailingStopSellDecision);

        BuyOrderWithPendingSellOrders orderOne = new BuyOrderWithPendingSellOrders(order);
        orderOne.addSellPendingOrder(sellTrailingStopSellSellConditionalOrder);
        StockGroup stockGroup = new StockGroup(stockName, "Stock Group Desc", new String[]{stockName}, percentageOfOrdersWon, ordersWon, 0, null, null);
        orderOne.setStockGroup(stockGroup);
        return orderOne;
    }
}