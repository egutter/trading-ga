package com.egutter.trading.out;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.SellOrder;
import com.egutter.trading.repository.PortfolioRepository;
import com.egutter.trading.runner.TradeOneDayRunner;
import com.egutter.trading.stats.CandidateRanker;
import com.egutter.trading.stats.CandidateStatsCollector;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import org.apache.commons.math3.util.Pair;
import org.joda.time.LocalDate;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by egutter on 1/6/15.
 */
public class StatsPrinter {


    private final CandidateRanker ranker;
    private PortfolioRepository portfolioRepository;
    private StockMarket stockMarket;
    private List<Candidate> candidates;

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2014, 1, 1);
        LocalDate toDate = LocalDate.now();
        PortfolioRepository portfolioRepository = new PortfolioRepository();
        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, false, false);
        LocalDate lastTradingDay = stockMarket.getLastTradingDay();
        new StatsPrinter(portfolioRepository, stockMarket, new TradeOneDayRunner(fromDate, toDate).candidates()).printStatsAndPortfolio(lastTradingDay);
    }

    public StatsPrinter(PortfolioRepository portfolioRepository, StockMarket stockMarket, List<Candidate> candidates) {
        this.portfolioRepository = portfolioRepository;
        this.stockMarket = stockMarket;
        this.candidates = candidates;
        this.ranker = new CandidateRanker(new CandidateStatsCollector(this.portfolioRepository));
    }

    public void printStatsAndPortfolio(LocalDate lastTradingDay) {

        System.out.println("==========================================");
        System.out.println("ALL STATS");

        portfolioRepository.forEachStatCollection(key -> {
            System.out.println("==========================================");

            System.out.println(candidateNameByKey(key));
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
                System.out.println(candidateNameByKey(pair.getFirst()) + " " + fakeOperation);
            });
        });
    }

    public String candidateNameByKey(String key) {
        Candidate candidateFound = candidates.stream().filter(candidate -> candidate.key().equals(key)).findFirst().orElseThrow(() -> new RuntimeException("Cannot find candidate for " + key));

        return ranker.rank(candidateFound) + candidateFound;
    }

    public void htmlStatsAndPortfolioOn(LocalDate lastTradingDay, PrintWriter writer) {
        System.out.println("Generating html for " + lastTradingDay);

        writer.print("<h1>ALL STATS</h1>");

        portfolioRepository.forEachStatCollection(key -> {
            writer.print("<h2>" + candidateNameByKey(key) + "</h2>");
            AtomicReference<BigDecimal> averageReturn = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
            AtomicInteger count = new AtomicInteger(0);
            writer.print("<ul>");
            portfolioRepository.forEachStat(key, buySellOrder -> {
                writer.print("<li>" + buySellOrder + "</li>");
                averageReturn.getAndAccumulate(buySellOrder.profitPctg30(), (currentProfit, newProfit) -> {
                    return currentProfit.add(newProfit);
                });
                count.getAndIncrement();
            });
            writer.print("</ul>");
            writer.print("<p>Average return 30d " + averageReturn.get().divide(BigDecimal.valueOf(count.intValue()), RoundingMode.HALF_EVEN) + "%</p>");
        });

        writer.print("<h1>ALL STOCKS IN PORTFOLIO</h1>");
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
            writer.print("<h2>" + stockName + "</h2>");
            writer.print("<ul>");
            stockMap.get(stockName).forEach(pair -> {
                BuyOrder buyOrder = pair.getSecond();
                DailyQuote buyDailyQuote = buyOrder.getDailyQuote();
                Optional<DailyQuote> fakeSelldailyQuoteOptional = stockMarket.getStockPricesFor(stockName).dailyPriceOn(lastTradingDay);
                DailyQuote fakeSellDailyQuote = fakeSelldailyQuoteOptional.orElse(fakeSellQuoteBasedOn(lastTradingDay, buyDailyQuote));
                SellOrder fakeSellOrder = new SellOrder(stockName, fakeSellDailyQuote, buyOrder.getNumberOfShares());
                BuySellOperation fakeOperation = new BuySellOperation(buyOrder, fakeSellOrder);
                writer.print("<li>" + candidateNameByKey(pair.getFirst()) + " " + fakeOperation + "</li>");
            });
            writer.print("</ul>");
        });
    }

    private DailyQuote fakeSellQuoteBasedOn(LocalDate lastTradingDay, DailyQuote buyDailyQuote) {
        return new DailyQuote(lastTradingDay,
                0,
                buyDailyQuote.getClosePrice(),
                buyDailyQuote.getAdjustedClosePrice(),
                buyDailyQuote.getLowPrice(),
                buyDailyQuote.getHighPrice(),
                buyDailyQuote.getVolume());
    }
}
