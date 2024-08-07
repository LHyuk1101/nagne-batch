package org.team.nagnebatch.place.batch.createPlace;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.batch.item.ItemProcessor;
import org.team.nagnebatch.place.batch.market.domain.Area;
import org.team.nagnebatch.place.batch.repository.AreaRepository;
import org.team.nagnebatch.place.domain.ApiType;
import org.team.nagnebatch.place.domain.Festival;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.PlaceImg;
import org.team.nagnebatch.place.domain.PlaceWrapper;
import org.team.nagnebatch.place.domain.requestFestival.FestivalDTO;

public class FestivalItemProcessor implements ItemProcessor<FestivalDTO, PlaceWrapper> {

  private final AreaRepository areaRepository;
  private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

  public FestivalItemProcessor(AreaRepository areaRepository) {
    this.areaRepository = areaRepository;
  }

  @Override
  public PlaceWrapper process(FestivalDTO item) throws Exception {
    int areaCode = Integer.parseInt(item.getAreaCode());
    LocalDate eventStartDate = LocalDate.parse(item.getEventStartDate(), formatter);
    LocalDate eventEndDate = LocalDate.parse(item.getEventEndDate(), formatter);
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

    Festival festival = new Festival(
        place,
        eventStartDate,
        eventEndDate
    );

    PlaceImg placeImg = new PlaceImg(
        place,
        item.getFirstImage()
    );

    return new PlaceWrapper(place, placeImg, festival);
  }
}
