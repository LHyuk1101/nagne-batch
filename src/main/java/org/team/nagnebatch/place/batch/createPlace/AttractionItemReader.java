package org.team.nagnebatch.place.batch.createPlace;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.team.nagnebatch.place.batch.service.TourApiEngService;
import org.team.nagnebatch.place.client.TourApiProvider;
import org.team.nagnebatch.place.client.config.ApiLink;
import org.team.nagnebatch.place.domain.requestAttraction.AttractionDTO;
import org.team.nagnebatch.place.domain.requestAttraction.ResponseAttraction;

public class AttractionItemReader extends TourApiProvider implements ItemReader<AttractionDTO> {

  private final TourApiEngService tourApiEngService;
  private Iterator<AttractionDTO> attractionIterator;

  public AttractionItemReader(TourApiEngService engService) {
    this.tourApiEngService = engService;
  }

  @Override
  public AttractionDTO read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

    if (attractionIterator == null) {
      loadAttractions();
    }
    if (attractionIterator.hasNext()) {
      return attractionIterator.next();
    } else {
      return null;
    }
  }

  public void loadAttractions(){
    List<AttractionDTO> mergeList = new ArrayList<>();
    int page = 1;
    while(true){
      ResponseAttraction attractions1 = tourApiEngService.getLocationBased(ApiLink.GET_ATTRACTION, page);
      if(!attractions1.getAttractions().isEmpty()){
        List<AttractionDTO> filterAttractions = attractions1.getAttractions()
            .stream()
            .filter(fest -> fest.getAreaCode() != null && !fest.getAreaCode().isEmpty())
            .toList();
        mergeList.addAll(filterAttractions);
        page++;
      }
      if(!attractions1.isNextPage()){
        break;
      }
    }
    this.attractionIterator = mergeList.iterator();
  }
}
