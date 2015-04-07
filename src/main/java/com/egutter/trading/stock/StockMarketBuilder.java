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
        try {
            Mongo client = new Mongo();
            DB db = client.getDB("merval-stats");

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


    public StockMarket build(LocalDate fromDate, LocalDate toDate) {
        return build(fromDate, toDate, false, false);
    }

    public StockMarket build(LocalDate fromDate, LocalDate toDate, boolean runImport, boolean appendLastQuoteFromMarket) {
        if (runImport) yahooQuoteImporter.runImport();
        List<StockPrices> stockPrices = new ArrayList<StockPrices>();
        List<DailyQuote> marketIndexPrices = new ArrayList<DailyQuote>();

        repository.forEachStock(stockName -> {
            List<DailyQuote> dailyPrices = new ArrayList<DailyQuote>();
            repository.forEachDailyQuote(fromDate, toDate, stockName, (dailyQuote) -> {
                dailyPrices.add((DailyQuote) dailyQuote);
            });
            if (appendLastQuoteFromMarket) appendLastQuoteFromMarket(stockName, dailyPrices);
            if (stockName.endsWith("MERV")) {
                marketIndexPrices.addAll(dailyPrices);
            } else {
                stockPrices.add(new StockPrices(stockName, dailyPrices));
            }
        });
        return new StockMarket(stockPrices, new StockPrices("MERVAL", marketIndexPrices));
    }

    private void appendLastQuoteFromMarket(String stockName, List<DailyQuote> dailyPrices) {
        Optional<DailyQuote> potentialLastQuote = yahooQuoteImporter.getLastQuote(stockName);
        potentialLastQuote.ifPresent( lastQuote -> {
            if (lastQuote.getTradingDate().isAfter(repository.getMaxTradingDate(stockName))) {
                System.out.println("Add last quote to market " + lastQuote.getTradingDate() + " " + lastQuote);
                dailyPrices.add(lastQuote);
            }
        });
    }
}
