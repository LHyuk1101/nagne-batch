package org.team.nagnebatch.place.batch.market.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.team.nagnebatch.place.batch.market.domain.CsvData;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.PlaceImg;
import org.team.nagnebatch.place.domain.Store;

@Component
public class PlaceAndStoreProcessorForLodging implements ItemProcessor<CsvData, PlaceAndStore> {
  private static final int CONTENT_TYPE_ID = 80;

  @Override
  public PlaceAndStore process(CsvData data) throws Exception {
    Place place = new Place(
            data.getAddress(),
            data.getName(),
            (int) (Math.random() * 1000),
            CONTENT_TYPE_ID,
            data.getLatitude(),
            data.getLongitude()
    );

    Store store = new Store(
            null,
            place,
            data.getBusinessHours(),
            data.getPhoneNumber()
    );

    PlaceImg placeImg = new PlaceImg(
            place,
            data.getImageUrl()
    );

    return new PlaceAndStore(place, store, placeImg);
  }
}
