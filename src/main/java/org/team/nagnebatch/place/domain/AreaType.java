package org.team.nagnebatch.place.domain;

public enum AreaType {

  SEOUL(1),
  INCHEON(2),
  DAEJEON(3),
  DAEGU(4),
  GWANGJU(5),
  BUSAN(6),
  ULSAN(7),
  SEJONG(8),
  GYEONGGIDO(31),
  GANGWONDO(32),
  CHUNGCHEONGBUKDO(33),
  CHUNGCHEONGNAMDO(34),
  GYEONGSANGBUKDO(35),
  GYEONGSANGNAMDO(36),
  JEONBUKDO(37),
  JEOLLANAMDO(38),
  JEJUDO(39);

  private int areaCode;

  private AreaType(final int areaCode) {
    this.areaCode = areaCode;
  }

  public int getAreaCode() {
    return areaCode;
  }

}
