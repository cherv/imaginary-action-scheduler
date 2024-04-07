package com.fintechnologies.imaginary.file;

import com.fintechnologies.imaginary.domain.ActionSchedule;

public interface IActionScheduleConverter {

    ActionSchedule convert(String[] scheduleData);
}