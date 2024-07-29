package org.team.nagnebatch.place.batch.market.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

  private static final Logger log = LoggerFactory.getLogger(BatchService.class);

  private final JobLauncher jobLauncher;
  private final Job importRestaurantJob;
  private final AreaService areaService;
  private final JobExplorer jobExplorer;

  @Autowired
  public BatchService(JobLauncher jobLauncher, Job importRestaurantJob, AreaService areaService, JobExplorer jobExplorer) {
    this.jobLauncher = jobLauncher;
    this.importRestaurantJob = importRestaurantJob;
    this.areaService = areaService;
    this.jobExplorer = jobExplorer;
  }

  public void checkAndRunBatch() {
    LocalDate today = LocalDate.now();
    LocalDateTime startOfDay = today.atStartOfDay();
    LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

    List<JobExecution> jobExecutions = jobExplorer.findJobInstancesByJobName("importRestaurantJob", 0, 1)
        .stream()
        .flatMap(jobInstance -> jobExplorer.getJobExecutions(jobInstance).stream())
        .toList();

    boolean jobCompletedToday = jobExecutions.stream()
        .anyMatch(jobExecution -> Objects.requireNonNull(jobExecution.getStartTime()).isAfter(startOfDay) &&
            jobExecution.getStartTime().isBefore(endOfDay) &&
            jobExecution.getExitStatus().getExitCode().equals("COMPLETED"));

    if (jobCompletedToday) {
      log.info("오늘 이미 배치 작업이 수행되었습니다. 새로운 작업을 수행하지 않습니다.");
      return;
    }

    saveArea();
    runJob();
  }

  public void saveArea() {
    try {
      log.info("AREA 데이터 삽입");
      areaService.saveAreaData();
      log.info("AREA 데이터 삽입완료");
    } catch (Exception e) {
      log.error("꽁꽁 얼어붙은 코드위로 개발자가 뛰어내립니다. : ", e);
      throw new RuntimeException("꽁꽁 얼어붙은 코드위로 개발자가 뛰어내립니다.", e);
    }
  }

  public void runJob() {
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
