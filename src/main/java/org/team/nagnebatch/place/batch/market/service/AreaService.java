package org.team.nagnebatch.place.batch.market.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.team.nagnebatch.place.batch.market.domain.Area;
import org.team.nagnebatch.place.batch.repository.AreaRepository;
import org.team.nagnebatch.place.domain.AreaType;

@Service
public class AreaService {

  private static final Logger log = LoggerFactory.getLogger(AreaService.class);
  private final AreaRepository areaRepository;

  public AreaService(AreaRepository areaRepository) {
    this.areaRepository = areaRepository;
  }

  public void saveAreaData() {
    for (AreaType areaType : AreaType.values()) {
      Area area = new Area(areaType.getAreaCode(), areaType.getName());
      log.info("Saving Area: " + areaType.getName() + " (" + areaType.getAreaCode() + ")");
      areaRepository.save(area);
      log.info(area.getAreaCode() + " " + areaType.getName() + " saved");
    }
  }
}
