package org.team.nagnebatch.place.batch.market.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.team.nagnebatch.place.batch.market.processor.PlaceAndStore;
import org.team.nagnebatch.place.batch.market.repository.StoreRepository;
import org.team.nagnebatch.place.batch.repository.PlaceImgRepository;
import org.team.nagnebatch.place.batch.repository.PlaceRepository;

import java.util.Collections;

@Configuration
public class RestaurantWriter {

  private static final Logger log = LoggerFactory.getLogger(RestaurantWriter.class);

  private final PlaceRepository placeRepository;
  private final StoreRepository storeRepository;
  private final PlaceImgRepository placeImgRepository;

  @Autowired
  public RestaurantWriter(PlaceRepository placeRepository, StoreRepository storeRepository, PlaceImgRepository placeImgRepository) {
    this.placeRepository = placeRepository;
    this.storeRepository = storeRepository;
    this.placeImgRepository = placeImgRepository;
  }

  @Bean
  public ItemWriter<PlaceAndStore> databaseWriter() {
    return new ItemWriter<PlaceAndStore>() {
      @Override
      public void write(Chunk<? extends PlaceAndStore> chunk) throws Exception {
        for (PlaceAndStore placeAndStore : chunk) {
          placeRepository.save(placeAndStore.getPlace());
          storeRepository.save(placeAndStore.getStore());
          placeImgRepository.save(placeAndStore.getPlaceImg());
        }
        log.info("디비에 {}건의 데이터가 추가 됨.", chunk.size());
      }
    };
  }

  @Bean
  public CompositeItemWriter<PlaceAndStore> compositeItemWriter() {
    CompositeItemWriter<PlaceAndStore> compositeWriter = new CompositeItemWriter<>();
    compositeWriter.setDelegates(Collections.singletonList(databaseWriter()));
    return compositeWriter;
  }
}
