package org.team.nagnebatch.place.batch.createPlace;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.batch.item.ItemProcessor;
import org.team.nagnebatch.place.batch.market.domain.Area;
import org.team.nagnebatch.place.batch.repository.AreaRepository;
import org.team.nagnebatch.place.client.TourApiConnection;
import org.team.nagnebatch.place.domain.ApiType;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.PlaceImg;
import org.team.nagnebatch.place.domain.PlaceWrapper;
import org.team.nagnebatch.place.domain.requestAttraction.AttractionDTO;

public class AttractionItemProcessor extends TourApiConnection implements
    ItemProcessor<AttractionDTO, PlaceWrapper> {

  private final AreaRepository areaRepository;
  private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  public AttractionItemProcessor(AreaRepository areaRepository) {
    this.areaRepository = areaRepository;
  }

  @Override
  public PlaceWrapper process(AttractionDTO item) throws Exception {
    int areaCode = Integer.parseInt(item.getAreaCode());
    Area area = areaRepository.findById(areaCode)
        .orElseThrow(() -> new IllegalArgumentException("AREA 코드 잘못됨 : " + item.getAreaCode()
        ));

    LocalDateTime localDateTime = LocalDateTime.parse(item.getModifiedTime(), dateTimeFormatter);

    Place place = new Place(
        item.getAddr1(),
        item.getTitle(),
        item.getContentId(),
        item.getLat(),
        item.getContentTypeId().intValue(),
        item.getLng(),
        area,
        ApiType.TOUR,
        item.getOverview(),
        item.getFirstImage2(),
        localDateTime
    );

    PlaceImg placeImg = new PlaceImg(
        place,
        item.getFirstImage()
    );

    return new PlaceWrapper(place, placeImg);
  }
}
