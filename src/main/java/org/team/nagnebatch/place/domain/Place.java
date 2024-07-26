package org.team.nagnebatch.place.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Place {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String address;

  private String title;

  private int contentId;

  private int contentTypeId;

  private double latitude;

  private double longitude;

//  @Enumerated(EnumType.STRING)
//  private ApiType apiType;

  protected Place() {}

  public Place(String address, String title, int contentId, int contentTypeId,
               double latitude, double longitude) {
    this.address = address;
    this.title = title;
    this.contentId = contentId;
    this.contentTypeId = contentTypeId;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Place(Long id, String address, String title, int contentId, int contentTypeId,
               double latitude, double longitude) {
    this.id = id;
    this.address = address;
    this.title = title;
    this.contentId = contentId;
    this.contentTypeId = contentTypeId;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public void setContentTypeId(int contentTypeId) {
    this.contentTypeId = contentTypeId;
  }

  public void setContentId(int contentId) {
    this.contentId = contentId;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAddress(String address1) {
    this.address = address1;
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
