package com.fintechnologies.imaginary.task.impl;

import com.fintechnologies.imaginary.task.IActionConditionalTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SchedulerTest {

    @Mock
    private IActionConditionalTask actionConditionalTask;

    @InjectMocks
    private Scheduler scheduler;

    @Test
    void testExecute() {
        // Given

        // When
        scheduler.execute();

        // Then
        verify(actionConditionalTask).run();
    }
}