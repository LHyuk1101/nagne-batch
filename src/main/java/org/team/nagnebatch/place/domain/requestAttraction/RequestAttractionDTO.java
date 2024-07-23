package org.team.nagnebatch.place.domain.requestAttraction;

import java.time.LocalDateTime;

public class RequestAttractionDTO {

  private int numOfRows;

  private int pageNo;

  private String MobileApp;

  private String serviceKey;

  private String arrange;

  private int areaCode;

  private String eventStartDate;

  private String eventEndDate;

  // 업데이트할 때 사용할 필드
  private LocalDateTime modifiedTime;
}
