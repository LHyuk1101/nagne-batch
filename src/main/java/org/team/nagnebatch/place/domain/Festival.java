package org.team.nagnebatch.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class Festival {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "festival_id")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id")
  private Place place;

  private LocalDate eventStartDate;
  private LocalDate eventEndDate;

  public Festival() {
  }

  public Festival(Place place, LocalDate eventStartDate, LocalDate eventEndDate) {
    this.place = place;
    this.eventStartDate = eventStartDate;
    this.eventEndDate = eventEndDate;
  }

  public LocalDate getEventStartDate() {
    return eventStartDate;
  }

  public LocalDate getEventEndDate() {
    return eventEndDate;
  }
}
