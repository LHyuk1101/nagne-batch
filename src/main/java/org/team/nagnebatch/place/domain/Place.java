package org.team.nagnebatch.place.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Place {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String address1;

  private String title;

  private int contentId;

  private int contentTypeId;

  private double latitude;

  private double longitude;

//  @Enumerated(EnumType.STRING)
//  private ApiType apiType;

  protected Place() {}

  public Place(String address1, String title, int contentId, int contentTypeId,
      double latitude, double longitude) {
    this.address1 = address1;
    this.title = title;
    this.contentId = contentId;
    this.contentTypeId = contentTypeId;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Place(Long id, String address1, String title, int contentId, int contentTypeId,
      double latitude, double longitude) {
    this.id = id;
    this.address1 = address1;
    this.title = title;
    this.contentId = contentId;
    this.contentTypeId = contentTypeId;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Long getId() {
    return id;
  }

  public String getAddress1() {
    return address1;
  }

  public String getTitle() {
    return title;
  }

  public int getContentId() {
    return contentId;
  }

  public int getContentTypeId() {
    return contentTypeId;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
}
