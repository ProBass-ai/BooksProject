package utils;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;

public class RestHelper {
    public static  <T> T convertJsonToObject(String jsonString, Class<T> aClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, aClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
