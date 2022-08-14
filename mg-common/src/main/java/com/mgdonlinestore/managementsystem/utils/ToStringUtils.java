package com.mgdonlinestore.managementsystem.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public final class ToStringUtils {

  private static final String NULL = "null";

  private ObjectMapper objectMapper;

  private static ToStringUtils INSTANCE;

  public ToStringUtils() {}

  @Autowired
  public ToStringUtils(ObjectMapper objectMapper) {
    getInstance().objectMapper = objectMapper.copy();
  }

  private static ToStringUtils getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new ToStringUtils();
    }
    return INSTANCE;
  }

  public static String toString(Object ofInterest) {
    return toJson(ofInterest);
  }

  public static String reflectionToString(Object ofInterest) {
    return toJson(ofInterest);
  }

  public static String toJson(Object ofInterest) {
    String json = NULL;
    try {
      json = getInstance().objectMapper.writeValueAsString(ofInterest);
    } catch (JsonProcessingException e) {
      log.error("Exception while writing json from object", e);
    }
    return json;
  }

  public static <T> T toObjectType(String ofInterestJson, Class<T> clazz) {
    T object = null;
    if (ofInterestJson != null) {
      try {
        object = getInstance().objectMapper.readValue(ofInterestJson, clazz);
      } catch (JsonProcessingException e) {
        log.error("Exception while converting String to Object of Type " + clazz.getTypeName(), e);
      }
    }
    return object;
  }
}
