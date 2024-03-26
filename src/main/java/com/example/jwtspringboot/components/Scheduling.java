package com.example.jwtspringboot.components;

import com.example.jwtspringboot.dtos.MailStructure;
import com.example.jwtspringboot.services.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduling {
    private final MailService mailService;
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;

    //    @Scheduled(fixedRate = 2000)
    public void schedulingWithFixedRate() {
        mailService.sendMail("thinhtran383.au@gmail.com", MailStructure.builder()
                .message("Hello from spring: " + LocalDateTime.now())
                .subject("Send report")
                .build());

        log.info("Mail has sent");


    }

    private boolean conditionToStopTask(int condition) {
        return condition == 3;
    }

    public void startTask() throws NoSuchMethodException {
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            scheduledFuture = taskScheduler.schedule(new ScheduledMethodRunnable(this, "schedulingWithFixedRate"), new PeriodicTrigger(2000));
            log.info("Task started");
        } else {
            log.info("Task is already running");
        }
    }

    // Method to stop the task
    public void stopTask() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            log.info("Task stopped");
        } else {
            log.info("No task is running");
        }
    }
}
