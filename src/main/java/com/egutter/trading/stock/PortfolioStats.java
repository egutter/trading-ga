package com.egutter.trading.stock;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.SellOrder;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import org.apache.commons.math3.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.google.common.collect.FluentIterable.from;

/**
 * Created by egutter on 4/17/14.
 */
public class PortfolioStats {

    private List<Pair<BuyOrder, SellOrder>> stats = new ArrayList<Pair<BuyOrder, SellOrder>>();

    public void addStatsFor(BuyOrder buyOrder, SellOrder sellOrder) {
        this.stats.add(new Pair(buyOrder, sellOrder));
    }

    public List<String> mostPopularStocks(int numOfElements) {
        Map<String, Integer> countByStock = new HashMap<String, Integer>();
        for (Pair<BuyOrder, SellOrder> pair : stats) {
            String stockName = pair.getFirst().getStockName();
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
        return ordersWon().size();
    }

    public int countOrdersLost() {
        return ordersLost().size();
    }

    public BigDecimal percentageOfOrdersWon() {
        if (stats.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(countOrdersWon()).divide(BigDecimal.valueOf(stats.size()), 2, RoundingMode.HALF_EVEN);
    }

    public int countOrdersEven() {
        return from(stats).filter(new Predicate<Pair<BuyOrder, SellOrder>>() {
            @Override
            public boolean apply(Pair<BuyOrder, SellOrder> pair) {
                return pair.getFirst().amountPaid().equals(pair.getSecond().amountEarned());
            }
        }).size();
    }

    public BigDecimal allOrdersAverageReturn() {
        return averageReturn(stats);
    }

    public BigDecimal lostOrdersAverageReturn() {
        return averageReturn(ordersLost());
    }

    public BigDecimal averageReturn(List<Pair<BuyOrder, SellOrder>> someStats) {
        List<BigDecimal> dailyReturns = from(someStats).transform(new Function<Pair<BuyOrder, SellOrder>, BigDecimal>() {
            @Override
            public BigDecimal apply(Pair<BuyOrder, SellOrder> pair) {
                BigDecimal amountEarned = pair.getSecond().amountEarned();
                BigDecimal amountPaid = pair.getFirst().amountPaid();

                return amountEarned.subtract(amountPaid).divide(amountPaid, RoundingMode.HALF_EVEN);
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

    private List<Pair<BuyOrder, SellOrder>> ordersLost() {
        return from(stats).filter(new Predicate<Pair<BuyOrder, SellOrder>>() {
            @Override
            public boolean apply(Pair<BuyOrder, SellOrder> pair) {
                return pair.getFirst().amountPaid().doubleValue() > pair.getSecond().amountEarned().doubleValue();
            }
        }).toList();
    }

    private FluentIterable<Pair<BuyOrder, SellOrder>> ordersWon() {
        return from(stats).filter(new Predicate<Pair<BuyOrder, SellOrder>>() {
            @Override
            public boolean apply(Pair<BuyOrder, SellOrder> pair) {
                return pair.getFirst().amountPaid().doubleValue() < pair.getSecond().amountEarned().doubleValue();
            }
        });
    }



}
