package com.fintechnologies.imaginary.task.impl;

import com.fintechnologies.imaginary.domain.ActionSchedule;
import com.fintechnologies.imaginary.file.IActionScheduleFileReader;
import com.fintechnologies.imaginary.imaginary.IImaginaryActionService;
import com.fintechnologies.imaginary.task.IActionScheduleChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneId;
import java.util.List;

import static com.fintechnologies.imaginary.task.impl.ActionConditionalTask.SCHEDULE_SETTINGS_FILENAME;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActionConditionalTaskTest {

    public static final String TIME_ZONE_ID = "Africa/Lagos";
    @Mock
    private IActionScheduleChecker scheduleChecker;
    @Mock
    private IActionScheduleFileReader scheduleFileReader;
    @Mock
    private IImaginaryActionService imaginaryActionService;

    private ActionConditionalTask actionConditionalTask;

    @BeforeEach
    public void setUp() {
        actionConditionalTask = new ActionConditionalTask(scheduleChecker, scheduleFileReader, imaginaryActionService, TIME_ZONE_ID);
    }

    @Test
    void testRunWithActionRequired() {
        // Given
        List<ActionSchedule> schedules = List.of();
        when(scheduleFileReader.read(SCHEDULE_SETTINGS_FILENAME))
            .thenReturn(schedules);

        when(scheduleChecker.checkIfActionRequiredNowInTimeZone(schedules, ZoneId.of(TIME_ZONE_ID)))
            .thenReturn(true);

        // When
        actionConditionalTask.run();

        // Then
        verify(scheduleFileReader).read(SCHEDULE_SETTINGS_FILENAME);
        verify(scheduleChecker).checkIfActionRequiredNowInTimeZone(schedules, ZoneId.of(TIME_ZONE_ID));
        verify(imaginaryActionService).executeAction();
    }

    @Test
    void testRunWithActionNotRequired() {
        // Given
        List<ActionSchedule> schedules = List.of();
        when(scheduleFileReader.read(SCHEDULE_SETTINGS_FILENAME))
            .thenReturn(schedules);

        when(scheduleChecker.checkIfActionRequiredNowInTimeZone(schedules, ZoneId.of(TIME_ZONE_ID)))
            .thenReturn(false);

        // When
        actionConditionalTask.run();

        // Then
        verify(scheduleFileReader).read(SCHEDULE_SETTINGS_FILENAME);
        verify(scheduleChecker).checkIfActionRequiredNowInTimeZone(schedules, ZoneId.of(TIME_ZONE_ID));
        verify(imaginaryActionService, never()).executeAction();
    }
}