package org.team.nagnebatch.place.domain;

public class PlaceWrapper {
  private Place place;
  private PlaceImg placeImg;
  private Store store;
  private Festival festival;

  public PlaceWrapper(Place place, PlaceImg placeImg) {
    this.place = place;
    this.placeImg = placeImg;
    this.store = new Store();
  }

  public PlaceWrapper(Place place, PlaceImg placeImg, Festival festival) {
    this.place = place;
    this.placeImg = placeImg;
    this.store = new Store();
    this.festival = festival;
  }

  public PlaceWrapper(Place place, PlaceImg placeImg, Store store) {
    this.place = place;
    this.placeImg = placeImg;
    this.store = store;
  }

  public Place getPlace() {
    return place;
  }

  public PlaceImg getPlaceImg() {
    return placeImg;
  }

  public Store getStore() {
    return store;
  }

  public Festival getFestival() {
    return festival;
  }
}
