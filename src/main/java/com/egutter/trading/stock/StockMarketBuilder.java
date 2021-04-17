package com.egutter.trading.stock;

import com.egutter.trading.repository.HistoricPriceRepository;
import com.egutter.trading.runner.YahooQuoteImporter;
import com.mongodb.*;
import org.joda.time.LocalDate;

import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by egutter on 2/17/14.
 */
public class StockMarketBuilder {

    private YahooQuoteImporter yahooQuoteImporter = new YahooQuoteImporter();
    private HistoricPriceRepository repository = new HistoricPriceRepository();

    public static void main(String[] args) {
        LocalDate fromDate = new LocalDate(2020, 1, 1);
        LocalDate toDate = LocalDate.now();
        new StockMarketBuilder().build(fromDate, toDate, true, true, StockMarket.sNp20());
    }

    public static void main3(String[] args) {
        LocalDate fromDate = new LocalDate(2020, 4, 1);
        LocalDate toDate = LocalDate.now();
        List<String> failedStocks = new ArrayList<>();
        for (String stock : StockMarket.sNp500()) {
            try {
                new StockMarketBuilder().buildInMemory(fromDate, new String[] {stock});
            } catch (Exception e) {
                failedStocks.add(stock);
            }
        }
        System.out.println("Cannot retrieve stocks [" + failedStocks +"]");
//                build(fromDate, toDate).getStockPrices().stream().forEach(stockPrice -> System.out.println(stockPrice.getStockName() + " - " + stockPrice.getDailyQuotes().size()));
    }

    public static void main2(String[] args) {
        try {
            Mongo client = new Mongo();
            DB db = client.getDB(HistoricPriceRepository.STOCK_STATS);

            Set<String> colls = db.getCollectionNames();

            List<StockPrices> stockPrices = new ArrayList<StockPrices>();
            StockPrices marketIndexPrices = null;
            for (String stockName : colls) {
                if (stockName.equals("system.indexes")) {
                    continue;
                }
                DBObject query = new BasicDBObject();
                LocalDate fromDate = new LocalDate(2013, 2, 21);
                LocalDate toDate = new LocalDate(2013, 2, 28);
                query.put("date", BasicDBObjectBuilder.start("$gte", fromDate.toDate()).add("$lte", toDate.toDate()).get());

                DBCursor cursor = db.getCollection(stockName).find(query);
                System.out.println("name " + stockName);
                List<DailyQuote> dailyPrices = new ArrayList<DailyQuote>();
                try {
                    while (cursor.hasNext()) {
                        DBObject object = cursor.next();
                        LocalDate tradingDate = LocalDate.fromDateFields((Date) object.get("date"));
                        Double adjustedClose = Double.valueOf(((String) object.get("adjusted_close")));
                        Double open = Double.valueOf(((String) object.get("open")));
                        Double low = Double.valueOf(((String) object.get("low")));
                        Double high = Double.valueOf(((String) object.get("high")));
                        Double close = Double.valueOf(((String) object.get("close")));
                        Long volume = Long.valueOf(((String) object.get("volume")));
                        System.out.println("date " + tradingDate + " close " + close);

                        dailyPrices.add(new DailyQuote(tradingDate, open, close, adjustedClose, low, high, volume));
                    }
                } finally {
                    cursor.close();
                }
                if (stockName.equals("%5EMERV")) {
                    marketIndexPrices = new StockPrices("MERVAL", dailyPrices);
                } else {
                    stockPrices.add(new StockPrices(stockName, dailyPrices));
                }
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public StockMarket buildInMemory(LocalDate fromDate) {
        List<StockPrices> stockPrices = yahooQuoteImporter.fetchStockPrices(fromDate, StockMarket.sNp20());;
//        List<StockPrices> stockPrices = yahooQuoteImporter.fetchStockPrices(fromDate, StockMarket.aapl());;
        return getStockMarket(fromDate, stockPrices);
    }

    public StockMarket buildInMemory(LocalDate fromDate, String[] symbols) {
        List<StockPrices> stockPrices = yahooQuoteImporter.fetchStockPrices(fromDate, symbols);;
        return getStockMarket(fromDate, stockPrices);
    }

    private StockMarket getStockMarket(LocalDate fromDate, List<StockPrices> stockPrices) {
        List<DailyQuote> marketIndexPrices = yahooQuoteImporter.fetchStockPrices(fromDate, StockMarket.spy()).get(0).getDailyQuotes();
        System.out.println("stock prices size "+stockPrices.size());
        System.out.println("first stock name "+stockPrices.get(0).getStockName());
        System.out.println("num of daily quotes "+stockPrices.get(0).getDailyQuotes().size());
        return new StockMarket(stockPrices, new StockPrices("SPY", marketIndexPrices));
    }

    public StockMarket build(LocalDate fromDate, LocalDate toDate) {
        return build(fromDate, toDate, false, false, StockMarket.sNp20());
    }

    public StockMarket build(LocalDate fromDate, LocalDate toDate, String[] stockSymbols) {
        return build(fromDate, toDate, false, false, stockSymbols);
    }

    public StockMarket build(LocalDate fromDate, LocalDate toDate, boolean runImport, boolean appendLastQuoteFromMarket, String[] stockSymbols) {
        if (runImport) yahooQuoteImporter.runImportFromMaxOrFrom(fromDate, toDate, stockSymbols);
        List<StockPrices> stockPrices = new ArrayList<StockPrices>();

        repository.forEachStock(stockName -> {
            List<DailyQuote> dailyPrices = new ArrayList<DailyQuote>();
            repository.forEachDailyQuote(fromDate, toDate, stockName, (dailyQuote) -> {
                dailyPrices.add((DailyQuote) dailyQuote);
            });
            if (appendLastQuoteFromMarket) appendLastQuoteFromMarket(stockName, dailyPrices, toDate);
            stockPrices.add(new StockPrices(stockName, dailyPrices));
        }, stockName -> !Arrays.asList(stockSymbols).contains(stockName));
        return new StockMarket(stockPrices);
    }

    private void appendLastQuoteFromMarket(String stockName, List<DailyQuote> dailyPrices, LocalDate toDate) {

        LocalDate maxTradingDate = repository.getMaxTradingDate(stockName).orElse(toDate);
        if (toDate.isBefore(maxTradingDate) || toDate.equals(maxTradingDate)) {
            System.out.println("Max trading date in repo is equal or after to date for symbol " + stockName);
            return;
        }

        Optional<DailyQuote> potentialLastQuote = yahooQuoteImporter.getLastQuote(stockName);
        potentialLastQuote.ifPresent( lastQuote -> {
            LocalDate tradingDate = lastQuote.getTradingDate();
            if (tradingDate.isAfter(maxTradingDate)) {
                System.out.println("Add last quote to market " + tradingDate + " " + lastQuote);
                dailyPrices.add(lastQuote);
            }
        });
    }

}
