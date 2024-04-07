package com.fintechnologies.imaginary.imaginary.impl;

import com.fintechnologies.imaginary.imaginary.IImaginaryActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ImaginaryActionService implements IImaginaryActionService {

    @Override
    public void executeAction() {
        log.info("Imaginary action completed!");
    }
}
