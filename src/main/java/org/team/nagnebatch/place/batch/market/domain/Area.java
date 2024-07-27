package org.team.nagnebatch.place.batch.market.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "area")
public class Area {

  @Id
  private Integer areaCode;
  private String name;

  public Area() {
  }

  public Area(Integer areaCode, String name) {
    this.areaCode = areaCode;
    this.name = name;
  }

  public Integer getAreaCode() {
    return areaCode;
  }

  public void setAreaCode(Integer areaCode) {
    this.areaCode = areaCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
