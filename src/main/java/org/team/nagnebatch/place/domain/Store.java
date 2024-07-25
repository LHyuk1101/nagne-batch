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
}
