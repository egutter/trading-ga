package com.egutter.trading.out;

import com.egutter.trading.order.BuyBracketOrder;
import com.egutter.trading.order.MarketOrder;
import com.egutter.trading.out.adapters.BitStringGsonAdapter;
import com.egutter.trading.out.adapters.ClassTypeAdapterFactory;
import com.egutter.trading.out.adapters.JodaLocalDateGsonAdapter;
import com.egutter.trading.out.adapters.MarketOrderGsonAdapter;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
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
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BuyBracketOrdersFileHandler {

    public static final String BUY_BRACKET_ORDERS_JSON_FILE_NAME = "buy_bracket_orders.json";
    public static final String BUY_BRACKET_ORDERS_JSON_FILE_DIR = "orders";
    private final Gson gson;
    private LocalDate tradingDate;
    private String baseOrdersPath;

    public BuyBracketOrdersFileHandler() {
        this(LocalDate.now(), "");
    }
    public BuyBracketOrdersFileHandler(LocalDate tradeOn) {
        this(tradeOn, "");
    }

    public BuyBracketOrdersFileHandler(LocalDate tradingDate, String baseOrdersPath) {
        this.tradingDate = tradingDate;
        this.baseOrdersPath = baseOrdersPath;
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting().addSerializationExclusionStrategy(buildExclusionStrategy());
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
        String fileName = buildFilePath();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            Type listType = new TypeToken<ArrayList<BuyBracketOrder>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (NoSuchFileException e) {
            System.out.println("No file to open " + fileName);
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String buildFilePath() {
        return baseOrdersPath + BUY_BRACKET_ORDERS_JSON_FILE_DIR + "/" + tradingDate.toString() + "_" + BUY_BRACKET_ORDERS_JSON_FILE_NAME;
    }

    private void writeFile(String json) {
        try {
            String fileName = buildFilePath();
//            String fileUri = ClassLoader.getSystemResource(fileName).toURI().getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private ExclusionStrategy buildExclusionStrategy() {
        return new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getName().equals("stockSymbols");
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        };
    }
}
