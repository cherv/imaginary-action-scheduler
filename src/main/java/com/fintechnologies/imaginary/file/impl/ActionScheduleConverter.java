package com.fintechnologies.imaginary.file.impl;

import com.fintechnologies.imaginary.domain.ActionSchedule;
import com.fintechnologies.imaginary.exception.ScheduleFileException;
import com.fintechnologies.imaginary.file.IActionScheduleConverter;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
public class ActionScheduleConverter implements IActionScheduleConverter {

    public static final String COLON_DELIMITER = ":";

    @Override
    public ActionSchedule convert(String[] scheduleData) {
        if (scheduleData.length < 2) {
            throw new ScheduleFileException("Invalid file format");
        }

        String time = scheduleData[0];
        String scheduleHexBitmask = scheduleData[1];

        LocalTime localTime = convertToLocalTime(time);
        List<DayOfWeek> days = convertToDaysOfWeek(scheduleHexBitmask);

        return new ActionSchedule(localTime, days);
    }

    private LocalTime convertToLocalTime(String time) {
        String[] values = time.split(COLON_DELIMITER);

        if (values.length < 2) {
            throw new ScheduleFileException("Invalid time format. 'HH:MM' is required");
        }

        int hours = Integer.parseInt(values[0]);
        int minutes = Integer.parseInt(values[1]);

        return LocalTime.of(hours, minutes);
    }

    private List<DayOfWeek> convertToDaysOfWeek(String scheduleHexBitmask) {
        byte scheduleBitmask = convertToBitmask(scheduleHexBitmask);

        return Arrays.stream(DayOfWeek.values())
            .filter(dayOfWeek -> (convertToBitmask(dayOfWeek) & scheduleBitmask) != 0)
            .toList();
    }

    private byte convertToBitmask(String scheduleHexBitmask) {
        try {
            byte scheduleBitmask = Byte.parseByte(scheduleHexBitmask, 16);

            if(scheduleBitmask < 0) {
                throw new ScheduleFileException("Bitmask should have positive value");
            }

            return scheduleBitmask;
        } catch (NumberFormatException exception) {
            throw new ScheduleFileException("Invalid bitmask value");
        }
    }

    private int convertToBitmask(DayOfWeek dayOfWeek) {
        return 1 << dayOfWeek.getValue() - 1;
    }
}
