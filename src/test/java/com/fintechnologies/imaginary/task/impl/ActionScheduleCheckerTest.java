package com.fintechnologies.imaginary.task.impl;

import com.fintechnologies.imaginary.domain.ActionSchedule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ActionScheduleCheckerTest {

    private static final ZoneId ZONE_ID = ZoneId.of("Africa/Lagos");
    private static final LocalTime LOCAL_TIME = LocalTime.of(15, 0);
    private static final LocalDate FRIDAY_LOCAL_DATE = LocalDate.of(2024, Month.APRIL, 5);
    private static final LocalDate SATURDAY_LOCAL_DATE = LocalDate.of(2024, Month.APRIL, 6);
    private static final List<DayOfWeek> SCHEDULE_DAYS = List.of(DayOfWeek.FRIDAY);
    private static final ActionSchedule ACTION_SCHEDULE = new ActionSchedule(LOCAL_TIME, SCHEDULE_DAYS);
    private static final List<ActionSchedule> ACTION_SCHEDULES = List.of(ACTION_SCHEDULE);

    @Spy
    private ActionScheduleChecker checker;

    @Test
    void testCheckIfActionRequiredNowInTimeZoneTrue() {
        // Given
        LocalDateTime localDateTime = LocalDateTime.of(FRIDAY_LOCAL_DATE, LOCAL_TIME);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZONE_ID);

        doReturn(zonedDateTime)
            .when(checker)
            .getNow(ZONE_ID);

        // When
        boolean actual = checker.checkIfActionRequiredNowInTimeZone(ACTION_SCHEDULES, ZONE_ID);

        // Then
        assertTrue(actual);
    }

    @Test
    void testCheckIfActionRequiredNowInTimeZoneFalse() {
        // Given
        LocalDateTime localDateTime = LocalDateTime.of(SATURDAY_LOCAL_DATE, LOCAL_TIME);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZONE_ID);

        doReturn(zonedDateTime)
            .when(checker)
            .getNow(ZONE_ID);

        // When
        boolean actual = checker.checkIfActionRequiredNowInTimeZone(ACTION_SCHEDULES, ZONE_ID);

        // Then
        assertFalse(actual);
    }
}