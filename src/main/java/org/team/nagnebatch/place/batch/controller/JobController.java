package org.team.nagnebatch.place.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.nagnebatch.place.batch.market.repository.BatchExecutionLogRepository;
import org.team.nagnebatch.place.batch.market.service.BatchService;

import java.time.LocalDateTime;

@RestController
public class JobController {

  private static final Logger log = LoggerFactory.getLogger(JobController.class);
  private final JobLauncher jobLauncher;
  private final Job createPlaceJob;
  private final Job importRestaurantJob;
  private final BatchExecutionLogRepository batchExecutionLogRepository;
  private final BatchService batchService;

  @Autowired
  public JobController(JobLauncher jobLauncher, Job createPlaceJob, Job importRestaurantJob,
                       BatchExecutionLogRepository batchExecutionLogRepository,
                       BatchService batchService) {
    this.jobLauncher = jobLauncher;
    this.createPlaceJob = createPlaceJob;
    this.importRestaurantJob = importRestaurantJob;
    this.batchExecutionLogRepository = batchExecutionLogRepository;
    this.batchService = batchService;
  }

  @PostMapping("run-create-place-job")
  public void runCreatePlaceJob() throws Exception{
    JobParameters jobParameters = new JobParametersBuilder()
            .addLocalDateTime("time", LocalDateTime.now())
            .toJobParameters();

    jobLauncher.run(createPlaceJob, jobParameters);
    log.info(">>>> 추가 배치 작업 끝 <<<<");
  }


  @PostMapping("/batch/start")
  public void startBatch() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();

    if (batchExecutionLogRepository.findFirstByJobNameAndExecutionTimeAfter("importRestaurantJob", startOfDay).isPresent()) {
      log.info("오늘 이미 배치 작업이 수행되었습니다. 새로운 작업을 수행하지 않습니다.");
      return;
    }

    batchService.saveAreaData();

    JobParameters jobParameters = new JobParametersBuilder()
            .addString("time", now.toString())
            .toJobParameters();

    batchService.runJob(importRestaurantJob, jobParameters, "importRestaurantJob");
  }
}
