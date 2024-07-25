package org.team.nagnebatch.place.batch.market.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.team.nagnebatch.place.batch.market.domain.Restaurant;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.Store;

@Component
public class RestaurantProcessor implements ItemProcessor<Restaurant, CompositeData> {
  @Override
  public CompositeData process(Restaurant restaurant) throws Exception {
    Place place = new Place(
      null,
            restaurant.getAddress(),
            null,
            (int) (Math.random() * 1000),
            (int) (Math.random() * 1000),
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
