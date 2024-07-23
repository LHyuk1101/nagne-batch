package org.team.nagnebatch.place.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PlaceImg {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Place place;

  private String imgUrl;

  public Long getId() {
    return id;
  }

  public Place getPlace() {
    return place;
  }

  public String getImgUrl() {
    return imgUrl;
  }
}
