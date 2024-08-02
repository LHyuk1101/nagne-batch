package org.team.nagnebatch.place.batch.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.team.nagnebatch.place.batch.util.ApiDataParser;
import org.team.nagnebatch.place.client.TourApiConnection;
import org.team.nagnebatch.place.client.config.ApiLink;
import org.team.nagnebatch.place.domain.requestAttraction.ResponseAttraction;
import org.team.nagnebatch.place.domain.requestFestival.ResponseFestival;
import org.team.nagnebatch.place.exception.ApiResponseException;

@Service
public class TourApiEngService extends TourApiConnection {

  private static final Logger log = LoggerFactory.getLogger(TourApiEngService.class);
  private final AttractionReader attractionReader;
  private final FestivalReader festivalReader;

  @Autowired
  public TourApiEngService(AttractionReader attractionReader, FestivalReader festivalReader) {
    this.attractionReader = attractionReader;
    this.festivalReader = festivalReader;
  }

  public ResponseAttraction getLocationBased(ApiLink apiLink, int page) {
    ResponseEntity<String> responseJsonData = super.getConnectJsonData(
        makeRequestURL(apiLink, page));
    return ApiDataParser.convertToAttraction(page, responseJsonData.getBody());
  }

  public ResponseFestival getFestivalByLocationBased(ApiLink apiLink, int page) {
    ResponseEntity<String> responseJsonData = super.getConnectJsonData(
        makeRequestURL(apiLink, page));
    return ApiDataParser.convertToFestival(page, responseJsonData.getBody());
  }

  private URI makeRequestURL(ApiLink apiLink, int page) {
    String url = TourApiConnection.ENG_SERVICE_URL + apiLink.getSufixUrl();
    String[] requiredQueryParams = apiLink.getRequiredParams()
        .toArray(new String[0]);

    //TODO 축제 검색 기준을 나중에 추가한다면 매개변수로 받아서 진행해야 합니다.
    String nowDateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    Map<String, String> queryParams = new HashMap<>();
    if (apiLink.name().equals(ApiLink.GET_ATTRACTION.name())) {
      queryParams.put(requiredQueryParams[0], super.getApiKeys());
      queryParams.put(requiredQueryParams[1], "AppTest");
      queryParams.put(requiredQueryParams[2], "ETC");
      queryParams.put("_type", "json");
      queryParams.put("pageNo", Integer.toString(page));
      queryParams.put("numOfRows", Integer.toString(TourApiConnection.DEFAULT_PAGE_SIZE));
      queryParams.put("contentTypeId", "76");
    } else if (apiLink.name().equals(ApiLink.GET_FESTIVAL.name())) {
      queryParams.put(requiredQueryParams[0], super.getApiKeys());
      queryParams.put(requiredQueryParams[1], "AppTest");
      queryParams.put(requiredQueryParams[2], "ETC");
      queryParams.put(requiredQueryParams[3], nowDateStr);
      queryParams.put("_type", "json");
      queryParams.put("pageNo", Integer.toString(page));
      queryParams.put("numOfRows", Integer.toString(TourApiConnection.DEFAULT_PAGE_SIZE));
    }

    UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url);

    for (Map.Entry<String, String> entry : queryParams.entrySet()) {
      urlBuilder.queryParam(entry.getKey(), entry.getValue());
    }

    try {
      URI builtUri = new URI(urlBuilder.build(false).toUriString());
      return builtUri;
    } catch (URISyntaxException e) {
      throw new ApiResponseException("URI NOT FOUND");
    }
  }

}
