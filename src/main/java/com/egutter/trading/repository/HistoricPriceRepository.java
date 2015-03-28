package com.egutter.trading.repository;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Ordering;
import com.mongodb.*;
import org.joda.time.LocalDate;
import org.joda.time.ReadablePartial;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by egutter on 2/10/14.
 */
public class HistoricPriceRepository {

    private DB dbConn;

    public static void main(String[] args) {
        System.out.println(new HistoricPriceRepository().getMaxTradingDate());
    }

    public LocalDate getMaxTradingDate(String stockName) {
        DBCursor cursor = conn().getCollection(stockName).find().sort(new BasicDBObject("date", -1)).limit(1);

        LocalDate maxDate = LocalDate.now();
        try {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                maxDate = LocalDate.fromDateFields((Date) object.get("date"));
            }
        } finally {
            cursor.close();
        }
        return maxDate;
    }

    public LocalDate getMaxTradingDate() {
        List<LocalDate> maxDates = new ArrayList<LocalDate>();

        forEachStock(stockName -> {
            if (!stockName.equals("^MERV")) maxDates.add(getMaxTradingDate(stockName));
        });

        return Ordering.natural().max(maxDates);
    }

    public void insertStockPrices(StockPrices prices) {
        DBCollection collection = conn().getCollection(prices.getStockName());
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

    public void forEachStock(Consumer<String> applyBlock) {
        Set<String> colls = conn().getCollectionNames();
        for (String stockName : colls) {
            if (stockName.equals("system.indexes")) {
                continue;
            }
            applyBlock.accept(stockName);
        }
    }

    public void forEachDailyQuote(LocalDate fromDate, LocalDate toDate, String stockName, Consumer applyBlock) {
        DBObject query = new BasicDBObject();
        query.put("date", BasicDBObjectBuilder.start("$gte", fromDate.toDate()).add("$lte", toDate.toDate()).get());

        DBCursor cursor = conn().getCollection(stockName).find(query).sort(new BasicDBObject("date", 1));

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
        DBCollection collection = conn().getCollection(stockName);
        DBObject query = new BasicDBObject();
        collection.remove(query);
    }

    public void removeAll() {
        forEachStock(stockName -> {
            DBCollection collection = conn().getCollection(stockName);
            collection.drop();
        });
    }

    public void removeAllFrom(String stockName, LocalDate fromDate) {
        DBObject query = new BasicDBObject();
        query.put("date", BasicDBObjectBuilder.start("$gte", fromDate.toDate()).get());

        DBCollection collection = conn().getCollection(stockName);
        collection.remove(query);
    }

    public void removeAllAt(LocalDate fromDate) {
        forEachStock(stockName -> {
            DBObject query = new BasicDBObject();
            query.put("date", BasicDBObjectBuilder.start("$gte", fromDate.toDate()).get());

            DBCollection collection = conn().getCollection(stockName);
            collection.remove(query);
        });
    }

    private DB conn() {
        try {
            if (dbConn == null) {
                Mongo client = new Mongo();
                dbConn = client.getDB("merval-stats");
            }
            return dbConn;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
