package org.team.nagnebatch.place.batch.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team.nagnebatch.place.batch.market.entity.BatchExecutionLog;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BatchExecutionLogRepository extends JpaRepository<BatchExecutionLog, Long> {
  Optional<BatchExecutionLog> findFirstByJobNameAndExecutionTimeAfter(String jobName, LocalDateTime executionTime);
}
