package com.egutter.trading.repository;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.StockPrices;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Optional;

/**
 * Created by egutter on 2/10/14.
 */
public class FitnessCacheRepository {

    private DB dbConn;

    public static void main(String[] args) {
        new FitnessCacheRepository().store("test", 123.2);
    }
    public Optional<Double> get(String key) {
        DBObject query = new BasicDBObject("key", key);

        DBCursor cursor = collection().find(query);
        try {
            while(cursor.hasNext()) {
                DBObject result = cursor.next();
                return Optional.of((Double) result.get("value"));
            }
        } finally {
            cursor.close();
        }
        return Optional.empty();
    }

    public void store(String key, Double value) {
        BasicDBObject doc = new BasicDBObject("key", key).append("value", value);
        collection().insert(doc);
    }

    private DBCollection collection() {
        return conn().getCollection("results");
    }

    private DB conn() {
        try {
            if (dbConn == null) {
                Mongo client = new Mongo();
                dbConn = client.getDB("fitnesse-cache");
            }
            return dbConn;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
