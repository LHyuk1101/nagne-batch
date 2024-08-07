package org.team.nagnebatch.place.batch.market.init;

import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import org.team.nagnebatch.place.domain.Area;
import org.team.nagnebatch.place.batch.repository.AreaRepository;
import org.team.nagnebatch.place.batch.repository.CustomAreaRepository;
import org.team.nagnebatch.place.domain.AreaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
/**
 * 이게 돌아는 가는데 왜 저장이 안될까.....
 * */

@Component
public class AreaInitializer implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(AreaInitializer.class);
  private final CustomAreaRepository caRepository;
  private final AreaRepository areaRepository;
  private final EntityManager em;

  public AreaInitializer(CustomAreaRepository caRepository, AreaRepository areaRepository, EntityManager em) {
    this.caRepository = caRepository;
    this.areaRepository = areaRepository;
    this.em = em;
  }

  @Override
  public void run(String... args) throws Exception {
    for (AreaType areaType : AreaType.values()) {
      Area area = new Area(areaType.getAreaCode(), areaType.getName());
      log.info("데이터 삽입 : " + areaType.getName() + " (" + areaType.getAreaCode() + ")");
      caRepository.save(area);
    }
    List<Area> all = areaRepository.findAll();
    log.info("저장된 데이터 개수 : " + all.size());
  }
}

