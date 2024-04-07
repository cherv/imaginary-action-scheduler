package com.fintechnologies.imaginary.domain;

import lombok.Value;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Value
public class ActionSchedule {

    LocalTime localTime;
    List<DayOfWeek> daysOfWeek;
}
