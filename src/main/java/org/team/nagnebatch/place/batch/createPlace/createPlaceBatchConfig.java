package org.team.nagnebatch.place.batch.createPlace;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryException;
import org.springframework.transaction.PlatformTransactionManager;
import org.team.nagnebatch.place.batch.repository.AreaRepository;
import org.team.nagnebatch.place.batch.service.TourApiEngService;
import org.team.nagnebatch.place.domain.PlaceWrapper;
import org.team.nagnebatch.place.domain.requestAttraction.AttractionDTO;
import org.team.nagnebatch.place.domain.requestFestival.FestivalDTO;
import org.team.nagnebatch.place.exception.ApiResponseException;

@Configuration
@EnableBatchProcessing
public class createPlaceBatchConfig {

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  @Autowired
  private TourApiEngService tourApiEngService;

  @Autowired
  private AreaRepository areaRepository;

  @Bean
  public Job createPlaceJob(JobRepository jobRepository, Step createAttreactionStep, Step createFestivalStep) {
    return new JobBuilder("placeCreate", jobRepository)
        .start(createAttreactionStep)
        .next(createFestivalStep)
        .build();
  }

  @Bean
  public Step createAttreactionStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("AttractionCreateStep", jobRepository)
        .<AttractionDTO, PlaceWrapper>chunk(20, transactionManager)
        .reader(createAttractionItemReader())
        .processor(createAttractionItemProcessor())
        .writer(createAttractionItemWriter())
        .faultTolerant()
        .skip(ApiResponseException.class)
        .skipLimit(1)
        .retry(RetryException.class)
        .retryLimit(2)
        .noRollback(NumberFormatException.class)
        .build();
  }


  @Bean
  public Step createFestivalStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("FestivalCreateStep", jobRepository)
        .<FestivalDTO, PlaceWrapper>chunk(20, transactionManager)
        .reader(createFestivalItemReader())
        .processor(createFestivalItemProcessor())
        .writer(createFestivalItemWriter())
        .build();
  }

  @Bean
  public ItemProcessor<AttractionDTO, PlaceWrapper> createAttractionItemProcessor() {
    return new AttractionItemProcessor(areaRepository);
  }

  @Bean
  public ItemReader<AttractionDTO> createAttractionItemReader() {
    return new AttractionItemReader(tourApiEngService);
  }

  @Bean
  public ItemWriter<PlaceWrapper> createAttractionItemWriter() {

    return new AttractionItemWriter(entityManagerFactory);
  }

  @Bean
  public ItemReader<FestivalDTO> createFestivalItemReader(){
    return new FestivalItemReader(tourApiEngService);
  }

  @Bean
  public ItemProcessor<FestivalDTO, PlaceWrapper> createFestivalItemProcessor() {
    return new FestivalItemProcessor(areaRepository);
  }

  @Bean
  public ItemWriter<PlaceWrapper> createFestivalItemWriter(){
    return new FestivalItemWriter(entityManagerFactory);
  }
}
