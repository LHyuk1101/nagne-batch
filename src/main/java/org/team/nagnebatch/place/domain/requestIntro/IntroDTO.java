package org.team.nagnebatch.place.domain.requestIntro;

public class IntroDTO {
  private int contentId;
  private int contentTypeId;
  private String title;
  private String createdTime;
  private String modifiedTime;
  private String tel;
  private String telName;
  private String homepage;
  private String firstImage;
  private String firstImage2;
  private String cpyrhtDivCd;
  private int areaCode;
  private int sigungucode;
  private String cat1;
  private String cat2;
  private String cat3;
  private String addr1;
  private String addr2;
  private String zipcode;
  private double mapx;
  private double mapy;
  private int mlevel;
  private String overview;

  public int getContentId() {
    return contentId;
  }

  public int getContentTypeId() {
    return contentTypeId;
  }

  public String getTitle() {
    return title;
  }

  public String getCreatedTime() {
    return createdTime;
  }

  public String getModifiedTime() {
    return modifiedTime;
  }

  public String getTel() {
    return tel;
  }

  public String getTelName() {
    return telName;
  }

  public String getHomepage() {
    return homepage;
  }

  public String getFirstImage() {
    return firstImage;
  }

  public String getFirstImage2() {
    return firstImage2;
  }

  public String getCpyrhtDivCd() {
    return cpyrhtDivCd;
  }

  public int getAreaCode() {
    return areaCode;
  }

  public int getSigungucode() {
    return sigungucode;
  }

  public String getCat1() {
    return cat1;
  }

  public String getCat2() {
    return cat2;
  }

  public String getCat3() {
    return cat3;
  }

  public String getAddr1() {
    return addr1;
  }

  public String getAddr2() {
    return addr2;
  }

  public String getZipcode() {
    return zipcode;
  }

  public double getMapx() {
    return mapx;
  }

  public double getMapy() {
    return mapy;
  }

  public int getMlevel() {
    return mlevel;
  }

  public String getOverview() {
    return overview;
  }

  public static class Builder {
    private int contentId;
    private int contentTypeId;
    private String title;
    private String createdTime;
    private String modifiedTime;
    private String tel;
    private String telName;
    private String homepage;
    private String firstImage;
    private String firstImage2;
    private String cpyrhtDivCd;
    private int areaCode;
    private int sigungucode;
    private String cat1;
    private String cat2;
    private String cat3;
    private String addr1;
    private String addr2;
    private String zipcode;
    private double mapx;
    private double mapy;
    private int mlevel;
    private String overview;

    public Builder contentId(int contentId) {
      this.contentId = contentId;
      return this;
    }

    public Builder contentTypeId(int contentTypeId) {
      this.contentTypeId = contentTypeId;
      return this;
    }
    public Builder title(String title) {
      this.title = title;
      return this;
    }
    public Builder createdTime(String createdTime) {
      this.createdTime = createdTime;
      return this;
    }

    public Builder modifiedTime(String modifiedTime) {
      this.modifiedTime = modifiedTime;
      return this;
    }

    public Builder tel(String tel) {
      this.tel = tel;
      return this;
    }

    public Builder telName(String telName) {
      this.telName = telName;
      return this;
    }

    public Builder homepage(String homepage) {
      this.homepage = homepage;
      return this;
    }

    public Builder firstImage(String firstImage) {
      this.firstImage = firstImage;
      return this;
    }

    public Builder firstImage2(String firstImage2) {
      this.firstImage2 = firstImage2;
      return this;
    }

    public Builder cpyrhtDivCd(String cpyrhtDivCd) {
      this.cpyrhtDivCd = cpyrhtDivCd;
      return this;
    }

    public Builder areaCode(int areaCode) {
      this.areaCode = areaCode;
      return this;
    }

    public Builder sigungucode(int sigungucode) {
      this.sigungucode = sigungucode;
      return this;
    }

    public Builder cat1(String cat1) {
      this.cat1 = cat1;
      return this;
    }

    public Builder cat2(String cat2) {
      this.cat2 = cat2;
      return this;
    }

    public Builder cat3(String cat3) {
      this.cat3 = cat3;
      return this;
    }

    public Builder addr1(String addr1) {
      this.addr1 = addr1;
      return this;
    }

    public Builder addr2(String addr2) {
      this.addr2 = addr2;
      return this;
    }

    public Builder zipcode(String zipcode) {
      this.zipcode = zipcode;
      return this;
    }

    public Builder mapx(double mapx) {
      this.mapx = mapx;
      return this;
    }

    public Builder mapy(double mapy) {
      this.mapy = mapy;
      return this;
    }

    public Builder mlevel(int mlevel) {
      this.mlevel = mlevel;
      return this;
    }

    public Builder overview(String overview){
      this.overview = overview;
      return this;
    }

    public IntroDTO build() {
      IntroDTO introDTO = new IntroDTO();
      introDTO.contentId = contentId;
      introDTO.contentTypeId = contentTypeId;
      introDTO.title = title;
      introDTO.createdTime = createdTime;
      introDTO.modifiedTime = modifiedTime;
      introDTO.tel = tel;
      introDTO.telName = telName;
      introDTO.homepage = homepage;
      introDTO.firstImage = firstImage;
      introDTO.firstImage2 = firstImage2;
      introDTO.cpyrhtDivCd = cpyrhtDivCd;
      introDTO.areaCode = areaCode;
      introDTO.sigungucode = sigungucode;
      introDTO.cat1 = cat1;
      introDTO.cat2 = cat2;
      introDTO.cat3 = cat3;
      introDTO.addr1 = addr1;
      introDTO.addr2 = addr2;
      introDTO.zipcode = zipcode;
      introDTO.mapx = mapx;
      introDTO.mapy = mapy;
      introDTO.mlevel = mlevel;
      introDTO.overview = overview;
      return introDTO;
    }

  }

  public static IntroDTO.Builder builder() {
    return new IntroDTO.Builder();
  }
}
