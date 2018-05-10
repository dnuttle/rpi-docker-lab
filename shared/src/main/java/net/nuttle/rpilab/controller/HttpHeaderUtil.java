package net.nuttle.rpilab.controller;

import static net.nuttle.rpilab.config.Constants.CONTENT_TYPE_APP_JSON;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class HttpHeaderUtil {
  public void checkContentHeader(String contentType) {
    if (!MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
      throw new InvalidHeaderException("Value for Content-Type header was " + contentType
        + ", expected " + CONTENT_TYPE_APP_JSON);
    }
  }
}
