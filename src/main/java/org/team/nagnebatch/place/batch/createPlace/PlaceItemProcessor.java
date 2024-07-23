package org.team.nagnebatch.place.batch.createPlace;

import org.springframework.batch.item.ItemProcessor;
import org.team.nagnebatch.place.client.TourApiProvider;
import org.team.nagnebatch.place.domain.Place;

public class PlaceItemProcessor extends TourApiProvider implements ItemProcessor<Place, Place> {


  @Override
  public Place process(Place item) throws Exception {
    return null;
  }
}
