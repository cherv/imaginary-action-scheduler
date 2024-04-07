package com.fintechnologies.imaginary.file.impl;

import com.fintechnologies.imaginary.domain.ActionSchedule;
import com.fintechnologies.imaginary.exception.ScheduleFileException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static com.fintechnologies.imaginary.file.impl.ActionScheduleConverter.COLON_DELIMITER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ActionScheduleConverterTest {

    private static final int HOURS = 13;
    private static final int MINUTES = 15;
    private static final String TIME = HOURS + COLON_DELIMITER + MINUTES;
    private static final String MON_WEN_BITMASK = "5";
    private static final List<DayOfWeek> BITMASK_DAYS = List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);

    private final ActionScheduleConverter converter = new ActionScheduleConverter();

    @Test
    void testConvert() {
        // Given
        String[] scheduleData = {TIME, MON_WEN_BITMASK};

        // When
        ActionSchedule actual = converter.convert(scheduleData);

        // Then
        LocalTime expectedTime = LocalTime.of(HOURS, MINUTES);
        ActionSchedule expected = new ActionSchedule(expectedTime, BITMASK_DAYS);

        assertEquals(expected, actual);
    }

    @Test
    void testConvertInvalidFileFormat() {
        // Given
        String[] scheduleData = {TIME};

        // When
        ScheduleFileException result = assertThrows(ScheduleFileException.class, () -> converter.convert(scheduleData));

        // Then
        assertEquals("Invalid file format", result.getMessage());
    }

    @Test
    void testConvertInvalidTimeFormat() {
        // Given
        String invalidTime = "12-23";
        String[] scheduleData = {invalidTime, MON_WEN_BITMASK};

        // When
        ScheduleFileException result = assertThrows(ScheduleFileException.class, () -> converter.convert(scheduleData));

        // Then
        assertEquals("Invalid time format. 'HH:MM' is required", result.getMessage());
    }

    @Test
    void testConvertInvalidBitmaskNegative() {
        // Given
        String[] scheduleData = {TIME, "-A"};

        // When
        ScheduleFileException result = assertThrows(ScheduleFileException.class, () -> converter.convert(scheduleData));

        // Then
        assertEquals("Bitmask should have positive value", result.getMessage());
    }

    @Test
    void testConvertInvalidBitmaskValue() {
        // Given
        String[] scheduleData = {TIME, "invalid"};

        // When
        ScheduleFileException result = assertThrows(ScheduleFileException.class, () -> converter.convert(scheduleData));

        // Then
        assertEquals("Invalid bitmask value", result.getMessage());
    }
}