package org.team.nagnebatch.place.batch.market.processor;

import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.Store;

public class PlaceAndStore {
  private final Place place;
  private final Store store;

  public PlaceAndStore(Place place, Store store) {
    this.place = place;
    this.store = store;
  }

  public Place getPlace() {
    return place;
  }

  public Store getStore() {
    return store;
  }
}
