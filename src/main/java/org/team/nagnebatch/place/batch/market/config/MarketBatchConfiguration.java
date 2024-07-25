package org.team.nagnebatch.place.batch.market.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.team.nagnebatch.place.batch.market.domain.Restaurant;
import org.team.nagnebatch.place.batch.market.processor.CompositeData;
import org.team.nagnebatch.place.batch.market.processor.RestaurantProcessorForRestaurant;
import org.team.nagnebatch.place.batch.market.processor.RestaurantProcessorForLodging;
import org.team.nagnebatch.place.batch.market.reader.RestaurantReader;
import org.team.nagnebatch.place.batch.market.writer.RestaurantWriter;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class MarketBatchConfiguration {

  private final RestaurantReader restaurantReader;
  private final RestaurantWriter restaurantWriter;
  private final RestaurantProcessorForRestaurant restaurantProcessorForRestaurant;
  private final RestaurantProcessorForLodging restaurantProcessorForLodging;

  @Autowired
  public MarketBatchConfiguration(
          RestaurantReader restaurantReader,
          RestaurantWriter restaurantWriter,
          RestaurantProcessorForRestaurant restaurantProcessorForRestaurant,
          RestaurantProcessorForLodging restaurantProcessorForLodging) {
    this.restaurantReader = restaurantReader;
    this.restaurantWriter = restaurantWriter;
    this.restaurantProcessorForRestaurant = restaurantProcessorForRestaurant;
    this.restaurantProcessorForLodging = restaurantProcessorForLodging;
  }

  @Bean
  public Job importRestaurantJob(JobRepository jobRepository, Step restaurantStep, Step lodgingStep) {
    return new JobBuilder("importRestaurantJob", jobRepository)
            .start(restaurantStep)
            .next(lodgingStep)
            .build();
  }

  @Bean
  public Step restaurantStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws IOException {
    return new StepBuilder("restaurantStep", jobRepository)
            .<Restaurant, CompositeData>chunk(10, transactionManager)
            .reader(restaurantReader.multiResourceItemReader("classpath:restaurant/*.csv"))
            .processor(restaurantProcessorForRestaurant)
            .writer(restaurantWriter.compositeItemWriter())
            .build();
  }

  @Bean
  public Step lodgingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws IOException {
    return new StepBuilder("lodgingStep", jobRepository)
            .<Restaurant, CompositeData>chunk(10, transactionManager)
            .reader(restaurantReader.multiResourceItemReader("classpath:lodging/*.csv"))
            .processor(restaurantProcessorForLodging)
            .writer(restaurantWriter.compositeItemWriter())
            .build();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new ResourcelessTransactionManager();
  }
}
