package org.team.nagnebatch.place.domain;

import jakarta.persistence.*;

@Entity
public class PlaceImg {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Place place;

  @Column(length = 500)
  private String imgUrl;

  public Long getId() {
    return id;
  }

  public Place getPlace() {
    return place;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public PlaceImg(Place place, String imgUrl) {
    this.place = place;
    this.imgUrl = imgUrl;
  }
}
