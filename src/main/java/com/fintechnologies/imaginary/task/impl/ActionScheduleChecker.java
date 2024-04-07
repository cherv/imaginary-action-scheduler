package com.fintechnologies.imaginary.task.impl;

import com.fintechnologies.imaginary.domain.ActionSchedule;
import com.fintechnologies.imaginary.task.IActionScheduleChecker;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ActionScheduleChecker implements IActionScheduleChecker {

    @Override
    public boolean checkIfActionRequiredNowInTimeZone(List<ActionSchedule> actionSchedules, ZoneId zoneId) {
        ZonedDateTime currentDateTime = getNow(zoneId);

        return actionSchedules.stream()
            .anyMatch(schedule -> checkSchedule(schedule, currentDateTime));
    }

    ZonedDateTime getNow(ZoneId zoneId) {
        return ZonedDateTime.now(zoneId);
    }

    private boolean checkSchedule(ActionSchedule actionSchedule, ZonedDateTime currentTime) {
        LocalTime actionTime = actionSchedule.getLocalTime();

        return currentTime.getHour() == actionTime.getHour()
            && currentTime.getMinute() == actionTime.getMinute()
            && actionSchedule.getDaysOfWeek().contains(currentTime.getDayOfWeek());
    }
}
