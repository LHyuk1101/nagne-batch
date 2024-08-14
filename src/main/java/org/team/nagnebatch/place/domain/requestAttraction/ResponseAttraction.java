package org.team.nagnebatch.place.domain.requestAttraction;

import java.util.List;

public class ResponseAttraction {

  private List<AttractionDTO> attractions;

  private int totalCount;

  private boolean isNextPage;

  public ResponseAttraction() {
  }

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

  public static class Builder{
    private List<AttractionDTO> attractions;
    private int totalCount;
    private boolean isNextPage;
    private Builder(){}

    public Builder attractions(List<AttractionDTO> attractions) {
      this.attractions = attractions;
      return this;
    }
    public Builder totalCount(int totalCount) {
      this.totalCount = totalCount;
      return this;
    }

    public Builder isNextPage(boolean isNextPage) {
      this.isNextPage = isNextPage;
      return this;
    }

    public ResponseAttraction build() {
      return new ResponseAttraction(attractions, totalCount, isNextPage);
    }

  }

  public static Builder builder() {
    return new Builder();
  }
}
