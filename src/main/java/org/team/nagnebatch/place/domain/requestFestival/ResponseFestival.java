package org.team.nagnebatch.place.domain.requestFestival;

import java.util.List;

public class ResponseFestival {
  private List<FestivalDTO> festivals;

  private int totalCount;

  private boolean isNextPage;

  public ResponseFestival() {}

  public ResponseFestival(List<FestivalDTO> festivals, int totalCount, boolean isNextPage) {
    this.festivals = festivals;
    this.totalCount = totalCount;
    this.isNextPage = isNextPage;
  }

  public List<FestivalDTO> getFestivals() {
    return festivals;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public boolean isNextPage() {
    return isNextPage;
  }
}
