package org.team.nagnebatch.place.domain;

public enum AreaType {

  SEOUL(1,"SEOUL"),
  INCHEON(2,"INCHEON"),
  DAEJEON(3,"DAEJEON"),
  DAEGU(4,"DAEGU"),
  GWANGJU(5,"GWANGJU"),
  BUSAN(6,"BUSAN"),
  ULSAN(7,"ULSAN"),
  SEJONG(8,"SEJONG"),
  GYEONGGIDO(31,"GYEONGGIDO"),
  GANGWONDO(32,"GANGWONDO"),
  CHUNGCHEONGBUKDO(33,"CHUNGCHEONGBUKDO"),
  CHUNGCHEONGNAMDO(34,"CHUNGCHEONGNAMDO"),
  GYEONGSANGBUKDO(35,"GYEONGSANGBUKDO"),
  GYEONGSANGNAMDO(36,"GYEONGSANGNAMDO"),
  JEONBUKDO(37,"JEONBUKDO"),
  JEOLLANAMDO(38,"JEOLLANAMDO"),
  JEJUDO(39,"JEJUDO");

  private int areaCode;
  private String name;

  private AreaType(final int areaCode, final String name) {
    this.areaCode = areaCode;
    this.name = name;

  }

  public int getAreaCode() {
    return areaCode;
  }

  public String getName() {
    return name;
  }

}
