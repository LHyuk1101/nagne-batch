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
public class MarketBatchConfiguration{

  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;
  private final RestaurantReader restaurantReader;
  private final RestaurantWriter restaurantWriter;

  @Autowired
  public MarketBatchConfiguration(JobRepository jobRepository,
                                  PlatformTransactionManager transactionManager,
                                  RestaurantReader restaurantReader,
                                  RestaurantWriter restaurantWriter) {
    this.jobRepository = jobRepository;
    this.transactionManager = transactionManager;
    this.restaurantReader = restaurantReader;
    this.restaurantWriter = restaurantWriter;
  }

  @Bean
  public Job importRestaurantJob() {
    return new JobBuilder("importRestaurantJob", jobRepository)
            .start(step1())
            .build();
  }

  @Bean
  public Step step1() {
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
