package org.team.nagnebatch.place.domain.requestIntro;

public class ResponseIntro {
  private String overview;

  public ResponseIntro(IntroDTO introDTO) {
    this.overview = introDTO.getOverview();
  }

  public String getOverview() {
    return overview;
  }
}
