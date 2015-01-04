package com.egutter.trading.repository;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.StockPrices;
import com.google.common.collect.Ordering;
import com.mongodb.*;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by egutter on 2/10/14.
 */
public class PortfolioRepository {

    private DB dbConn;

    public void forEachStockOn(String key, Consumer<BuyOrder> applyBlock) {
        DBCursor cursor = conn().getCollection(key).find();
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
                String stockName = (String) object.get("stock");
                Integer numberOfShares = (Integer) object.get("shares");
                applyBlock.accept(new BuyOrder(stockName, new DailyQuote(tradingDate, open, close, adjustedClose, low, high, volume), numberOfShares));
            }
        } finally {
            cursor.close();
        }
    }

    public void removeAll() {
        forEachCollection(collectionName -> {
            DBCollection collection = conn().getCollection(collectionName);
            DBObject query = new BasicDBObject();
            collection.remove(query);
        });
    }

    private void forEachCollection(Consumer<String> applyBlock) {
        Set<String> colls = conn().getCollectionNames();
        for (String stockName : colls) {
            if (stockName.equals("system.indexes")) {
                continue;
            }
            applyBlock.accept(stockName);
        }
    }

    public void update(String key, Portfolio portfolio) {
        DBCollection collection = conn().getCollection(key);
        clear(collection);

        Map<String, BuyOrder> stocks = portfolio.getStocks();
        for (String stockName : stocks.keySet()) {
            BuyOrder buyOrder = stocks.get(stockName);
            DailyQuote quote = buyOrder.getDailyQuote();
            BasicDBObject doc = new BasicDBObject("date", quote.getTradingDate().toDate())
                    .append("adjusted_close", quote.getAdjustedClosePrice())
                    .append("open", quote.getOpenPrice())
                    .append("high", quote.getHighPrice())
                    .append("low", quote.getLowPrice())
                    .append("close", quote.getClosePrice())
                    .append("volume", quote.getVolume())
                    .append("stock", stockName)
                    .append("shares", buyOrder.getNumberOfShares());
            collection.insert(doc);
        }
    }

    public void clear(DBCollection collection) {
        DBObject query = new BasicDBObject();
        collection.remove(query);
    }

    private DB conn() {
        try {
            if (dbConn == null) {
                Mongo client = new Mongo();
                dbConn = client.getDB("stock-portfolio");
            }
            return dbConn;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
