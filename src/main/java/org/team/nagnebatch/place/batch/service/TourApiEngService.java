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
import org.team.nagnebatch.place.client.TourApiConnectionDecorator;
import org.team.nagnebatch.place.client.config.ApiLink;
import org.team.nagnebatch.place.domain.requestAttraction.ResponseAttraction;
import org.team.nagnebatch.place.domain.requestFestival.ResponseFestival;
import org.team.nagnebatch.place.domain.requestIntro.ResponseIntro;
import org.team.nagnebatch.place.exception.ApiResponseException;

@Service
public class TourApiEngService {

  private static final Logger log = LoggerFactory.getLogger(TourApiEngService.class);
  private final AttractionReader attractionReader;
  private final FestivalReader festivalReader;
  private final TourApiConnectionDecorator tourApiConnectionDecorator;

  @Autowired
  public TourApiEngService(AttractionReader attractionReader, FestivalReader festivalReader,
      TourApiConnectionDecorator tourApiConnectionDecorator) {
    this.attractionReader = attractionReader;
    this.festivalReader = festivalReader;
    this.tourApiConnectionDecorator = tourApiConnectionDecorator;
  }

  public ResponseAttraction getLocationBased(ApiLink apiLink, int page) {

    // 1. Attraction에 대한 정보를 가져온다.
    ResponseEntity<String> connectAttractionJsonData = tourApiConnectionDecorator.getConnectJsonData(
        makeRequestURL(apiLink, page));

    // 2. Attraction에 대한 정보를 알려준다.
    ResponseAttraction responseAttraction = ApiDataParser.convertToAttraction(page,
        connectAttractionJsonData.getBody());

    if (responseAttraction.getAttractions().size() == 0) {
      throw new ApiResponseException("Attraction not found");
    }

    // 3. Attraction에 불러온 아이템을 추가한다.
    responseAttraction.getAttractions()
        .stream()
        .filter(fest -> fest.getAreaCode() != null && !fest.getAreaCode().isEmpty())
        .forEach(item -> {
          int contentId = Integer.parseInt(item.getContentId());
          // 3 - 1. ApiConnection
          ResponseEntity<String> connectIntroJsonData = tourApiConnectionDecorator.getConnectJsonData(
              makeRequestURL(ApiLink.GET_INTRO, (long) contentId));
          // 3 - 2. Convert Data
          ResponseIntro responseIntro = ApiDataParser.convertToDetailData(
              connectIntroJsonData.getBody());
          log.info("Call Api: {}, overview : {}", apiLink, responseIntro.getOverview());
          item.addContent(responseIntro.getOverview());

        });

    log.info("API 디테일 정보를 받고 있습니다. {}", responseAttraction.getAttractions().size());

    return responseAttraction;

  }

  public ResponseFestival getFestivalByLocationBased(ApiLink apiLink, int page) {
    ResponseEntity<String> responseJsonData = tourApiConnectionDecorator.getConnectJsonData(
        makeRequestURL(apiLink, page));

    ResponseFestival festival = ApiDataParser.convertToFestival(page, responseJsonData.getBody());

    if (festival.getFestivals().size() == 0) {
      throw new ApiResponseException("Festival not found");
    }

    // 3. Festival에 불러온 아이템을 추가한다.
    festival.getFestivals()
        .stream()
        .filter(fest -> fest.getAreaCode() != null && !fest.getAreaCode().isEmpty())
        .forEach(item -> {
          int contentId = Integer.parseInt(item.getContentId());

          // 3 - 1. ApiConnection
          ResponseEntity<String> connectIntroJsonData = tourApiConnectionDecorator.getConnectJsonData(
              makeRequestURL(ApiLink.GET_INTRO, (long) contentId));

          // 3 - 2. Convert Data
          ResponseIntro responseIntro = ApiDataParser.convertToDetailData(
              connectIntroJsonData.getBody());
          log.info("Call Api: {}, overview : {}", apiLink, responseIntro.getOverview());
          item.addOverview(responseIntro.getOverview());

        });

    log.info("API 디테일 정보를 받고 있습니다. {}", festival.getFestivals().size());

    return festival;
  }

  private URI makeRequestURL(ApiLink apiLink, int page) {
    String url = TourApiConnection.ENG_SERVICE_URL + apiLink.getSufixUrl();
    String[] requiredQueryParams = apiLink.getRequiredParams()
        .toArray(new String[0]);

    //TODO 축제 검색 기준을 나중에 추가한다면 매개변수로 받아서 진행해야 합니다.
    String nowDateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    Map<String, String> queryParams = new HashMap<>();
    if (apiLink.name().equals(ApiLink.GET_ATTRACTION.name())) {
      queryParams.put(requiredQueryParams[0], tourApiConnectionDecorator.getApiKey());
      queryParams.put(requiredQueryParams[1], "AppTest");
      queryParams.put(requiredQueryParams[2], "ETC");
      queryParams.put("_type", "json");
      queryParams.put("pageNo", Integer.toString(page));
      queryParams.put("numOfRows", Integer.toString(TourApiConnection.DEFAULT_PAGE_SIZE));
      queryParams.put("contentTypeId", "76");
    } else if (apiLink.name().equals(ApiLink.GET_FESTIVAL.name())) {
      queryParams.put(requiredQueryParams[0], tourApiConnectionDecorator.getApiKey());
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

  private URI makeRequestURL(ApiLink apiLink, Long contentId) {
    String url = TourApiConnection.ENG_SERVICE_URL + apiLink.getSufixUrl();
    String[] requiredQueryParams = apiLink.getRequiredParams()
        .toArray(new String[0]);

    Map<String, String> queryParams = new HashMap<>();
    String rotateApikey = tourApiConnectionDecorator.getApiKey();
    if (apiLink.name().equals(ApiLink.GET_INTRO.name())) {
      queryParams.put(requiredQueryParams[0], rotateApikey);
      queryParams.put(requiredQueryParams[1], "AppTest");
      queryParams.put(requiredQueryParams[2], "ETC");
      queryParams.put(requiredQueryParams[3], contentId.toString());
      queryParams.put("_type", "json");
      queryParams.put("overviewYN", "Y");
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
