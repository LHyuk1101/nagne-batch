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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team.nagnebatch.place.batch.service.AreaService;

import java.time.LocalDateTime;


@RestController
public class JobController {

  private static final Logger log = LoggerFactory.getLogger(JobController.class);
  private final JobLauncher jobLauncher;
  private final Job createPlaceJob;
  private final Job importRestaurantJob;
  private final AreaService areaService;

  @Autowired
  public JobController(JobLauncher jobLauncher, Job createPlaceJob, Job importRestaurantJob, AreaService areaService) {
    this.jobLauncher = jobLauncher;
    this.createPlaceJob = createPlaceJob;
    this.importRestaurantJob = importRestaurantJob;
    this.areaService = areaService;
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

    try {
      log.info("AREA 데이터 삽입");
      areaService.saveAreaData();
      log.info("AREA 데이터 삽입완료");
    }catch (Exception e) {
      log.error("꽁꽁 얼어붙은 코드위로 개발자가 뛰어내립니다. : ", e);
      throw new RuntimeException("꽁꽁 얼어붙은 코드위로 개발자가 뛰어내립니다.", e);
    }

    try {
      JobParameters jobParameters = new JobParametersBuilder()
              .addString("time", LocalDateTime.now().toString())
              .toJobParameters();

      log.info("배치 작업 시작 : {}", jobParameters);
      jobLauncher.run(importRestaurantJob, jobParameters);
      log.info("배치 작업 완료");
    } catch (Exception e) {
      log.error("코드가 개발자의 허리를 끊었다..[배치실패 에러]", e);
      throw new RuntimeException("코드가 개발자의 허리를 끊었다..[배치실패 에러]", e);
    }
  }


}
