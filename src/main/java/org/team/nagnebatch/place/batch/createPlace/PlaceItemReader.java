package org.team.nagnebatch.place.batch.createPlace;

import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.team.nagnebatch.place.batch.service.TourApiEngService;
import org.team.nagnebatch.place.client.TourApiProvider;
import org.team.nagnebatch.place.domain.requestAttraction.RequestAttractionDTO;
import org.team.nagnebatch.place.domain.requestAttraction.ResponseAttraction;

public class PlaceItemReader extends TourApiProvider implements ItemReader<RequestAttractionDTO> {

  private final TourApiEngService tourApiEngService;

  public PlaceItemReader(TourApiEngService engService) {
    this.tourApiEngService = engService;
  }

  @Override
  public RequestAttractionDTO read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

    int areaCode = 1 ;
    int contentTypeId = 76;
    List<ResponseAttraction> datas = loopFetchData(areaCode, contentTypeId);
    if(datas.size() > 0) {

    }
    return null;
  }

  public List<ResponseAttraction> loopFetchData(int areaCode, int contentTypeId){
    List<ResponseAttraction> list = new ArrayList<>();
    int page = 1;
    while(true){
      ResponseAttraction attractions1 = tourApiEngService.getLocationBased(page, areaCode, contentTypeId);
      if(!attractions1.getAttractions().isEmpty()){
        list.add(attractions1);
        page++;
      }
      if(!attractions1.isNextPage()){
        break;
      }
    }
    return list;
  }
}
