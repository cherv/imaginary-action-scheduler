package com.fintechnologies.imaginary.task.impl;

import com.fintechnologies.imaginary.domain.ActionSchedule;
import com.fintechnologies.imaginary.file.IActionScheduleFileReader;
import com.fintechnologies.imaginary.imaginary.IImaginaryActionService;
import com.fintechnologies.imaginary.task.IActionConditionalTask;
import com.fintechnologies.imaginary.task.IActionScheduleChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
public class ActionConditionalTask implements IActionConditionalTask {

    public static final String SCHEDULE_SETTINGS_FILENAME = "action_schedule.csv";

    private final IActionScheduleChecker scheduleChecker;
    private final IActionScheduleFileReader scheduleFileReader;
    private final IImaginaryActionService imaginaryActionService;
    private final String actionTimeZoneId;

    public ActionConditionalTask(IActionScheduleChecker scheduleChecker,
                                 IActionScheduleFileReader scheduleFileReader,
                                 IImaginaryActionService imaginaryActionService,
                                 @Value("${imaginary-action.time-zone.id}") String actionTimeZoneId) {
        this.scheduleChecker = scheduleChecker;
        this.scheduleFileReader = scheduleFileReader;
        this.imaginaryActionService = imaginaryActionService;
        this.actionTimeZoneId = actionTimeZoneId;
    }

    @Override
    public void run() {
        List<ActionSchedule> actionSchedules = scheduleFileReader.read(SCHEDULE_SETTINGS_FILENAME);
        ZoneId timeZoneId = ZoneId.of(actionTimeZoneId);

        boolean actionRequired = scheduleChecker.checkIfActionRequiredNowInTimeZone(actionSchedules, timeZoneId);

        if (actionRequired) {
            imaginaryActionService.executeAction();
        }
    }
}
