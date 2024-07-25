package org.team.nagnebatch.place.batch.createPlace;

import org.springframework.batch.item.ItemProcessor;
import org.team.nagnebatch.place.client.TourApiProvider;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.PlaceImg;
import org.team.nagnebatch.place.domain.PlaceWrapper;
import org.team.nagnebatch.place.domain.requestAttraction.AttractionDTO;

public class AttractionItemProcessor extends TourApiProvider implements
    ItemProcessor<AttractionDTO, PlaceWrapper> {


  @Override
  public PlaceWrapper process(AttractionDTO item) throws Exception {

    Place place = new Place(
        item.getAddr1(),
        item.getTitle(),
        item.getContentId(),
        Math.toIntExact(item.getContentTypeId()),
        item.getLat(),
        item.getLng()
    );
    PlaceImg placeImg = new PlaceImg(
        place,
        item.getFirstImage2()
    );

    return new PlaceWrapper(place, placeImg);
  }
}
