package org.team.nagnebatch.place.client;

import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.team.nagnebatch.place.batch.service.ApiService;
import org.team.nagnebatch.place.client.config.ApiConfiguration;
import org.team.nagnebatch.place.exception.ApiResponseException;

public class TourApiConnection implements ApiConfiguration,  ApiService<String> {

  private static final Logger log = LoggerFactory.getLogger(TourApiConnection.class);

  private final String API_NAME = "Tour API";
  private final String API_VERSION = "4.0";
  public static final String ENG_SERVICE_URL = "http://apis.data.go.kr/B551011/EngService1";
  public static final String KOR_SERVICE_URL = "";
  public static final int DEFAULT_PAGE_SIZE = 5000;

  @Autowired
  private RestTemplate restTemplate;
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
  public ResponseEntity<String> getConnectJsonData(URI requestUrl) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
    HttpEntity<String> entity = new HttpEntity<>(headers);
    try {
      return restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);
    } catch (HttpClientErrorException e) {
      log.error("요청이 정상적으로 처리되지 못했습니다\n상태코드 : {}, 응답메세지 : {}", e.getStatusCode(), e.getMessage());
      throw new ApiResponseException(e.getMessage());
    }catch(ResourceAccessException e){
      try {
        Thread.sleep(5000);
      }catch (InterruptedException ex) {
        log.error(ex.getMessage());
      }
      return restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);
    }
  }

  @Override
  public String getApiKeys() {
    return this.tourApiKey;
  }


}
