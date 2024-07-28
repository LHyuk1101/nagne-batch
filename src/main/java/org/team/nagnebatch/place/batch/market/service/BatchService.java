package org.team.nagnebatch.place.batch.market.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.team.nagnebatch.place.batch.market.entity.BatchExecutionLog;
import org.team.nagnebatch.place.batch.market.repository.BatchExecutionLogRepository;

import java.time.LocalDateTime;

@Service
public class BatchService {

  private static final Logger log = LoggerFactory.getLogger(BatchService.class);
  private final AreaService areaService;
  private final JobLauncher jobLauncher;
  private final BatchExecutionLogRepository batchExecutionLogRepository;

  @Autowired
  public BatchService(AreaService areaService, JobLauncher jobLauncher, BatchExecutionLogRepository repository) {
    this.areaService = areaService;
    this.jobLauncher = jobLauncher;
    this.batchExecutionLogRepository = repository;
  }

  public void saveAreaData() {
    try {
      log.info("AREA 데이터 삽입");
      areaService.saveAreaData();
      log.info("AREA 데이터 삽입완료");
    } catch (Exception e) {
      log.error("AREA 데이터 삽입 실패: ", e);
      throw new RuntimeException("AREA 데이터 삽입 실패", e);
    }
  }

  public void runJob(Job job, JobParameters jobParameters, String jobName){
    try {
      log.info("배치 작업 시작 : {}", jobParameters);
      jobLauncher.run(job, jobParameters);
      log.info("배치 작업 완료");

      // 배치 실행 기록 저장
      BatchExecutionLog logEntry = new BatchExecutionLog(jobName, LocalDateTime.now());
      batchExecutionLogRepository.save(logEntry);
    } catch (Exception e) {
      log.error("배치 작업 실패: ", e);
      throw new RuntimeException("배치 작업 실패", e);
    }
  }

}
