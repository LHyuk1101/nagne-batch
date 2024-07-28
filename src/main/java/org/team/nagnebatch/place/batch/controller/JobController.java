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
import org.team.nagnebatch.place.batch.market.entity.BatchExecutionLog;
import org.team.nagnebatch.place.batch.market.repository.BatchExecutionLogRepository;
import org.team.nagnebatch.place.batch.service.AreaService;

import java.time.LocalDateTime;

@RestController
public class JobController {

  private static final Logger log = LoggerFactory.getLogger(JobController.class);
  private final JobLauncher jobLauncher;
  private final Job createPlaceJob;
  private final Job importRestaurantJob;
  private final AreaService areaService;
  private final BatchExecutionLogRepository batchExecutionLogRepository;

  @Autowired
  public JobController(JobLauncher jobLauncher, Job createPlaceJob, Job importRestaurantJob,
                       AreaService areaService, BatchExecutionLogRepository batchExecutionLogRepository) {
    this.jobLauncher = jobLauncher;
    this.createPlaceJob = createPlaceJob;
    this.importRestaurantJob = importRestaurantJob;
    this.areaService = areaService;
    this.batchExecutionLogRepository = batchExecutionLogRepository;
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
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();

    if (batchExecutionLogRepository.findFirstByJobNameAndExecutionTimeAfter("importRestaurantJob", startOfDay).isPresent()) {
      log.info("오늘 이미 배치 작업이 수행되었습니다. 새로운 작업을 수행하지 않습니다.");
      return;
    }

    try {
      log.info("AREA 데이터 삽입");
      areaService.saveAreaData();
      log.info("AREA 데이터 삽입완료");
    } catch (Exception e) {
      log.error("꽁꽁 얼어붙은 코드위로 개발자가 뛰어내립니다. : ", e);
      throw new RuntimeException("꽁꽁 얼어붙은 코드위로 개발자가 뛰어내립니다.", e);
    }

    try {
      JobParameters jobParameters = new JobParametersBuilder()
              .addString("time", now.toString())
              .toJobParameters();

      log.info("배치 작업 시작 : {}", jobParameters);
      jobLauncher.run(importRestaurantJob, jobParameters);
      log.info("배치 작업 완료");

      // 배치 실행 기록 저장
      BatchExecutionLog logEntry = new BatchExecutionLog("importRestaurantJob", now);
      batchExecutionLogRepository.save(logEntry);
    } catch (Exception e) {
      log.error("코드가 개발자의 허리를 끊었다..[배치실패 에러]", e);
      throw new RuntimeException("코드가 개발자의 허리를 끊었다..[배치실패 에러]", e);
    }
  }
}
