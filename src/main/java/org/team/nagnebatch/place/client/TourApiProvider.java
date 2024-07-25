package org.team.nagnebatch.place.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.team.nagnebatch.place.client.config.ApiConfiguration;

public class TourApiProvider implements ApiConfiguration {

  private static final Logger log = LoggerFactory.getLogger(TourApiProvider.class);

  private final String API_NAME = "Tour API";
  private final String API_VERSION = "4.0";
  public static final String ENG_SERVICE_URL = "http://apis.data.go.kr/B551011/EngService1";
  public static final String KOR_SERVICE_URL = "";
  public static final int DEFAULT_PAGE_SIZE = 5000;

  //TODO 훗날에 LogService 따로 추가할 예정. 그래서 Provider로 분리함.

  @Value("${tourapi.key}")
  private String tourApiKey;

  @Override
  public String getApiName() {
    return this.API_NAME;
  }

  @Override
  public String getApiVersion() {
    return this.API_VERSION;
  }

  @Override
  public String getApiKeys() {
    return this.tourApiKey;
  }


}
