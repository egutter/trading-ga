package com.egutter.trading.finnhub;

import com.egutter.trading.out.adapters.ClassTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public class FinnhubClient {

    private final static String BASE_URL = "https://finnhub.io/api/v1/";
    private final static String SENTINMENT_URL = BASE_URL + "news-sentiment";
    private final static String SUPPORT_RESISTANCE_URL = BASE_URL + "scan/support-resistance";
    private static final String AGGREGATE_INDICATOR_URL = BASE_URL + "scan/technical-indicator";
    private final Gson gson;
    private String apiToken;
    private CloseableHttpClient client;

    public FinnhubClient() {
        this.client = HttpClients.createDefault();
        this.apiToken = "c0knd0f48v6und6roslg";
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
        this.gson = gsonBuilder.create();
    }

    public NewsSentiment newsSentimentFor(String stock) {
        return fetchFinnhubDataFrom(stock, NewsSentiment.class, (symbol) -> buildSentimentUrl(symbol));
    }

    public SupportResistance supportResistanceFor(String stock) {
        return fetchFinnhubDataFrom(stock, SupportResistance.class, (symbol) -> buildSupportResistanceUrl(symbol));
    }

    public AggregateIndicator aggregateIndicator(String stock) {
        return fetchFinnhubDataFrom(stock, AggregateIndicator.class, (symbol) -> buildAggregateIndicatorUrl(symbol));
    }

    public <T> T fetchFinnhubDataFrom(String stock, Class<T> classOfT, Function<String, String> urlBuilder) {
        try {
            HttpGet request = new HttpGet(urlBuilder.apply(stock));
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            String sentimentJson = EntityUtils.toString(entity);
            Reader reader = new StringReader(sentimentJson);
            return gson.fromJson(reader, classOfT);
        } catch (Exception e) {
            try {
                return classOfT.getConstructor().newInstance();
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }
    }

    private String buildSentimentUrl(String stock) {
        return addApiToken(SENTINMENT_URL + "?symbol=" +stock);
    }

    private String buildSupportResistanceUrl(String stock) {
        return addApiToken(SUPPORT_RESISTANCE_URL + "?resolution=D&symbol=" +stock);
    }

    private String buildAggregateIndicatorUrl(String stock) {
        return addApiToken(AGGREGATE_INDICATOR_URL + "?resolution=D&symbol=" + stock);
    }
    
    private String addApiToken(String url) {
        return url + "&token=" + this.apiToken;
    }
}
