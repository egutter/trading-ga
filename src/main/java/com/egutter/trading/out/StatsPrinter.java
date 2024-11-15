package com.egutter.trading.out;

/**
 * Created by egutter on 1/6/15.
 */
public class StatsPrinter {

//
//    private final CandidateRanker ranker;
//    private PortfolioRepository portfolioRepository;
//    private StockMarket stockMarket;
//    private List<Candidate> candidates;
//
//    public static void main(String[] args) {
//        LocalDate fromDate = new LocalDate(2014, 1, 1);
//        LocalDate toDate = LocalDate.now();
//        PortfolioRepository portfolioRepository = new PortfolioRepository();
//        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);
//        LocalDate lastTradingDay = stockMarket.getLastTradingDay();
//        new StatsPrinter(portfolioRepository, stockMarket, new TradeOneDayRunner(fromDate, toDate).candidates()).printStatsAndPortfolio(lastTradingDay);
//    }
//
//    public StatsPrinter(PortfolioRepository portfolioRepository, StockMarket stockMarket, List<Candidate> candidates) {
//        this.portfolioRepository = portfolioRepository;
//        this.stockMarket = stockMarket;
//        this.candidates = candidates;
//        this.ranker = new CandidateRanker(new CandidateStatsCollector(this.portfolioRepository));
//    }
//
//    public void printStatsAndPortfolio(LocalDate lastTradingDay) {
//
//        System.out.println("==========================================");
//        System.out.println("ALL STATS");
//
//        portfolioRepository.forEachStatCollection(key -> {
//            System.out.println("==========================================");
//
//            System.out.println(candidateNameByKey(key));
//            AtomicReference<BigDecimal> averageReturn = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
//            AtomicInteger count = new AtomicInteger(0);
//            portfolioRepository.forEachStat(key, buySellOrder -> {
//                System.out.println(buySellOrder);
//                averageReturn.getAndAccumulate(buySellOrder.profitPctg30(), (currentProfit, newProfit) -> {
//                    return currentProfit.add(newProfit);
//                });
//                count.getAndIncrement();
//            });
//            System.out.println("Average return 30d " + averageReturn.get().divide(BigDecimal.valueOf(count.intValue()), RoundingMode.HALF_EVEN) + "%");
//        });
//
//        System.out.println("==========================================");
//        System.out.println("******************************************");
//        System.out.println("==========================================");
//        System.out.println("ALL STOCKS IN PORTFOLIO");
//        Map<String, List<Pair<String, BuyOrder>>> stockMap = new HashMap<String, List<Pair<String, BuyOrder>>>();
//        portfolioRepository.forEachStock((key, buyOrder) -> {
//            if (stockMap.containsKey(buyOrder.getStockName())) {
//                List<Pair<String, BuyOrder>> buyOrders = stockMap.get(buyOrder.getStockName());
//                buyOrders.add(new Pair(key, buyOrder));
//            } else {
//                List<Pair<String, BuyOrder>> buyOrders = new ArrayList<Pair<String, BuyOrder>>();
//                buyOrders.add(new Pair(key, buyOrder));
//                stockMap.put(buyOrder.getStockName(), buyOrders);
//            }
//        });
//        stockMap.keySet().forEach(stockName -> {
//            System.out.println("==========================================");
//            System.out.println(stockName);
//            stockMap.get(stockName).forEach(pair -> {
//                BuyOrder buyOrder = pair.getSecond();
//                SellOrder fakeSellOrder = new SellOrder(stockName, stockMarket.getStockPricesFor(stockName).dailyPriceOn(lastTradingDay).get(), buyOrder.getNumberOfShares());
//                BuySellOperation fakeOperation = new BuySellOperation(buyOrder, fakeSellOrder);
//                System.out.println(candidateNameByKey(pair.getFirst()) + " " + fakeOperation);
//            });
//        });
//    }
//
//    public String candidateNameByKey(String key) {
//        Candidate candidateFound = candidateByKey(key);
//
//        return ranker.rank(candidateFound).toString() + candidateFound;
//    }
//
//    public Candidate candidateByKey(String key) {
//        return candidates.stream().filter(candidate -> candidate.key().equals(key)).findFirst().orElseThrow(() -> new RuntimeException("Cannot find candidate for " + key));
//    }
//
//    public void htmlStatsAndPortfolioOn(LocalDate lastTradingDay, PrintWriter writer) {
//        System.out.println("Generating html for " + lastTradingDay);
//
//        writer.print("<h1>ALL STATS</h1>");
//
//        portfolioRepository.forEachStatCollection(key -> {
//            writer.print("<h2>" + candidateNameByKey(key) + "</h2>");
//            AtomicReference<BigDecimal> averageReturn = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
//            AtomicInteger count = new AtomicInteger(0);
//            writer.print("<ul>");
//            portfolioRepository.forEachStat(key, buySellOrder -> {
//                writer.print("<li>" + buySellOrder + "</li>");
//                averageReturn.getAndAccumulate(buySellOrder.profitPctg30(), (currentProfit, newProfit) -> {
//                    return currentProfit.add(newProfit);
//                });
//                count.getAndIncrement();
//            });
//            writer.print("</ul>");
//            writer.print("<p>Average return 30d " + averageReturn.get().divide(BigDecimal.valueOf(count.intValue()), RoundingMode.HALF_EVEN) + "%</p>");
//        });
//
//        writer.print("<h1>ALL STOCKS IN PORTFOLIO</h1>");
//        Map<String, List<Pair<String, BuyOrder>>> stockMap = new HashMap<String, List<Pair<String, BuyOrder>>>();
//        portfolioRepository.forEachStock((key, buyOrder) -> {
//            if (isMerval25(buyOrder.getStockName())) {
//                if (stockMap.containsKey(buyOrder.getStockName())) {
//                    List<Pair<String, BuyOrder>> buyOrders = stockMap.get(buyOrder.getStockName());
//                    buyOrders.add(new Pair(key, buyOrder));
//                } else {
//                    List<Pair<String, BuyOrder>> buyOrders = new ArrayList<Pair<String, BuyOrder>>();
//                    buyOrders.add(new Pair(key, buyOrder));
//                    stockMap.put(buyOrder.getStockName(), buyOrders);
//                }
//            }
//        });
//        stockMap.keySet().forEach(stockName -> {
//            writer.print("<h2>" + stockName + "</h2>");
//            writer.print("<ul>");
//            stockMap.get(stockName).forEach(pair -> {
//                BuyOrder buyOrder = pair.getSecond();
//                DailyQuote buyDailyQuote = buyOrder.getDailyQuote();
//                Optional<DailyQuote> fakeSelldailyQuoteOptional = stockMarket.getStockPricesFor(stockName).dailyPriceOn(lastTradingDay);
//                DailyQuote fakeSellDailyQuote = fakeSelldailyQuoteOptional.orElse(fakeSellQuoteBasedOn(lastTradingDay, buyDailyQuote));
//                SellOrder fakeSellOrder = new SellOrder(stockName, fakeSellDailyQuote, buyOrder.getNumberOfShares());
//                BuySellOperation fakeOperation = new BuySellOperation(buyOrder, fakeSellOrder);
//                writer.print("<li>" + candidateNameByKey(pair.getFirst()) + " " + fakeOperation + "</li>");
//            });
//            writer.print("</ul>");
//        });
//    }
//
//    public Map collectCandidateStats() {
//        Map stats = new HashMap<>();
//
//        portfolioRepository.forEachStatCollection(key -> {
//            List candidateOperations = new ArrayList<>();
//
//            AtomicReference<BigDecimal> averageReturnAccum = new AtomicReference<BigDecimal>(BigDecimal.ZERO);
//            AtomicInteger count = new AtomicInteger(0);
//            portfolioRepository.forEachStat(key, buySellOrder -> {
//                candidateOperations.add(buySellOrder);
//                averageReturnAccum.getAndAccumulate(buySellOrder.profitPctg30(), (currentProfit, newProfit) -> {
//                    return currentProfit.add(newProfit);
//                });
//                count.getAndIncrement();
//            });
//            BigDecimal averageReturn = averageReturnAccum.get().divide(BigDecimal.valueOf(count.intValue()), RoundingMode.HALF_EVEN);
//            Candidate candidate = candidateByKey(key);
//            stats.put(candidate, ImmutableMap.of("OPERATIONS", candidateOperations, "AVERAGE-30", averageReturn, "RANK", ranker.rank(candidate).rankingAsString()));
//        });
//        return stats;
//    }
//
//    public Map collectStockStats() {
//        Map stats = new HashMap<>();
//        LocalDate lastTradingDay = stockMarket.getLastTradingDay();
//        Map<String, List<Pair<String, BuyOrder>>> stockMap = new HashMap<String, List<Pair<String, BuyOrder>>>();
//        portfolioRepository.forEachStock((key, buyOrder) -> {
//            if (isMerval25(buyOrder.getStockName())) {
//                if (stockMap.containsKey(buyOrder.getStockName())) {
//                    List<Pair<String, BuyOrder>> buyOrders = stockMap.get(buyOrder.getStockName());
//                    buyOrders.add(new Pair(key, buyOrder));
//                } else {
//                    List<Pair<String, BuyOrder>> buyOrders = new ArrayList<Pair<String, BuyOrder>>();
//                    buyOrders.add(new Pair(key, buyOrder));
//                    stockMap.put(buyOrder.getStockName(), buyOrders);
//                }
//            }
//        });
//        stockMap.keySet().forEach(stockName -> {
//            List stockOperations = new ArrayList();
//            stockMap.get(stockName).forEach(pair -> {
//                BuyOrder buyOrder = pair.getSecond();
//                DailyQuote buyDailyQuote = buyOrder.getDailyQuote();
//                Optional<DailyQuote> fakeSelldailyQuoteOptional = stockMarket.getStockPricesFor(stockName).dailyPriceOn(lastTradingDay);
//                DailyQuote fakeSellDailyQuote = fakeSelldailyQuoteOptional.orElse(fakeSellQuoteBasedOn(lastTradingDay, buyDailyQuote));
//                SellOrder fakeSellOrder = new SellOrder(stockName, fakeSellDailyQuote, buyOrder.getNumberOfShares());
//                BuySellOperation fakeOperation = new BuySellOperation(buyOrder, fakeSellOrder);
//                stockOperations.add(new Pair(candidateNameByKey(pair.getFirst()), fakeOperation));
//            });
//            stats.put(stockName, "</h2>");
//        });
//        return stats;
//    }
//
//    private DailyQuote fakeSellQuoteBasedOn(LocalDate lastTradingDay, DailyQuote buyDailyQuote) {
//        return new DailyQuote(lastTradingDay,
//                0,
//                buyDailyQuote.getClosePrice(),
//                buyDailyQuote.getAdjustedClosePrice(),
//                buyDailyQuote.getLowPrice(),
//                buyDailyQuote.getHighPrice(),
//                buyDailyQuote.getVolume());
//    }
}
