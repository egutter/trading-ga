package com.egutter.trading.out.adapters;

import com.egutter.trading.order.BuyOrder;
import com.egutter.trading.order.MarketOrder;
import com.google.gson.*;
import org.joda.time.LocalDate;

import java.lang.reflect.Type;

public class MarketOrderGsonAdapter implements JsonSerializer<MarketOrder>, JsonDeserializer<MarketOrder>  {

    @Override
    public MarketOrder deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        if (asJsonObject.get("type").equals(BuyOrder.class.toString())) {
            return new BuyOrder(asJsonObject.get("stockName").getAsString(),
                    null,
                    asJsonObject.get("numberOfShares").getAsInt(),
                    asJsonObject.get("price").getAsBigDecimal());
        }
        return null;
    }

    @Override
    public JsonElement serialize(MarketOrder marketOrder, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("type", new JsonPrimitive(marketOrder.getClass().toString()));
        jsonObject.add("stockName", new JsonPrimitive(marketOrder.getStockName()));
        jsonObject.add("numberOfShares", new JsonPrimitive(marketOrder.getNumberOfShares()));
        jsonObject.add("price", new JsonPrimitive(marketOrder.getPrice()));
        return jsonObject;
    }
}
