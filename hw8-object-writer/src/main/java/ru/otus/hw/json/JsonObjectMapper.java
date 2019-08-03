package ru.otus.hw.json;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class JsonObjectMapper implements ObjectMapper {

    @Override
    public String writeValueAsString(Object obj) throws IllegalAccessException {
        if (Objects.isNull(obj)) {
            throw new NullPointerException();
        }

        JsonObject parse = parse(obj);

        return Objects.requireNonNull(parse).toString();
    }

    private JsonObject parse(Object obj) throws IllegalAccessException {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            int modifiers = field.getModifiers();
            if (isNotStaticOrTransient(modifiers)) {
                builder.add(field.getName(), createJsonValue(field.getType(), field.get(obj)));
            }
        }

        return builder.build();
    }

    private JsonValue createJsonValue(Class<?> type, Object obj) {
        boolean isNull = Objects.isNull(obj);

        if (type.isArray()) {
            return isNull ? JsonArray.EMPTY_JSON_ARRAY : createArrayValue(obj);
        } else if (Collection.class.isAssignableFrom(type)) {
            Collection<?> collection = (Collection<?>) obj;
            return isNull ? JsonArray.EMPTY_JSON_ARRAY : createArrayValue(collection.toArray());
        } else if (Map.class.isAssignableFrom(type)) {
            return isNull ? JsonValue.EMPTY_JSON_OBJECT : createMapValue(obj);
        }

        return createPrimitiveValue(obj);

    }

    private JsonValue createMapValue(Object obj) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        Map<?, ?> map = (Map<?, ?>) obj;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            objectBuilder.add(key.toString(), createJsonValue(value.getClass(), value));
        }
        return objectBuilder.build();
    }

    private JsonArray createArrayValue(Object obj) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            Object value = Array.get(obj, i);
            arrayBuilder.add(createJsonValue(value.getClass(), value));
        }
        return arrayBuilder.build();
    }

    private JsonValue createPrimitiveValue(Object obj) {
        if (obj instanceof  Number) {
            Number number = (Number) obj;
            if (number instanceof Double || number instanceof Float) {
                return Json.createValue(number.doubleValue());
            } else {
                return Json.createValue(number.longValue());
            }
        } else if (obj instanceof Boolean) {
            return obj.equals(true) ? JsonValue.TRUE : JsonValue.FALSE;
        } else {
            return Json.createValue(obj.toString());
        }
    }

    private boolean isNotStaticOrTransient(int modifiers) {
        return !Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers);
    }
}
