package org.team.nagnebatch.place.batch.market.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.PlatformTransactionManager;
import org.team.nagnebatch.place.batch.market.domain.Restaurant;
import org.team.nagnebatch.place.batch.market.reader.RestaurantReader;
import org.team.nagnebatch.place.batch.market.writer.RestaurantWriter;

@Configuration
@EnableBatchProcessing
public class MarketBatchConfiguration {

  private final RestaurantReader restaurantReader;
  private final RestaurantWriter restaurantWriter;

  @Autowired
  public MarketBatchConfiguration(
      RestaurantReader restaurantReader,
      RestaurantWriter restaurantWriter) {
    this.restaurantReader = restaurantReader;
    this.restaurantWriter = restaurantWriter;
  }

  @Bean
  public Job importRestaurantJob(JobRepository jobRepository, Step step1) {
    return new JobBuilder("importRestaurantJob", jobRepository)
        .start(step1)
        .build();
  }

  @Bean
  public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("step1", jobRepository)
        .<Restaurant, Restaurant>chunk(10, transactionManager)
        .reader(restaurantReader.reader())
        .writer(restaurantWriter.writer())
        .build();
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new ResourcelessTransactionManager();
  }
}
