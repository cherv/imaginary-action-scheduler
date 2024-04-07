package com.fintechnologies.imaginary.file;

import com.fintechnologies.imaginary.domain.ActionSchedule;

import java.util.List;

public interface IActionScheduleFileReader {

    List<ActionSchedule> read(String filename);
}
