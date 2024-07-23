package org.team.nagnebatch.place.batch.market.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.team.nagnebatch.place.batch.market.domain.Restaurant;
import org.team.nagnebatch.place.batch.market.repository.RestaurantRepository;

@Component
public class RestaurantWriter {

  private static final Logger log = LoggerFactory.getLogger(RestaurantWriter.class);

  private final RestaurantRepository restaurantRepository;

  @Autowired
  public RestaurantWriter(RestaurantRepository restaurantRepository) {
    this.restaurantRepository = restaurantRepository;
  }

  @Bean
  public ItemWriter<Restaurant> writer() {
    return new ItemWriter<Restaurant>() {
      @Override
      public void write(Chunk<? extends Restaurant> chunk) throws Exception {
        log.info("Writing {} records to the database", chunk.size());
        restaurantRepository.saveAll(chunk);
      }
    };
  }
}
