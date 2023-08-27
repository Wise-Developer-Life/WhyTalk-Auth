package com.wisedevlife.whytalkauth.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class JsonUtil {
    private JsonUtil() {}
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static Map<String, Object> toMap(Object object) {
        return OBJECT_MAPPER.convertValue(object, new TypeReference<>() {});
    }

    public static <T> T convertFromString(String jsonValue, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(jsonValue, clazz);
    }

    public static <T> T convertFromMap(Map<String, Object> jsonValue, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(jsonValue, clazz);
    }
}
