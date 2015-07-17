package com.egutter.trading.repository;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.BuySellOperation;
import com.egutter.trading.order.MarketOrder;
import com.egutter.trading.order.SellOrder;
import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.PortfolioStats;
import com.mongodb.*;
import org.joda.time.LocalDate;

import java.lang.reflect.Constructor;
import java.net.UnknownHostException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by egutter on 2/10/14.
 */
public class PortfolioRepository extends MongoRepository {

    private static DB stockPortfolioConn;
    private static DB statsPortfolioConn;


    public void forEachStatOn(String key, Consumer<BuySellOperation> applyBlock) {
        DBCursor cursor = statsPortfolioConn().getCollection(key).find();
        try {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                BuyOrder buyOrder = (BuyOrder) buildMarketOrder((DBObject) object.get("buyOrder"), BuyOrder.class);
                SellOrder sellOrder = (SellOrder) buildMarketOrder((DBObject) object.get("sellOrder"), SellOrder.class);
                applyBlock.accept(new BuySellOperation(buyOrder, sellOrder));
            }
        } finally {
            cursor.close();
        }
    }

    public void forEachStat(String key, Consumer<BuySellOperation> applyBlock) {
        DBCursor cursor = statsPortfolioConn().getCollection(key).find();
        try {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                BuyOrder buyOrder = (BuyOrder) buildMarketOrder((DBObject) object.get("buyOrder"), BuyOrder.class);
                SellOrder sellOrder = (SellOrder) buildMarketOrder((DBObject) object.get("sellOrder"), SellOrder.class);
                applyBlock.accept(new BuySellOperation(buyOrder, sellOrder));
            }
        } finally {
            cursor.close();
        }
    }

    public void forEachStock(BiConsumer<String, BuyOrder> applyBlock) {
        forEachStockCollection(key -> {
            DBCursor cursor = stockPortfolioConn().getCollection(key).find();
            try {
                while (cursor.hasNext()) {
                    DBObject object = cursor.next();
                    BuyOrder buyOrder = (BuyOrder) buildMarketOrder(object, BuyOrder.class);
                    applyBlock.accept(key, buyOrder);
                }
            } finally {
                cursor.close();
            }
        });
    }


    public void forEachStockOn(String key, Consumer<BuyOrder> applyBlock) {
        DBCursor cursor = stockPortfolioConn().getCollection(key).find();
        try {
            while (cursor.hasNext()) {
                DBObject object = cursor.next();
                BuyOrder buyOrder = (BuyOrder) buildMarketOrder(object, BuyOrder.class);
                applyBlock.accept(buyOrder);
            }
        } finally {
            cursor.close();
        }
    }


    public void removeAllStatsSoldAt(LocalDate dayToRollback) {
        forEachStatCollection(key -> {
            forEachStat(key, buySellOperation -> {
                DBCollection collection = statsPortfolioConn().getCollection(key);
                DBObject query = new BasicDBObject();
                query.put("date", dayToRollback.toDate());
                collection.remove(query);
            });
        });
    }

    public void removeAllStocksAt(LocalDate dayToRollback) {
        forEachStockCollection(collectionName -> {
            DBCollection collection = stockPortfolioConn().getCollection(collectionName);
            DBObject query = new BasicDBObject();
            query.put("date", dayToRollback.toDate());
            collection.remove(query);
        });
    }

    public void removeAll() {
        forEachStockCollection(collectionName -> {
            DBCollection collection = stockPortfolioConn().getCollection(collectionName);
            DBObject query = new BasicDBObject();
            collection.remove(query);
        });
    }

    public void forEachStockCollection(Consumer<String> applyBlock) {
        Set<String> colls = stockPortfolioConn().getCollectionNames();
        for (String stockName : colls) {
            if (stockName.equals("system.indexes") || stockName.equals("objectlabs-system")) {
                continue;
            }
            applyBlock.accept(stockName);
        }
    }

    public void forEachStatCollection(Consumer<String> applyBlock) {
        Set<String> colls = statsPortfolioConn().getCollectionNames();
        for (String key : colls) {
            if (key.equals("system.indexes")) {
                continue;
            }
            applyBlock.accept(key);
        }
    }

    public void update(String key, Portfolio portfolio) {
        updateStocks(key, portfolio);
        updateStats(key, portfolio);
    }

    private void updateStats(String key, Portfolio portfolio) {
        DBCollection collection = statsPortfolioConn().getCollection(key);
        clear(collection);

        PortfolioStats stats = portfolio.getStats();
        stats.forEachStat(buySellOperation -> {
            BuyOrder buyOrder = buySellOperation.getBuyOrder();
            SellOrder sellOrder = buySellOperation.getSellOrder();
            BasicDBObject buyDoc = buildOrderDocObject(buyOrder);
            BasicDBObject sellDoc = buildOrderDocObject(sellOrder);
            BasicDBObject doc = new BasicDBObject("buyOrder", buyDoc).append("sellOrder", sellDoc);
            collection.insert(doc);
        });
    }

    private void updateStocks(String key, Portfolio portfolio) {
        DBCollection collection = stockPortfolioConn().getCollection(key);
        clear(collection);

        Map<String, BuyOrder> stocks = portfolio.getStocks();
        for (String stockName : stocks.keySet()) {
            BuyOrder buyOrder = stocks.get(stockName);
            insertOrder(collection, buyOrder);
        }
    }

    private void insertOrder(DBCollection collection, MarketOrder order) {
        BasicDBObject doc = buildOrderDocObject(order);
        collection.insert(doc);
    }

    private BasicDBObject buildOrderDocObject(MarketOrder order) {
        DailyQuote quote = order.getDailyQuote();
        return new BasicDBObject("date", quote.getTradingDate().toDate())
                .append("adjusted_close", quote.getAdjustedClosePrice())
                .append("open", quote.getOpenPrice())
                .append("high", quote.getHighPrice())
                .append("low", quote.getLowPrice())
                .append("close", quote.getClosePrice())
                .append("volume", quote.getVolume())
                .append("stock", order.getStockName())
                .append("shares", order.getNumberOfShares());
    }

    private MarketOrder buildMarketOrder(DBObject object, Class marketOrderClass) {
        LocalDate tradingDate = LocalDate.fromDateFields((Date) object.get("date"));
        Double adjustedClose = (Double) object.get("adjusted_close");
        Double open = (Double) object.get("open");
        Double low = (Double) object.get("low");
        Double high = (Double) object.get("high");
        Double close = (Double) object.get("close");
        Long volume = (Long) object.get("volume");
        String stockName = (String) object.get("stock");
        Integer numberOfShares = (Integer) object.get("shares");
        try {
            Constructor c = marketOrderClass.getConstructor(String.class, DailyQuote.class, int.class);
            return (MarketOrder) c.newInstance(stockName, new DailyQuote(tradingDate, open, close, adjustedClose, low, high, volume), numberOfShares);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clear(DBCollection collection) {
        DBObject query = new BasicDBObject();
        collection.remove(query);
    }

    private synchronized DB stockPortfolioConn() {
        if (stockPortfolioConn == null) {
            stockPortfolioConn = conn("stock-portfolio", "STOCK_PORTFOLIO_URI");
        }
        return stockPortfolioConn;
    }

    private synchronized DB statsPortfolioConn() {
        if (statsPortfolioConn == null) {
            statsPortfolioConn = conn("stats-portfolio", "STATS_PORTFOLIO_URI");
        }
        return statsPortfolioConn;
    }

}
