package org.team.nagnebatch.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import org.team.nagnebatch.utils.BaseEntity;

@Entity
public class Place extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 200)
  private String address;

  @Column(length = 200)
  private String title;

  @Column(unique = true)
  private String contentId;

  private int contentTypeId;

  @Column(columnDefinition = "LONGTEXT")
  private String overview;

  private double lat;

  private double lng;

  private int likes;

  @Enumerated(EnumType.STRING)
  private ApiType apiType;

  @ManyToOne(fetch = FetchType.LAZY)
  private Area area;


  private LocalDateTime modifiedTime;

  @Column(length = 200)
  private String thumbnailUrl;

  protected Place() {}

  // 지역코드 기반 insert
  public Place(String address, String title, String contentId, int contentTypeId, double latitude, double longitude, Area area) {
    this.address = address;
    this.title = title;
    this.contentId = contentId;
    this.contentTypeId = contentTypeId;
    this.lat = latitude;
    this.lng = longitude;
    this.area = area;
  }

  public Place(Long id, String address, String title, String contentId, int contentTypeId,
               double latitude, double longitude) {
    this.id = id;
    this.address = address;
    this.title = title;
    this.contentId = contentId;
    this.contentTypeId = contentTypeId;
    this.lat = latitude;
    this.lng = longitude;
  }

  public Place(String address, String title, String contentId, double lat, int contentTypeId,
      double lng, Area area, ApiType apiType, String overview, String thumbnailUrl, LocalDateTime modifiedDate) {
    this.address = address;
    this.title = title;
    this.contentId = contentId;
    this.lat = lat;
    this.contentTypeId = contentTypeId;
    this.lng = lng;
    this.area = area;
    this.apiType = apiType;
    this.overview = overview;
    this.thumbnailUrl = thumbnailUrl;
    this.modifiedTime = modifiedDate;
  }

  public Long getId() {
    return id;
  }

  public String getAddress() {
    return address;
  }

  public String getTitle() {
    return title;
  }

  public String getContentId() {
    return contentId;
  }

  public int getContentTypeId() {
    return contentTypeId;
  }

  public double getLatitude() {
    return lat;
  }

  public double getLongitude() {
    return lng;
  }

  public ApiType getApiType() {
    return apiType;
  }

  public void setApiType(ApiType apiType) {
    this.apiType = apiType;
  }
}
