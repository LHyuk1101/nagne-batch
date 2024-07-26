package org.team.nagnebatch.place.batch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileParseException;
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
  private final Job importRestaurantJob;

  @Autowired
  public JobController(JobLauncher jobLauncher, Job importRestaurantJob) {
    this.jobLauncher = jobLauncher;
    this.importRestaurantJob = importRestaurantJob;
  }

  @PostMapping("/batch/start")
  public ResponseEntity<String> startBatch() {
    try {
      JobParameters jobParameters = new JobParametersBuilder()
              .addString("time", LocalDateTime.now().toString())
              .toJobParameters();

      log.info("배치작업 시작 : {}", jobParameters);
      jobLauncher.run(importRestaurantJob, jobParameters);
      return ResponseEntity.ok("배치성공 레후wwwwwwwwww");
    } catch (FlatFileParseException e) {
      log.error("FlatFileParseException 발생: {} - at line number: {} - in input: {}",
              e.getMessage(), e.getLineNumber(), e.getInput());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(String.format("FlatFileParseException 발생: %s - at line number: %d - in input: %s",
                      e.getMessage(), e.getLineNumber(), e.getInput()));
    } catch (Exception e) {
      log.error("배치 실패: ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start batch job");
    }
  }
}
