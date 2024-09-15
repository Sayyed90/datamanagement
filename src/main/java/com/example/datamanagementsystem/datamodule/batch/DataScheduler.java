package com.example.datamanagementsystem.datamodule.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.*;

@Component
@EnableScheduling
public class DataScheduler {
    private static final Logger logger= LoggerFactory.getLogger(DataScheduler.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("runJob")
    Job job;

    @Scheduled(cron = "${cronTime}" )
    public void scheduleJob() throws Exception {
        LocalDateTime localDateTime=LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        long epochMilli = zonedDateTime.toInstant().toEpochMilli();
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time",epochMilli).toJobParameters();
        runJobWithTimeout(jobParameters);
    }

    public void runJobWithTimeout(JobParameters jobParameters) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        Future<JobExecution> future = executorService.submit(() -> jobLauncher.run(job, jobParameters));

        try {
            JobExecution execution = future.get(10, TimeUnit.SECONDS);
            logger.info("job execution is done");
        } catch (TimeoutException e) {
            future.cancel(true);
            logger.error("Job execution timed out and was cancelled.");
        } catch (Exception e) {
            logger.error("some error:{}",e.getMessage());
        } finally {
            executorService.shutdown();
        }
    }
}
