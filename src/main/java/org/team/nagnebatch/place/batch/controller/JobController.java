package org.team.nagnebatch.place.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
public class JobController {

  private static final Logger log = LoggerFactory.getLogger(JobController.class);
  private final JobLauncher jobLauncher;
  private final Job createPlaceJob;
  private final Job importRestaurantJob;

  @Autowired
  public JobController(JobLauncher jobLauncher, Job createPlaceJob, Job importRestaurantJob) {
    this.jobLauncher = jobLauncher;
    this.createPlaceJob = createPlaceJob;
    this.importRestaurantJob = importRestaurantJob;
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
  public ResponseEntity<String> startBatch() {
    try {
      JobParameters jobParameters = new JobParametersBuilder()
              .addString("time",LocalDateTime.now().toString())
              .toJobParameters();

      log.info("배치작업 시작 : {}", jobParameters);
      jobLauncher.run(importRestaurantJob, jobParameters);
      return ResponseEntity.ok("배치성공 레후wwwwwwwwww");
    }catch (Exception e) {
      log.error("배치 실패했데수 : ",e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start batch job");
    }
  }


}
