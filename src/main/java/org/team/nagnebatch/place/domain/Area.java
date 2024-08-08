package org.team.nagnebatch.place.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Area {

  @Id
  private Integer code;

  @Column(length = 100)
  private String name;

  public Area() {
  }

  public Area(AreaType areaType){
    this.code = areaType.getAreaCode();
    this.name = areaType.getName();
  }

  public Area(Integer code, String name) {
    this.code = code;
    this.name = name;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
