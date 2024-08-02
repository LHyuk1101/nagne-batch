package org.team.nagnebatch.place.batch.createPlace;

import org.springframework.batch.item.ItemProcessor;
import org.team.nagnebatch.place.batch.market.domain.Area;
import org.team.nagnebatch.place.batch.repository.AreaRepository;
import org.team.nagnebatch.place.client.TourApiConnection;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.PlaceImg;
import org.team.nagnebatch.place.domain.PlaceWrapper;
import org.team.nagnebatch.place.domain.requestAttraction.AttractionDTO;

public class AttractionItemProcessor extends TourApiConnection implements
    ItemProcessor<AttractionDTO, PlaceWrapper> {

  private final AreaRepository areaRepository;

  public AttractionItemProcessor(AreaRepository areaRepository) {
    this.areaRepository = areaRepository;
  }

  @Override
  public PlaceWrapper process(AttractionDTO item) throws Exception {
    int areaCode = Integer.parseInt(item.getAreaCode());
    Area area = areaRepository.findById(areaCode)
        .orElseThrow(() -> new IllegalArgumentException("AREA 코드 잘못됨 : " + item.getAreaCode()
        ));

    Place place = new Place(
        item.getAddr1(),
        item.getTitle(),
        item.getContentId(),
        Math.toIntExact(item.getContentTypeId()),
        item.getLat(),
        item.getLng(),
        area
    );
    PlaceImg placeImg = new PlaceImg(
        place,
        item.getFirstImage2()
    );

    return new PlaceWrapper(place, placeImg);
  }
}
