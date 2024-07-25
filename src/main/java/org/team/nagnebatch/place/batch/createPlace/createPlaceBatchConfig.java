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
import org.springframework.transaction.PlatformTransactionManager;
import org.team.nagnebatch.place.batch.service.TourApiEngService;
import org.team.nagnebatch.place.domain.PlaceWrapper;
import org.team.nagnebatch.place.domain.requestAttraction.AttractionDTO;

@Configuration
@EnableBatchProcessing
public class createPlaceBatchConfig {

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  @Autowired
  private TourApiEngService tourApiEngService;

  @Bean
  public Job createPlaceJob(JobRepository jobRepository, Step createAttreactionStep) {
    return new JobBuilder("placeCreate", jobRepository)
        .start(createAttreactionStep)
        .build();
  }

  @Bean
  public Step createAttreactionStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("AttractionCreateStep", jobRepository)
        .<AttractionDTO, PlaceWrapper>chunk(20, transactionManager)
        .reader(createAttractionItemReader())
        .processor(createAttractionItemProcessor())
        .writer(createAttractionItemWriter())
        .build();
  }


  @Bean
  public Step createFestivalStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("AttractionCreateStep", jobRepository)
        .<AttractionDTO, PlaceWrapper>chunk(20, transactionManager)
        .reader(createAttractionItemReader())
        .processor(createAttractionItemProcessor())
        .writer(createAttractionItemWriter())
        .build();
  }



  @Bean
  public ItemProcessor<AttractionDTO, PlaceWrapper> createAttractionItemProcessor() {
    return new AttractionItemProcessor();
  }

  @Bean
  public ItemReader<AttractionDTO> createAttractionItemReader() {
    return new AttractionItemReader(tourApiEngService);
  }

  @Bean
  public ItemWriter<PlaceWrapper> createAttractionItemWriter() {

    return new AttractionItemWriter(entityManagerFactory);
  }

}
