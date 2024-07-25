package org.team.nagnebatch.place.domain;

import jakarta.persistence.*;

@Entity
public class Store {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Place place;

  @Column(length = 500)
  private String openTime;

  private String contact;

  public Store(Long id, Place place, String openTime, String contact) {
    this.id = id;
    this.place = place;
    this.openTime = openTime;
    this.contact = contact;
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

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getOpenTime() {
    return openTime;
  }

  public void setOpenTime(String openTime) {
    this.openTime = openTime;
  }

}
