package org.team.nagnebatch.place.batch.market.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.team.nagnebatch.place.batch.market.domain.Restaurant;

@Component
public class RestaurantReader {

  private static final Logger log = LoggerFactory.getLogger(RestaurantReader.class);

  @Bean
  public FlatFileItemReader<Restaurant> reader() {
    log.info("Reading CSV file: Chungnam_restaurant_data.csv");
    return new FlatFileItemReaderBuilder<Restaurant>()
            .name("restaurantItemReader")
            .resource(new ClassPathResource("Chungnam_restaurant_data.csv"))
            .delimited()
            .names("index", "name", "address", "phone_number", "average_rating", "latitude", "longitude", "business_hours")
            .linesToSkip(1)
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Restaurant>() {{
              setTargetType(Restaurant.class);
            }})
            .build();
  }
}
