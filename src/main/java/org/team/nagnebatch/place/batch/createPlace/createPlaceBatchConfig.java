package org.team.nagnebatch.place.batch.createPlace;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.team.nagnebatch.place.batch.service.TourApiEngService;
import org.team.nagnebatch.place.domain.Place;
import org.team.nagnebatch.place.domain.requestAttraction.RequestAttractionDTO;

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
        .<RequestAttractionDTO, Place>chunk(20, transactionManager)
        .reader(createPlaceItemReader())
        .writer(createPlaceItemWriter())
        .build();
  }

  @Bean
  public ItemReader<RequestAttractionDTO> createPlaceItemReader() {
    return new PlaceItemReader(tourApiEngService);
  }

  @Bean
  ItemWriter<Place> createPlaceItemWriter() {
    JpaItemWriter<Place> writer = new JpaItemWriter<>();
    writer.setEntityManagerFactory(entityManagerFactory);
    return writer;
  }
}
