package org.team.nagnebatch.place.batch.market.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.team.nagnebatch.place.batch.market.processor.CompositeData;
import org.team.nagnebatch.place.batch.market.repository.RestaurantRepository;
import org.team.nagnebatch.place.batch.market.repository.StoreRepository;
import org.team.nagnebatch.place.batch.repository.PlaceRepository;


import java.util.Collections;

@Configuration
public class RestaurantWriter {

  private static final Logger log = LoggerFactory.getLogger(RestaurantWriter.class);

  private final RestaurantRepository restaurantRepository;
  private final PlaceRepository placeRepository;
  private final StoreRepository storeRepository;

  @Autowired
  public RestaurantWriter(RestaurantRepository restaurantRepository, PlaceRepository placeRepository, StoreRepository storeRepository) {
    this.restaurantRepository = restaurantRepository;
    this.placeRepository = placeRepository;
    this.storeRepository = storeRepository;
  }

  @Bean
  public ItemWriter<CompositeData> databaseWriter() {
    return new ItemWriter<CompositeData>() {
      @Override
      public void write(Chunk<? extends CompositeData> chunk) throws Exception {
        for (CompositeData compositeData : chunk) {
          restaurantRepository.save(compositeData.getRestaurant());
          placeRepository.save(compositeData.getPlace());
          storeRepository.save(compositeData.getStore());
        }
        log.info("Writing {} records to the database", chunk.size());
      }
    };
  }

  @Bean
  public CompositeItemWriter<CompositeData> compositeItemWriter() {
    CompositeItemWriter<CompositeData> compositeWriter = new CompositeItemWriter<>();
    compositeWriter.setDelegates(Collections.singletonList(databaseWriter()));
    return compositeWriter;
  }
}
