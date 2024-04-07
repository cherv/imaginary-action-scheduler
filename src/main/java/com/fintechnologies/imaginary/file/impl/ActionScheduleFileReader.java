package com.fintechnologies.imaginary.file.impl;

import com.fintechnologies.imaginary.domain.ActionSchedule;
import com.fintechnologies.imaginary.exception.ScheduleFileException;
import com.fintechnologies.imaginary.file.IActionScheduleFileReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ActionScheduleFileReader implements IActionScheduleFileReader {

    private static final String COMMA_DELIMITER = ",";

    private final ActionScheduleConverter actionScheduleConverter;

    @Override
    public List<ActionSchedule> read(String filename) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename);

        if (inputStream == null) {
            throw new ScheduleFileException("Schedule settings file cannot be reached");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return bufferedReader.lines()
                .skip(1)
                .map(line -> line.split(COMMA_DELIMITER))
                .map(actionScheduleConverter::convert)
                .toList();
        } catch (IOException e) {
            throw new ScheduleFileException("Schedule settings file cannot be read", e);
        }
    }
}
