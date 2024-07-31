package org.team.nagnebatch.place.batch.market.processor;

import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.PlaceImg;
import org.team.nagnebatch.place.domain.Store;

public class PlaceAndStore {
  private final Place place;
  private final Store store;
  private final PlaceImg placeImg;

  public PlaceAndStore(Place place, Store store, PlaceImg placeImg) {
    this.place = place;
    this.store = store;
    this.placeImg = placeImg;
  }

  public Place getPlace() {
    return place;
  }

  public Store getStore() {
    return store;
  }

  public PlaceImg getPlaceImg() {
    return placeImg;
  }
}
