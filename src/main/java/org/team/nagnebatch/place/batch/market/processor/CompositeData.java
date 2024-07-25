package org.team.nagnebatch.place.batch.market.processor;

import org.team.nagnebatch.place.batch.market.domain.Restaurant;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.Store;

public class CompositeData {
  private final Restaurant restaurant;
  private final Place place;
  private final Store store;

  public CompositeData(Restaurant restaurant, Place place, Store store) {
    this.restaurant = restaurant;
    this.place = place;
    this.store = store;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public Place getPlace() {
    return place;
  }

  public Store getStore() {
    return store;
  }

}
