package com.egutter.trading.repository;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Ordering;
import com.mongodb.*;
import org.joda.time.LocalDate;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by egutter on 2/10/14.
 */
public class HistoricPriceRepository extends MongoRepository {

    public static final String STOCK_STATS = "stock-stats";
    private static DB dbConn;

    public static void main(String[] args) throws Exception {

    }

    public static void main2(String[] args) throws Exception {
//        System.out.println(new HistoricPriceRepository().getMaxTradingDate());
        MongoClientURI uri = new MongoClientURI("mongodb://heroku_app35328737:o0oc7dgtftks5k028g8p3ao7tp@ds029117.mongolab.com:29117/heroku_app35328737");
        MongoClient client = new MongoClient(uri);
        DB conn = client.getDB("heroku_app35328737");
        Set<String> colls = conn.getCollectionNames();
        for (String stockName : colls) {
            if (stockName.equals("system.indexes")) {
                continue;
            }
            System.out.println(stockName);
        }
    }

    public Optional<LocalDate> getMaxTradingDate(String stockName) {
        return getMinMaxTradingDate(stockName, -1);
    }

    public Optional<LocalDate> getMinTradingDate(String stockName) {
        return getMinMaxTradingDate(stockName, 1);
    }

    private Optional<LocalDate> getMinMaxTradingDate(String stockName, int order) {
        DBCursor cursor = historicPriceConn().getCollection(stockName).find().sort(new BasicDBObject("date", order)).limit(1);

        Optional<LocalDate> maxDate = Optional.empty();
        try {
            while (cursor.hasNext()) {

                DBObject object = cursor.next();
                maxDate = Optional.of(LocalDate.fromDateFields((Date) object.get("date")));
            }
        } finally {
            cursor.close();
        }
        return maxDate;
    }

    public Optional<LocalDate> getMaxTradingDate(String[] symbols) {
        List<LocalDate> maxDates = new ArrayList<LocalDate>();

        for (int i = 0; i < symbols.length; i++) {
            Optional<LocalDate> maxTradingDate = getMaxTradingDate(symbols[i]);
            maxTradingDate.ifPresent(date -> maxDates.add(date));
        }

        if (maxDates.isEmpty()) return Optional.empty();
        return Optional.of(Ordering.natural().max(maxDates));
    }

    public void insertStockPrices(StockPrices prices) {
        DBCollection collection = historicPriceConn().getCollection(prices.getStockName());
        for (DailyQuote quote : prices.getDailyQuotes()) {
            BasicDBObject doc = new BasicDBObject("date", quote.getTradingDate().toDate())
                    .append("adjusted_close", quote.getAdjustedClosePrice())
                    .append("open", quote.getOpenPrice())
                    .append("high", quote.getHighPrice())
                    .append("low", quote.getLowPrice())
                    .append("close", quote.getClosePrice())
                    .append("volume", quote.getVolume());
            collection.insert(doc);
        }
    }

    public void forEachStock(Consumer<String> applyBlock, Predicate<String> filter) {
        Set<String> colls = historicPriceConn().getCollectionNames();
        for (String stockName : colls) {
            if (stockName.contains("system") || filter.test(stockName)) {
                continue;
            }
            applyBlock.accept(stockName);
        }
    }
    public void forEachStock(Consumer<String> applyBlock) {
        forEachStock(applyBlock, stockName -> false);
    }

    public void forEachDailyQuote(LocalDate fromDate, LocalDate toDate, String stockName, Consumer applyBlock) {
        DBObject query = new BasicDBObject();
        query.put("date", BasicDBObjectBuilder.start("$gte", fromDate.toDate()).add("$lte", toDate.toDate()).get());

        DBCursor cursor = historicPriceConn().getCollection(stockName).find(query).sort(new BasicDBObject("date", 1));

        try {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                LocalDate tradingDate = LocalDate.fromDateFields((Date) object.get("date"));
                Double adjustedClose = (Double) object.get("adjusted_close");
                Double open = (Double) object.get("open");
                Double low = (Double) object.get("low");
                Double high = (Double) object.get("high");
                Double close = (Double) object.get("close");
                Long volume = (Long) object.get("volume");

                applyBlock.accept(new DailyQuote(tradingDate, open, close, adjustedClose, low, high, volume));
            }
        } finally {
            cursor.close();
        }
    }

    public void removeAll(String stockName) {
        DBCollection collection = historicPriceConn().getCollection(stockName);
        DBObject query = new BasicDBObject();
        collection.remove(query);
    }

    public void removeAll() {
        forEachStock(stockName -> {
            DBCollection collection = historicPriceConn().getCollection(stockName);
            collection.drop();
        });
    }

    public void removeAllBetween(String stockName, LocalDate fromDate, LocalDate toDate) {
        DBObject query = new BasicDBObject();
        query.put("date", BasicDBObjectBuilder.start("$gte", fromDate.toDate()).add("$lte", toDate.toDate()).get());

        DBCollection collection = historicPriceConn().getCollection(stockName);
        collection.remove(query);
    }

    public void removeAllFrom(String stockName, LocalDate fromDate) {
        DBObject query = new BasicDBObject();
        query.put("date", BasicDBObjectBuilder.start("$gte", fromDate.toDate()).get());

        DBCollection collection = historicPriceConn().getCollection(stockName);
        collection.remove(query);
    }

    public void removeAllAt(LocalDate fromDate) {
        forEachStock(stockName -> {
            DBObject query = new BasicDBObject();
            query.put("date", BasicDBObjectBuilder.start("$gte", fromDate.toDate()).get());

            DBCollection collection = historicPriceConn().getCollection(stockName);
            collection.remove(query);
        });
    }

    private synchronized DB historicPriceConn() {
        if (dbConn == null) {
            dbConn = conn(STOCK_STATS, "MERVAL_STATS_URI");
        }
        return dbConn;
    }

}
