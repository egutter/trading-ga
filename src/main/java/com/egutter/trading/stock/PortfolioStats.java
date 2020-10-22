package com.egutter.trading.stock;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.SellOrder;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.google.common.collect.FluentIterable.from;

/**
 * Created by egutter on 4/17/14.
 */
public class PortfolioStats {

    private List<BuySellOperation> stats = new ArrayList<BuySellOperation>();
    private List<BuySellOperation> ordersWon = new ArrayList<>();
    private List<BuySellOperation> ordersLost= new ArrayList<>();
    private ImmutableList<BuySellOperation> ordersAboveMarket;

    public void removeStat(BuySellOperation buySellOperation) {
        this.stats.remove(buySellOperation);
    }

    public void addStatsFor(PortfolioAsset portfolioAsset, SellOrder sellOrder) {
        BuyOrder buyOrder = portfolioAsset.getBuyOrder();
        buyOrder.setNumberOfShares(sellOrder.getNumberOfShares());
        BuySellOperation buySellOperation = new BuySellOperation(buyOrder, sellOrder);
        if (buySellOperation.isLost()) {
            this.ordersLost.add(buySellOperation);
        } else if (buySellOperation.isWon()) {
            this.ordersWon.add(buySellOperation);
        }
        this.stats.add(buySellOperation);
    }

    public List<String> mostPopularStocks(int numOfElements) {
        Map<String, Integer> countByStock = new HashMap<String, Integer>();
        for (BuySellOperation operation : stats) {
            String stockName = operation.getBuyOrder().getStockName();
            if (countByStock.containsKey(stockName)) {
                int count = countByStock.get(stockName);
                countByStock.put(stockName, count + 1);
            } else {
                countByStock.put(stockName, 1);
            }
        }

        return Ordering.natural().onResultOf(Functions.forMap(countByStock)).greatestOf(countByStock.keySet(), numOfElements);
    }

    public int countOrdersWon() {
        return this.ordersWon.size();
    }

    private int countOrdersAboveMarket() {
        return ordersAboveMarket().size();
    }

    public double countOrdersWonMinusLost() {
        return countOrdersWon()-stats.size();
    }

    public int countOrdersLost() {
        return this.ordersLost.size();
    }

    public BigDecimal percentageOfOrdersWon() {
        if (stats.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(countOrdersWon()).divide(BigDecimal.valueOf(stats.size()), 2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal percentageOfOrdersAboveMarket() {
        if (stats.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(countOrdersAboveMarket()).divide(BigDecimal.valueOf(stats.size()), 2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal percentageOfOrdersNotLost() {
        if (stats.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(countOrdersWon()+countOrdersEven()).divide(BigDecimal.valueOf(stats.size()), 2, RoundingMode.HALF_EVEN);
    }

    public int countOrdersEven() {
        return from(stats).filter(new Predicate<BuySellOperation>() {
            @Override
            public boolean apply(BuySellOperation operation) {
                return operation.isEven();
            }
        }).size();
    }

    public BigDecimal allOrdersAverageReturn() {
        return averageReturn(stats);
    }

    public BigDecimal lostOrdersAverageReturn() {
        return averageReturn(this.ordersLost);
    }

    public BigDecimal averageReturn(List<BuySellOperation> someStats) {
        List<BigDecimal> dailyReturns = from(someStats).transform(new Function<BuySellOperation, BigDecimal>() {
            @Override
            public BigDecimal apply(BuySellOperation operation) {
                try {
                    return operation.profit().divide(operation.getBuyOrder().amountPaid(), RoundingMode.HALF_EVEN);
                } catch (java.lang.ArithmeticException e) {
                    return BigDecimal.ZERO;
                }
            }
        }).toList();
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal dailyReturn : dailyReturns) {
            total = total.add(dailyReturn);
        }
        if (dailyReturns.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return total.divide(BigDecimal.valueOf(dailyReturns.size()), RoundingMode.HALF_EVEN);
    }

    public BigDecimal average30daysReturn() {
        List<BigDecimal> returnPctg = stats.stream().map(BuySellOperation::profitPctg30).collect(Collectors.toList());
        BigDecimal total = BigDecimal.ZERO;
        for (BigDecimal dailyReturn : returnPctg) {
            total = total.add(dailyReturn);
        }
        if (returnPctg.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return total.divide(BigDecimal.valueOf(returnPctg.size()), RoundingMode.HALF_EVEN);
    }

    public int totalOrdersCount() {
        return stats.size();
    }

    private List<BuySellOperation> ordersAboveMarket() {
        if (this.ordersAboveMarket == null) {
            this.ordersAboveMarket = from(stats).filter(new Predicate<BuySellOperation>() {
                @Override
                public boolean apply(BuySellOperation operation) {
                    return operation.isAboveMarket();
                }
            }).toList();
        }
        return ordersAboveMarket;
    }

    public BuySellOperation biggestLost() {
       if (this.ordersLost.isEmpty()) return BuySellOperation.empty();
       return buySellOperationOrdering().min(this.ordersLost);
    }


    public BuySellOperation biggestWin() {
        if (this.ordersWon.isEmpty()) return BuySellOperation.empty();
        return buySellOperationOrdering().max(this.ordersWon);
    }

    public void forEachStat(Consumer<BuySellOperation> applyBlock) {
        stats.forEach(buySellOperation -> applyBlock.accept(buySellOperation));
    }

    private Ordering<BuySellOperation> buySellOperationOrdering() {
        return new Ordering<BuySellOperation>() {
             public int compare(BuySellOperation left, BuySellOperation right) {
                 return left.compareTo(right);
             }
         };
    }

    public Map<LocalDate, Pair<Integer, Integer>> ordersPerDay() {
        Map<LocalDate, Pair<Integer, Integer>> ordersPerDay = new HashMap<LocalDate, Pair<Integer, Integer>>();

        stats.stream().forEach(operation -> {
            LocalDate buyTradingDate = operation.getBuyOrder().getTradingDate();
            if (ordersPerDay.containsKey(buyTradingDate)) {
                Pair<Integer, Integer> orders = ordersPerDay.get(buyTradingDate);
                Integer newCount = orders.getFirst() + 1;
                ordersPerDay.put(buyTradingDate, new Pair<Integer, Integer>(newCount, orders.getSecond()));
            } else {
                ordersPerDay.put(buyTradingDate, new Pair<Integer, Integer>(1, 0));
            }
            LocalDate sellTradingDate = operation.getSellOrder().getTradingDate();
            if (ordersPerDay.containsKey(sellTradingDate)) {
                Pair<Integer, Integer> orders = ordersPerDay.get(sellTradingDate);
                Integer newCount = orders.getSecond() + 1;
                ordersPerDay.put(sellTradingDate, new Pair<Integer, Integer>(orders.getFirst(), newCount));
            } else {
                ordersPerDay.put(sellTradingDate, new Pair<Integer, Integer>(0, 1));
            }
        });
        return ordersPerDay;
    }

    public boolean hasLostOrders() {
        return this.countOrdersLost() > 0;
    }

    public List<BuySellOperation> allOrders() {
        return this.stats;
    }
}
