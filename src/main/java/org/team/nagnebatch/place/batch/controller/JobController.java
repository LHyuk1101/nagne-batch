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
import org.team.nagnebatch.place.batch.service.BatchService;

import java.time.LocalDateTime;

@RestController
public class JobController {

  private static final Logger log = LoggerFactory.getLogger(JobController.class);
  private final JobLauncher jobLauncher;
  private final Job createPlaceJob;
  private final BatchService batchService;

  @Autowired
  public JobController(JobLauncher jobLauncher, Job createPlaceJob, BatchService batchService) {
    this.jobLauncher = jobLauncher;
    this.createPlaceJob = createPlaceJob;
    this.batchService = batchService;
  }

  @PostMapping("run-create-place-job")
  public void runCreatePlaceJob() throws Exception {
    JobParameters jobParameters = new JobParametersBuilder()
        .addLocalDateTime("time", LocalDateTime.now())
        .toJobParameters();

    jobLauncher.run(createPlaceJob, jobParameters);
    log.info(">>>> 추가 배치 작업 끝 <<<<");
  }

  @PostMapping("/batch/start")
  public void startBatch() {
    batchService.checkAndRunBatch();
  }
}
