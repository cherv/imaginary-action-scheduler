package com.fintechnologies.imaginary.task;

import com.fintechnologies.imaginary.domain.ActionSchedule;

import java.time.ZoneId;
import java.util.List;

public interface IActionScheduleChecker {

    boolean checkIfActionRequiredNowInTimeZone(List<ActionSchedule> actionSchedules, ZoneId zoneId);
}
