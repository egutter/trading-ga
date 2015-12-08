package com.egutter.trading.repository;

import com.egutter.trading.stock.DailyQuote;
import com.mongodb.*;
import org.joda.time.LocalDate;

import java.util.Date;

/**
 * Created by egutter on 12/2/15.
 */
public class MarketOrdersRepository extends MongoRepository {
    private static DB dbConn;


    public void storeOrder(String key, String stockName) {
        DBCollection collection = marketOrderConn().getCollection(key);
        BasicDBObject doc = new BasicDBObject("date", LocalDate.now().toDate())
                .append("stock", stockName);

        collection.insert(doc);
    }

    public boolean popStock(String key, String stockName) {
        BasicDBObject query = new BasicDBObject("stock", stockName);

        DBCollection collection = marketOrderConn().getCollection(key);
        DBCursor cursor = collection.find(query);
        boolean found = false;

        try {
            while (cursor.hasNext()) {
                DBObject marketOrder = cursor.next();
                collection.remove(marketOrder);
                found = true;
            }
        } finally {
            cursor.close();
        }

        return found;
    }

    private synchronized DB marketOrderConn() {
        if (dbConn == null) {
            dbConn = conn("market-orders", "MARKET_ORDERS_URI");
        }
        return dbConn;
    }

}
