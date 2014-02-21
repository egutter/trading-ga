package com.egutter.trading.stock;

import com.mongodb.*;
import org.joda.time.LocalDate;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by egutter on 2/17/14.
 */
public class StockMarketBuilder {

    // TODO: Receive Repository and from/to Dates
    public StockMarket build() {
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
                DBCollection coll = db.getCollection(stockName);
                DBObject first = coll.findOne();

                System.out.println("name " + stockName + " date: " + first.get("date"));

                DBCursor cursor = coll.find();

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

            return new StockMarket(stockPrices, marketIndexPrices);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
