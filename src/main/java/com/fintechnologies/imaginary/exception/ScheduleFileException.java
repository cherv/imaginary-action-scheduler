package com.fintechnologies.imaginary.exception;

public class ScheduleFileException extends RuntimeException {

    public ScheduleFileException(String message) {
        super(message);
    }

    public ScheduleFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
