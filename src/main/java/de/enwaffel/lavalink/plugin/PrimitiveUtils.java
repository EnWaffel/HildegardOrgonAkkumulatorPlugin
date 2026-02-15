package de.enwaffel.lavalink.plugin;

import kotlinx.serialization.json.JsonElement;
import kotlinx.serialization.json.JsonObject;
import kotlinx.serialization.json.JsonPrimitive;
import org.jetbrains.annotations.Nullable;

public class PrimitiveUtils {

    public static boolean isTrue(JsonElement data, String key) {
        Boolean b = parseBooleanElement(data, key);
        return b != null && b;
    }

    public static boolean isGreaterThan(JsonElement data, String key, int y) {
        Integer i = parseIntElement(data, key);
        return i != null && i > y;
    }

    public static boolean isGreaterOrEqualsThan(JsonElement data, String key, float y) {
        Float f = parseFloatElement(data, key);
        return f != null && f >= y;
    }

    @Nullable
    public static Boolean parseBooleanElement(JsonElement data, String key) {
        if (!(data instanceof JsonObject obj)) {
            return null;
        }

        JsonElement element = obj.get(key);

        if (!(element instanceof JsonPrimitive elementValue)) {
            return null;
        }

        return Boolean.parseBoolean(elementValue.getContent());
    }

    @Nullable
    public static Integer parseIntElement(JsonElement data, String key) {
        if (!(data instanceof JsonObject obj)) {
            return null;
        }

        JsonElement element = obj.get(key);

        if (!(element instanceof JsonPrimitive elementValue)) {
            return null;
        }

        try {
            return Integer.parseInt(elementValue.getContent());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Nullable
    public static Float parseFloatElement(JsonElement data, String key) {
        if (!(data instanceof JsonObject obj)) {
            return null;
        }

        JsonElement element = obj.get(key);

        if (!(element instanceof JsonPrimitive elementValue)) {
            return null;
        }

        try {
            return Float.parseFloat(elementValue.getContent());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Nullable
    public static String parseStringElement(JsonElement data, String key) {
        if (!(data instanceof JsonObject obj)) {
            return null;
        }

        JsonElement element = obj.get(key);

        if (!(element instanceof JsonPrimitive elementValue)) {
            return null;
        }

        return elementValue.getContent();
    }

}
