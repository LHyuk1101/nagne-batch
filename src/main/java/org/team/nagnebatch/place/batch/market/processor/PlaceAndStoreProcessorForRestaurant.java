package org.team.nagnebatch.place.batch.market.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.team.nagnebatch.place.batch.market.domain.Area;
import org.team.nagnebatch.place.batch.market.domain.CsvData;
import org.team.nagnebatch.place.batch.repository.AreaRepository;
import org.team.nagnebatch.place.domain.ApiType;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.PlaceImg;
import org.team.nagnebatch.place.domain.Store;
import org.team.nagnebatch.place.batch.repository.PlaceRepository;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class PlaceAndStoreProcessorForRestaurant implements ItemProcessor<CsvData, PlaceAndStore> {
  private static final int CONTENT_TYPE_ID = 82;
  private final AreaRepository areaRepository;
  private final PlaceRepository placeRepository;

  @Autowired
  public PlaceAndStoreProcessorForRestaurant(AreaRepository areaRepository, PlaceRepository placeRepository) {
    this.areaRepository = areaRepository;
    this.placeRepository = placeRepository;
  }

  @Override
  @Transactional
  public PlaceAndStore process(CsvData data) throws Exception {

    if (data.getPhoneNumber() != null && data.getPhoneNumber().contains("Phone")){
      data.setPhoneNumber(null);
    }

    if(data.getImageUrl() != null && data.getImageUrl().contains("proxy")){
      return null;
    }

    if (data.getBusinessHours() != null && data.getBusinessHours().contains("\\u202f")){
      data.setBusinessHours(data.getBusinessHours().replace("\\u202f", ""));
    }

    Area area = areaRepository.findById(Integer.parseInt(data.getAreatype()))
        .orElseThrow(() -> new IllegalArgumentException("AREA 코드 잘못됨 : " + data.getAreatype()));

    ApiType apiType = ApiType.NONE;

    String uniqueId;
    do {
      uniqueId = String.format("%07d", ThreadLocalRandom.current().nextInt(1000000));
    } while (placeRepository.existsByContentId(uniqueId));

    Place place = new Place(
        data.getAddress(),
        data.getName(),
        uniqueId,
        data.getLatitude(),
        CONTENT_TYPE_ID,
        data.getLongitude(),
        area,
        apiType
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
