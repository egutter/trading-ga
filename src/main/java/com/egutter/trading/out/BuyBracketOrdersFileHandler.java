package com.egutter.trading.out;

import com.egutter.trading.order.BuyBracketOrder;
import com.egutter.trading.order.MarketOrder;
import com.egutter.trading.out.adapters.BitStringGsonAdapter;
import com.egutter.trading.out.adapters.ClassTypeAdapterFactory;
import com.egutter.trading.out.adapters.JodaLocalDateGsonAdapter;
import com.egutter.trading.out.adapters.MarketOrderGsonAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.LocalDate;
import org.uncommons.maths.binary.BitString;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BuyBracketOrdersFileHandler {

    public static final String BUY_BRACKET_ORDERS_JSON_FILE_NAME = "buy_bracket_orders.json";
    public static final String BUY_BRACKET_ORDERS_JSON_FILE_DIR = "orders";
    private final Gson gson;
    private LocalDate tradingDate;

    public BuyBracketOrdersFileHandler() {
        this(LocalDate.now());
    }

    public BuyBracketOrdersFileHandler(LocalDate tradingDate) {
        this.tradingDate = tradingDate;
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
        gsonBuilder.registerTypeAdapter(BitString.class, new BitStringGsonAdapter());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaLocalDateGsonAdapter());
        gsonBuilder.registerTypeAdapter(MarketOrder.class, new MarketOrderGsonAdapter());
        gson = gsonBuilder.create();
    }

    public void toJson(List<BuyBracketOrder> orders) {
        writeFile(gson.toJson(orders));
    }

    public List<BuyBracketOrder> fromJson() {
        try {
            String fileName = buildFilePath();
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            Type listType = new TypeToken<ArrayList<BuyBracketOrder>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String buildFilePath() {
        return BUY_BRACKET_ORDERS_JSON_FILE_DIR + "/" + tradingDate.toString() + "_" + BUY_BRACKET_ORDERS_JSON_FILE_NAME;
    }

    private void writeFile(String json) {
        try {
            String fileName = buildFilePath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
