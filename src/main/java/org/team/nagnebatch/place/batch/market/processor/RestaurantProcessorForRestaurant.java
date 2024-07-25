package org.team.nagnebatch.place.batch.market.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.team.nagnebatch.place.batch.market.domain.Restaurant;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.Store;

@Component
public class RestaurantProcessorForRestaurant implements ItemProcessor<Restaurant, CompositeData> {
  private static final int CONTENT_TYPE_ID = 82;

  @Override
  public CompositeData process(Restaurant restaurant) throws Exception {
    Place place = new Place(
            null,
            restaurant.getAddress(),
            null,
            (int) (Math.random() * 1000),
            CONTENT_TYPE_ID,
            restaurant.getLatitude(),
            restaurant.getLongitude()
    );

    Store store = new Store(
            null,
            place,
            restaurant.getBusinessHours(),
            restaurant.getPhoneNumber()
    );
    return new CompositeData(restaurant, place, store);
  }
}
