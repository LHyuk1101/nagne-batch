package org.team.nagnebatch.place.batch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.team.nagnebatch.place.batch.util.ApiDataParser;
import org.team.nagnebatch.place.client.TourApiProvider;
import org.team.nagnebatch.place.client.config.ApiLink;
import org.team.nagnebatch.place.domain.requestAttraction.ResponseAttraction;
import org.team.nagnebatch.place.exception.ApiResponseException;

@Service
public class TourApiEngService extends TourApiProvider implements BatchApiService {

  private static final Logger log = LoggerFactory.getLogger(TourApiEngService.class);
  private final AttractionReader attractionReader;
  private final FestivalReader festivalReader;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  public TourApiEngService(AttractionReader attractionReader, FestivalReader festivalReader) {
    this.attractionReader = attractionReader;
    this.festivalReader = festivalReader;
  }

  public ResponseAttraction getLocationBased(int page) {
    ResponseEntity<String> responseJsonData = callTourApi(makeRequestURL(page));
    return ApiDataParser.convertToLocation(page, responseJsonData.getBody());
  }

  private URI makeRequestURL(int page)  {
    String url = getServiceUrl() + ApiLink.GET_ATTRACTION.getSufixUrl();
    String[] requiredQueryParams = ApiLink.GET_ATTRACTION.getRequiredParams()
        .toArray(new String[0]);

    Map<String, String> queryParams = new HashMap<>();
    queryParams.put(requiredQueryParams[0], super.getApiKeys());
    queryParams.put(requiredQueryParams[1], "AppTest");
    queryParams.put(requiredQueryParams[2], "ETC");
    queryParams.put("_type", "json");
    queryParams.put("pageNo", Integer.toString(page));
    queryParams.put("numOfRows", Integer.toString(TourApiProvider.DEFAULT_PAGE_SIZE));
    queryParams.put("contentTypeId", "76");

    UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url);

    for (Map.Entry<String, String> entry : queryParams.entrySet()) {
      urlBuilder.queryParam(entry.getKey(), entry.getValue());
    }

    try {
      URI builtUri = new URI(urlBuilder.build(false).toUriString());
      return  builtUri;
    }catch(URISyntaxException e) {
      throw new ApiResponseException("URI NOT FOUND");
    }
  }

  protected ResponseEntity<String> callTourApi(URI requestUrl) {
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
  public String getServiceUrl() {
    return TourApiProvider.ENG_SERVICE_URL;
  }
}
