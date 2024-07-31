package org.team.nagnebatch.place.batch.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.team.nagnebatch.place.client.TourApiProvider;
import org.team.nagnebatch.place.domain.requestAttraction.AttractionDTO;
import org.team.nagnebatch.place.domain.requestAttraction.ResponseAttraction;
import org.team.nagnebatch.place.domain.requestFestival.FestivalDTO;
import org.team.nagnebatch.place.domain.requestFestival.ResponseFestival;

public class ApiDataParser {

  private static final ObjectMapper mapper = new ObjectMapper();
   private static final Logger log = LoggerFactory.getLogger(ApiDataParser.class);

  public static ResponseAttraction convertToAttraction(int page, String jsonData) {
    List<AttractionDTO> attractions = new ArrayList<>();

    try {
      JsonNode rootNode = mapper.readTree(jsonData);
      JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
      boolean isNextPage = true;
      int totalCount = 0;

      if (!itemsNode.isEmpty()) {
        for (JsonNode itemNode : itemsNode) {
          AttractionDTO attraction = AttractionDTO.builder()
              .addr1(itemNode.path("addr1").asText())
              .contentId(itemNode.path("contentid").asText())
              .contentTypeId(Long.parseLong(itemNode.path("contenttypeid").asText()))
              .firstImage(itemNode.path("firstimage").asText())
              .firstImage2(itemNode.path("firstimage2").asText())
              .lat(Double.parseDouble(itemNode.path("mapy").asText()))
              .lng(Double.parseDouble(itemNode.path("mapx").asText()))
              .tel(itemNode.path("tel").asText())
              .title(itemNode.path("title").asText())
              .areaCode(itemNode.path("areacode").asText())
              .build();

          attractions.add(attraction);
        }
      }

      totalCount = rootNode.path("response").path("body").path("totalCount").asInt();
      isNextPage = totalCount > TourApiProvider.DEFAULT_PAGE_SIZE * (page - 1) + attractions.size();

      return new ResponseAttraction(attractions, totalCount, isNextPage);
    } catch (Exception e) {
     log.error(e.getMessage());
      // 에러 처리를 위한 방어 totalCount : -1 ;
      return new ResponseAttraction(new ArrayList<>(), -1, false);
    }
  }

  public static ResponseFestival convertToFestival(int page, String jsonData) {
    List<FestivalDTO> festivals = new ArrayList<>();

    try {
      JsonNode rootNode = mapper.readTree(jsonData);
      JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
      boolean isNextPage = true;
      int totalCount = 0;

      if (!itemsNode.isEmpty()) {
        for (JsonNode itemNode : itemsNode) {
          FestivalDTO festival = FestivalDTO.builder()
              .addr1(itemNode.path("addr1").asText())
              .contentId(itemNode.path("contentid").asText())
              .contentTypeId(Long.parseLong(itemNode.path("contenttypeid").asText()))
              .firstImage(itemNode.path("firstimage").asText())
              .firstImage2(itemNode.path("firstimage2").asText())
              .lat(Double.parseDouble(itemNode.path("mapy").asText()))
              .lng(Double.parseDouble(itemNode.path("mapx").asText()))
              .tel(itemNode.path("tel").asText())
              .title(itemNode.path("title").asText())
              .eventStartDate(itemNode.path("eventstartdate").asText())
              .eventEndDate(itemNode.path("eventenddate").asText())
              .areaCode(itemNode.path("areacode").asText())
              .build();

          festivals.add(festival);
        }
      }

      totalCount = rootNode.path("response").path("body").path("totalCount").asInt();
      isNextPage = totalCount > TourApiProvider.DEFAULT_PAGE_SIZE * (page - 1) + festivals.size();

      return new ResponseFestival(festivals, totalCount, isNextPage);
    } catch (Exception e) {
      log.error(e.getMessage());
      // 에러 처리를 위한 방어 totalCount : -1 ;
      return new ResponseFestival(new ArrayList<>(), -1, false);
    }
  }

}
