package org.team.nagnebatch.place.client.config;

import java.util.Arrays;
import java.util.List;

public enum ApiLink {
  GET_ATTRACTION("/areaBasedList1", Arrays.asList("serviceKey", "MobileApp", "MobileOS")),
  GET_FESTIVAL("/searchFestival1", Arrays.asList("serviceKey", "MobileApp", "MobileOS", "eventStartDate")),
  GET_INTRO("/detailIntro1", Arrays.asList("serviceKey", "MobileApp", "MobileOS", "contentId", "contentTypeId")),
  GET_IMAGES_DETAILS_INFO("detailImage1", Arrays.asList("serviceKey", "MobileApp", "MobileOS", "contentId"));

  private final String sufixUrl;
  private final List<String> requiredParams;

  ApiLink(String sufixUrl, List<String> requiredParams) {
    this.sufixUrl = sufixUrl;
    this.requiredParams = requiredParams;
  }
  public String getSufixUrl() {
    return sufixUrl;
  }

  public List<String> getRequiredParams() {
    return requiredParams;
  }
}
