package com.example.jwtspringboot.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.scheduling.support.ScheduledMethodRunnable;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

@Component
@Slf4j
public class Scheduling {

    @Autowired
    private TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;
    private int count = 0;

    @Scheduled(fixedRate = 2000)
    public void schedulingWithFixedRate() {
        log.info("Task running");
        count++;
        if (conditionToStopTask(count)) {
            if (scheduledFuture != null) {
                scheduledFuture.cancel(true);
                log.info("Task stopped");
            }
        }
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
