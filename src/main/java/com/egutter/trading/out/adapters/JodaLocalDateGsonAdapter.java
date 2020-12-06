package com.egutter.trading.out.adapters;

import com.google.gson.*;
import org.joda.time.LocalDate;

import java.lang.reflect.Type;

public class JodaLocalDateGsonAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        return new LocalDate(asJsonObject.get("year").getAsInt(),
                asJsonObject.get("month").getAsInt(),
                asJsonObject.get("day").getAsInt());
    }

    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("year", new JsonPrimitive(localDate.getYear()));
        jsonObject.add("month", new JsonPrimitive(localDate.getMonthOfYear()));
        jsonObject.add("day", new JsonPrimitive(localDate.getDayOfMonth()));
        return jsonObject;
    }
}
