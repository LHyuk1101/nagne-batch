package org.team.nagnebatch.place.batch.createPlace;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreatePlaceJobController {

  private static final Logger log = LoggerFactory.getLogger(CreatePlaceJobController.class);
  private final JobLauncher jobLauncher;
  private final Job job;

  public CreatePlaceJobController(JobLauncher jobLauncher, Job job) {
    this.jobLauncher = jobLauncher;
    this.job = job;
  }

  @PostMapping("run-create-place-job")
  public void runCreatePlaceJob() throws Exception {
    JobParameters jobParameters = new JobParametersBuilder()
        .addLocalDateTime("time", LocalDateTime.now())
        .toJobParameters();
    jobLauncher.run(job, jobParameters);
    log.info(">>>> 추가 배치 작업 끝 <<<<");
  }

}
