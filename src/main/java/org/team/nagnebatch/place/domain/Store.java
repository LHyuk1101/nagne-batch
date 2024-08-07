package org.team.nagnebatch.place.domain;

import jakarta.persistence.*;

@Entity
public class Store {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "store_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Place place;

  @Column(length = 1000)
  private String openTime;

  @Column(length = 50)
  private String infocenter;

  public Store(Long id, Place place, String openTime, String infocenter) {
    this.id = id;
    this.place = place;
    this.openTime = openTime;
    this.infocenter = infocenter;
  }

  public Store() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Place getPlace() {
    return place;
  }

  public void setPlace(Place place) {
    this.place = place;
  }

  public String getInfocenter() {
    return infocenter;
  }

  public void setInfocenter(String contact) {
    this.infocenter = contact;
  }

  public String getOpenTime() {
    return openTime;
  }

  public void setOpenTime(String openTime) {
    this.openTime = openTime;
  }

}
