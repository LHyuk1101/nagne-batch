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
  JEONBUKDO(37,"JEONLABUKDO"),
  JEOLLANAMDO(38,"JEONLANAMDO"),
  JEJUDO(39,"JEJUDO");

  private int areaCode;
  private String name;

  private AreaType(final int areaCode, final String name) {
    this.areaCode = areaCode;
    this.name = name;

  }

  //areaCode로 AreaType을 반환합니다.
  public static AreaType getByAreaCode(int areaCode) {
    for (AreaType areaType : values()) {
      if (areaType.getAreaCode() == areaCode) {
        return areaType;
      }
    }
    throw new IllegalArgumentException("Invalid area code: " + areaCode);
  }

  public int getAreaCode() {
    return areaCode;
  }

  public String getName() {
    return name;
  }

}
