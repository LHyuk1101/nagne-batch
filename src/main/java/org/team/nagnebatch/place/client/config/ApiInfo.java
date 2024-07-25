package org.team.nagnebatch.place.client.config;

public enum ApiInfo {

  AttractContentTypeId(67, "관광지"),
  FestivalContentTypeId(85, "축제"),
  StayContentTypeId(80, "숙박");


  private int contentTypeId;

  private String contentTypeName;
  private ApiInfo(int apiInfo, String contentTypeName) {}

  public int getContentTypeId() {
    return contentTypeId;
  }

  public String getContentTypeName() {
    return contentTypeName;
  }
}
