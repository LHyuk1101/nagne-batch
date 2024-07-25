//package org.team.nagnebatch.place.batch.market.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDateTime;
//
//@RestController
//@RequestMapping("/batch")
//public class RestaurantBatchController {
//
//  private static final Logger log = LoggerFactory.getLogger(RestaurantBatchController.class);
//
//  private final JobLauncher jobLauncher;
//  private final Job importRestaurantJob;
//
//  @Autowired
//  public RestaurantBatchController(@Lazy JobLauncher jobLauncher, @Lazy Job importRestaurantJob) {
//    this.jobLauncher = jobLauncher;
//    this.importRestaurantJob = importRestaurantJob;
//  }
//
//  @GetMapping("/start")
//  public ResponseEntity<String> startBatch() {
//    try {
//      JobParameters jobParameters = new JobParametersBuilder()
//              .addString("time", LocalDateTime.now().toString())
//              .toJobParameters();
//      log.info("Starting batch job with parameters: {}", jobParameters);
//      jobLauncher.run(importRestaurantJob, jobParameters);
//      return ResponseEntity.ok("Batch job started successfully");
//    } catch (Exception e) {
//      log.error("Failed to start batch job", e);
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start batch job");
//    }
//  }
//}
