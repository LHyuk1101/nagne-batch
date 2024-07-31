package org.team.nagnebatch.place.domain.requestFestival;

public class FestivalDTO {
  private String addr1;

  private String contentId;

  private Long contentTypeId;

  private String firstImage;

  private String firstImage2;

  private double lat;

  private double lng;

  private String tel;

  private String title;

  private String areaCode;

  private String createdTime;

  private String eventStartDate;

  private String eventEndDate;

  public String getAddr1() {
    return addr1;
  }

  public String getContentId() {
    return contentId;
  }

  public Long getContentTypeId() {
    return contentTypeId;
  }

  public String getFirstImage() {
    return firstImage;
  }

  public String getFirstImage2() {
    return firstImage2;
  }

  public double getLat() {
    return lat;
  }

  public double getLng() {
    return lng;
  }

  public String getTel() {
    return tel;
  }

  public String getTitle() {
    return title;
  }

  public String getAreaCode() {
    return areaCode;
  }

  public String getCreatedTime() {
    return createdTime;
  }

  public String getEventStartDate() {
    return eventStartDate;
  }

  public String getEventEndDate() {
    return eventEndDate;
  }

  // Builder 클래스
  public static class Builder {
    private String addr1;
    private String contentId;
    private Long contentTypeId;
    private String firstImage;
    private String firstImage2;
    private double lat;
    private double lng;
    private String tel;
    private String title;
    private String areaCode;
    private String createdTime;
    private String eventStartDate;
    private String eventEndDate;

    public Builder addr1(String addr1) {
      this.addr1 = addr1;
      return this;
    }

    public Builder contentId(String contentId) {
      this.contentId = contentId;
      return this;
    }

    public Builder contentTypeId(Long contentTypeId) {
      this.contentTypeId = contentTypeId;
      return this;
    }

    public Builder firstImage(String firstImage) {
      this.firstImage = firstImage;
      return this;
    }

    public Builder firstImage2(String firstImage2) {
      this.firstImage2 = firstImage2;
      return this;
    }

    public Builder lat(double lat) {
      this.lat = lat;
      return this;
    }

    public Builder lng(double lng) {
      this.lng = lng;
      return this;
    }

    public Builder tel(String tel) {
      this.tel = tel;
      return this;
    }

    public Builder title(String title) {
      this.title = title;
      return this;
    }

    public Builder areaCode(String areaCode) {
      this.areaCode = areaCode;
      return this;
    }

    public Builder createdTime(String createdTime) {
      this.createdTime = createdTime;
      return this;
    }

    public Builder eventStartDate(String eventStartDate) {
      this.eventStartDate = eventStartDate;
      return this;
    }

    public Builder eventEndDate(String eventEndDate) {
      this.eventEndDate = eventEndDate;
      return this;
    }

    public FestivalDTO build() {
      FestivalDTO dto = new FestivalDTO();
      dto.addr1 = this.addr1;
      dto.contentId = this.contentId;
      dto.contentTypeId = this.contentTypeId;
      dto.firstImage = this.firstImage;
      dto.firstImage2 = this.firstImage2;
      dto.lat = this.lat;
      dto.lng = this.lng;
      dto.tel = this.tel;
      dto.title = this.title;
      dto.areaCode = this.areaCode;
      dto.createdTime = this.createdTime;
      dto.eventStartDate = this.eventStartDate;
      dto.eventEndDate = this.eventEndDate;
      return dto;
    }
  }

  public static Builder builder() {
    return new Builder();
  }
}
