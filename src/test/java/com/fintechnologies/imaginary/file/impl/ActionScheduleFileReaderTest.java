package com.fintechnologies.imaginary.file.impl;

import com.fintechnologies.imaginary.domain.ActionSchedule;
import com.fintechnologies.imaginary.exception.ScheduleFileException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActionScheduleFileReaderTest {

    @Mock
    private ActionScheduleConverter actionScheduleConverter;

    @InjectMocks
    private ActionScheduleFileReader reader;

    @Test
    void testRead() {
        // Given
        List<DayOfWeek> days = List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
        LocalTime localTime = LocalTime.of(15, 0);
        ActionSchedule actionSchedule = new ActionSchedule(localTime, days);

        String[] scheduleData = {"15:00", "15", "Mon/Wed/Fri"};
        when(actionScheduleConverter.convert(scheduleData))
            .thenReturn(actionSchedule);

        String filename = "./action_schedule_test.csv";

        // When
        List<ActionSchedule> actual = reader.read(filename);

        // Then
        assertEquals(List.of(actionSchedule), actual);
        verify(actionScheduleConverter).convert(scheduleData);
    }

    @Test
    void testReadInvalidFilePath() {
        // Given
        String filename = "./invalid.csv";

        // When
        ScheduleFileException result = assertThrows(ScheduleFileException.class, () -> reader.read(filename));

        // Then
        assertEquals("Schedule settings file cannot be reached", result.getMessage());
    }
}