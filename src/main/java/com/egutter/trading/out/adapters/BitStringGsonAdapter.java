package com.egutter.trading.out.adapters;

import com.google.gson.*;
import org.uncommons.maths.binary.BitString;

import java.lang.reflect.Type;

public class BitStringGsonAdapter implements JsonSerializer<BitString>, JsonDeserializer<BitString> {
    @Override
    public JsonElement serialize(BitString bitString, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(bitString.toString());
    }

    @Override
    public BitString deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new BitString(jsonElement.getAsString());
    }
}
