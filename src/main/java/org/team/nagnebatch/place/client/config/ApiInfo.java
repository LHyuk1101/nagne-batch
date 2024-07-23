package org.team.nagnebatch.place.client.config;

public enum ApiInfo {
  ENG_SERVICE("ENG"),
  KOR_SERVICE("KOR");

  private String apiInfo;

  private ApiInfo(String apiInfo) {}

  public String getApiInfo() {
    return apiInfo;
  }


}
