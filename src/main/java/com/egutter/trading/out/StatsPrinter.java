package com.egutter.trading.out;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.SellOrder;
import com.egutter.trading.repository.PortfolioRepository;
import com.egutter.trading.runner.TradeOneDayRunner;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by egutter on 1/6/15.
 */
public class StatsPrinter {


    private PortfolioRepository portfolioRepository;
    private StockMarket stockMarket;
    private List<Candidate> candidates;

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2014, 1, 1);
        LocalDate toDate = LocalDate.now();
        PortfolioRepository portfolioRepository = new PortfolioRepository();
        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, false, false);
        new StatsPrinter(portfolioRepository, stockMarket, new TradeOneDayRunner(fromDate, toDate).candidates()).printStatsAndPortfolio(new LocalDate(2015, 3, 2));
    }

    public StatsPrinter(PortfolioRepository portfolioRepository, StockMarket stockMarket, List<Candidate> candidates) {
        this.portfolioRepository = portfolioRepository;
        this.stockMarket = stockMarket;
        this.candidates = candidates;
    }

    public void printStatsAndPortfolio(LocalDate lastTradingDay) {
        System.out.println("==========================================");
        System.out.println("ALL STATS");

        portfolioRepository.forEachStatCollection(key -> {
            System.out.println("==========================================");
            System.out.println(findCandidateByKey(key));
            AtomicReference<BigDecimal> averageReturn = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
            AtomicInteger count = new AtomicInteger(0);
            portfolioRepository.forEachStat(key, buySellOrder -> {
                System.out.println(buySellOrder);
                averageReturn.getAndAccumulate(buySellOrder.profitPctg30(), (currentProfit, newProfit) -> {
                    return currentProfit.add(newProfit);
                });
                count.getAndIncrement();
            });
            System.out.println("Average return 30d " + averageReturn.get().divide(BigDecimal.valueOf(count.intValue()), RoundingMode.HALF_EVEN) + "%");
        });

        System.out.println("==========================================");
        System.out.println("******************************************");
        System.out.println("==========================================");
        System.out.println("ALL STOCKS IN PORTFOLIO");
        Map<String, List<Pair<String, BuyOrder>>> stockMap = new HashMap<String, List<Pair<String, BuyOrder>>>();
        portfolioRepository.forEachStock((key, buyOrder) -> {
            if (stockMap.containsKey(buyOrder.getStockName())) {
                List<Pair<String, BuyOrder>> buyOrders = stockMap.get(buyOrder.getStockName());
                buyOrders.add(new Pair(key, buyOrder));
            } else {
                List<Pair<String, BuyOrder>> buyOrders = new ArrayList<Pair<String, BuyOrder>>();
                buyOrders.add(new Pair(key, buyOrder));
                stockMap.put(buyOrder.getStockName(), buyOrders);
            }
        });
        stockMap.keySet().forEach(stockName -> {
            System.out.println("==========================================");
            System.out.println(stockName);
            stockMap.get(stockName).forEach(pair -> {
                BuyOrder buyOrder = pair.getSecond();
                SellOrder fakeSellOrder = new SellOrder(stockName, stockMarket.getStockPricesFor(stockName).dailyPriceOn(lastTradingDay).get(), buyOrder.getNumberOfShares());
                BuySellOperation fakeOperation = new BuySellOperation(buyOrder, fakeSellOrder);
                System.out.println(findCandidateByKey(pair.getFirst()) + " " + fakeOperation);
            });
        });
    }

    public Candidate findCandidateByKey(String key) {
        return candidates.stream().filter(candidate -> candidate.key().equals(key)).findFirst().get();
    }

}
