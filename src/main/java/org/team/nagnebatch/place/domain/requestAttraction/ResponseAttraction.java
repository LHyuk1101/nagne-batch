package org.team.nagnebatch.place.domain.requestAttraction;

import java.util.List;

public class ResponseAttraction {

  private List<AttractionDTO> attractions;

  private int totalCount;

  private boolean isNextPage;

  public ResponseAttraction(List<AttractionDTO> attractions, int totalCount, boolean isNextPage) {
    this.attractions = attractions;
    this.totalCount = totalCount;
    this.isNextPage = isNextPage;
  }

  public List<AttractionDTO> getAttractions() {
    return attractions;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public boolean isNextPage() {
    return isNextPage;
  }
}
