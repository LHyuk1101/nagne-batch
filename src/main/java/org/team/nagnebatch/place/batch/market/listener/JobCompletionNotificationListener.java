package org.team.nagnebatch.place.batch.market.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("Job 시작: {}", jobExecution.getJobInstance().getJobName());
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus().isUnsuccessful()) {
      jobExecution.getAllFailureExceptions().forEach(exception -> {
        if (exception instanceof FlatFileParseException) {
          FlatFileParseException parseException = (FlatFileParseException) exception;
          log.error("FlatFileParseException 발생: {} - at line number: {} - in input: {}",
                  parseException.getMessage(), parseException.getLineNumber(), parseException.getInput());
        } else {
          log.error("배치 작업 중 에러 발생: ", exception);
        }
      });
    } else {
      log.info("배치 작업 성공: {}", jobExecution.getJobInstance().getJobName());
    }
  }
}
