package org.team.nagnebatch.place.batch.market.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.team.nagnebatch.place.batch.market.domain.CsvData;
import org.team.nagnebatch.place.batch.market.listener.JobCompletionNotificationListener;
import org.team.nagnebatch.place.batch.market.processor.PlaceAndStore;
import org.team.nagnebatch.place.batch.market.processor.PlaceAndStoreProcessorForRestaurant;
import org.team.nagnebatch.place.batch.market.processor.PlaceAndStoreProcessorForLodging;
import org.team.nagnebatch.place.batch.market.reader.RestaurantReader;
import org.team.nagnebatch.place.batch.market.writer.RestaurantWriter;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class MarketBatchConfiguration {

  private final RestaurantReader restaurantReader;
  private final RestaurantWriter restaurantWriter;
  private final PlaceAndStoreProcessorForRestaurant placeAndStoreProcessorForRestaurant;
  private final PlaceAndStoreProcessorForLodging placeAndStoreProcessorForLodging;
  private final JobCompletionNotificationListener jobCompletionNotificationListener;

  @Autowired
  public MarketBatchConfiguration(
          RestaurantReader restaurantReader,
          RestaurantWriter restaurantWriter,
          PlaceAndStoreProcessorForRestaurant placeAndStoreProcessorForRestaurant,
          PlaceAndStoreProcessorForLodging placeAndStoreProcessorForLodging,
          JobCompletionNotificationListener jobCompletionNotificationListener) {
    this.restaurantReader = restaurantReader;
    this.restaurantWriter = restaurantWriter;
    this.placeAndStoreProcessorForRestaurant = placeAndStoreProcessorForRestaurant;
    this.placeAndStoreProcessorForLodging = placeAndStoreProcessorForLodging;
    this.jobCompletionNotificationListener = jobCompletionNotificationListener;
  }

  @Bean
  public Job importRestaurantJob(JobRepository jobRepository, Step restaurantStep, Step lodgingStep) {
    return new JobBuilder("importRestaurantJob", jobRepository)
            .listener(jobCompletionNotificationListener)
            .start(restaurantStep)
            .next(lodgingStep)
            .build();
  }

  @Bean
  public Step restaurantStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws IOException {
    return new StepBuilder("restaurantStep", jobRepository)
            .<CsvData, PlaceAndStore>chunk(10, transactionManager)
            .reader(restaurantReader.multiResourceItemReader("classpath:restaurant/*.csv"))
            .processor(placeAndStoreProcessorForRestaurant)
            .writer(restaurantWriter.compositeItemWriter())
            .build();
  }

  @Bean
  public Step lodgingStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws IOException {
    return new StepBuilder("lodgingStep", jobRepository)
            .<CsvData, PlaceAndStore>chunk(10, transactionManager)
            .reader(restaurantReader.multiResourceItemReader("classpath:lodging/*.csv"))
            .processor(placeAndStoreProcessorForLodging)
            .writer(restaurantWriter.compositeItemWriter())
            .build();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new ResourcelessTransactionManager();
  }
}
