package org.team.nagnebatch.place.batch.createPlace;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.team.nagnebatch.place.batch.service.TourApiEngService;
import org.team.nagnebatch.place.client.config.ApiLink;
import org.team.nagnebatch.place.domain.requestFestival.FestivalDTO;
import org.team.nagnebatch.place.domain.requestFestival.ResponseFestival;

public class FestivalItemReader implements ItemReader<FestivalDTO> {

  private final TourApiEngService tourApiEngService;
  private Iterator<FestivalDTO> festivalIterator;

  public FestivalItemReader(TourApiEngService tourApiEngService) {
    this.tourApiEngService = tourApiEngService;
  }

  @Override
  public FestivalDTO read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if (festivalIterator == null) {
      loadFestival();
    }
    if (festivalIterator.hasNext()) {
      return festivalIterator.next();
    } else {
      return null;
    }
  }

  public void loadFestival() {
    List<FestivalDTO> mergeList = new ArrayList<>();
    int page = 1;
    while (true) {
      ResponseFestival festival = tourApiEngService.getFestivalByLocationBased(ApiLink.GET_FESTIVAL,
          page);
      if (!festival.getFestivals().isEmpty()) {
        List<FestivalDTO> festivals = festival.getFestivals()
            .stream()
            .filter(fest -> fest.getAreaCode() != null && !fest.getAreaCode().isEmpty())
            .toList();
        mergeList.addAll(festivals);
        page++;
      }
      if (!festival.isNextPage()) {
        break;
      }
    }
    this.festivalIterator = mergeList.iterator();
  }
}
