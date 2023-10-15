package com.gomabot.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parse(final Class<T> clazz, final String text) throws JsonProcessingException {
        return mapper.readValue(text, clazz);
    }

    public static <T> String stringify(final T object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }
}
