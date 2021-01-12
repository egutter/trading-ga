package com.egutter.trading.runner;

import com.egutter.trading.order.*;
import com.egutter.trading.order.condition.SellWhenPriceAboveTarget;
import com.egutter.trading.order.condition.SellWhenPriceBellowTarget;
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
        assertThat(actual.getSellResistancePrice(), equalTo(orderTwo.getSellResistancePrice()));
        assertThat(actual.getSellTargetPrice(), equalTo(orderTwo.getSellTargetPrice()));
    }

    @Test
    public void shouldRetrieveOrderWithBiggestProfit() {
        BuyOrderWithPendingSellOrders orderOne = buildBuyOrderWithPendingSellOrders("NET", 84.360001, BigDecimal.valueOf(1.00), 1);
        BuyOrderWithPendingSellOrders orderTwo = buildBuyOrderWithPendingSellOrders("NET", 90.500721236, BigDecimal.valueOf(1.00), 2);
        BuyOrderWithPendingSellOrders orderThree = buildBuyOrderWithPendingSellOrders("NET", 84.360001, BigDecimal.valueOf(1.00), 2);

        List<BuyOrderWithPendingSellOrders> sellOrders = Arrays.asList(orderOne, orderTwo, orderThree);
        assertThat(dailyTrader.bestSellOrder(sellOrders).get(), equalTo(orderTwo));
    }

    @Test
    public void shouldFilterOrderByMinimumProfit() {
        BuyOrderWithPendingSellOrders orderOne = buildBuyOrderWithPendingSellOrders("NET", 81.360001, BigDecimal.valueOf(1.00), 1);

        List<BuyOrderWithPendingSellOrders> sellOrders = Arrays.asList(orderOne);
        assertThat(dailyTrader.bestSellOrder(sellOrders).isPresent(), equalTo(false));
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

        SellWhenPriceBellowTarget sellWhenPriceBelowResistance = new SellWhenPriceBellowTarget(BigDecimal.valueOf(70.050003));
        ConditionalOrder sellWhenPriceBelowResistanceSellConditionalOrder = new SellConditionalOrder(stockName, order, 119, sellWhenPriceBelowResistance);;
        sellWhenPriceBelowResistanceSellConditionalOrder.addCondition(sellWhenPriceBelowResistance);

        SellWhenPriceAboveTarget sellWhenPriceAboveTarget = new SellWhenPriceAboveTarget(BigDecimal.valueOf(sellTargetPrice));
        SellConditionalOrder sellWhenPriceAboveTargetSellConditionalOrder = new SellConditionalOrder(stockName, order, 119, sellWhenPriceAboveTarget);
        sellWhenPriceAboveTargetSellConditionalOrder.addCondition(sellWhenPriceAboveTarget);

        BuyOrderWithPendingSellOrders orderOne = new BuyOrderWithPendingSellOrders(order);
        orderOne.addSellPendingOrder(sellWhenPriceAboveTargetSellConditionalOrder);
        orderOne.addSellPendingOrder(sellWhenPriceBelowResistanceSellConditionalOrder);
        StockGroup stockGroup = new StockGroup(stockName, "Stock Group Desc", null, percentageOfOrdersWon, ordersWon, 0, null, null);
        orderOne.setStockGroup(stockGroup);
        return orderOne;
    }
}