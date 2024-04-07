package com.fintechnologies.imaginary.task.impl;

import com.fintechnologies.imaginary.task.IActionConditionalTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class Scheduler {

    private final IActionConditionalTask actionConditionalTask;

    @Scheduled(cron = "${task.scheduler.cron}")
    public void execute() {
        log.info("Scheduler execution started");

        actionConditionalTask.run();
    }
}
