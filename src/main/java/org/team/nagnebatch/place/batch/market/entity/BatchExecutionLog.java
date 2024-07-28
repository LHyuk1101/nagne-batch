package org.team.nagnebatch.place.batch.market.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class BatchExecutionLog {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String jobName;
  private LocalDateTime executionTime;

  public BatchExecutionLog() {
  }

  public BatchExecutionLog(String jobName, LocalDateTime executionTime) {
    this.jobName = jobName;
    this.executionTime = executionTime;
  }

  public String getJobName() {
    return jobName;
  }

  public void setJobName(String jobName) {
    this.jobName = jobName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocalDateTime getExecutionTime() {
    return executionTime;
  }

  public void setExecutionTime(LocalDateTime executionTime) {
    this.executionTime = executionTime;
  }
}
