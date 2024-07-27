package org.team.nagnebatch.place.batch.market.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team.nagnebatch.place.batch.market.domain.Area;
import org.team.nagnebatch.place.batch.repository.AreaRepository;
import org.team.nagnebatch.place.domain.AreaType;

import java.util.Arrays;

@Service
@Transactional
public class AreaService {

  private final AreaRepository areaRepository;

  public AreaService(AreaRepository areaRepository) {
    this.areaRepository = areaRepository;
  }

  public void initializeAreas() {
    Arrays.stream(AreaType.values()).forEach(areaType -> {
      if (!areaRepository.existsById(areaType.getAreaCode())) {
        Area area = new Area(areaType.getAreaCode(), areaType.getName());
        areaRepository.save(area);
      }
    });
  }
}
