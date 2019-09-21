package com.assignment.events.service;

import com.assignment.events.exceptions.TechnicalException;

import java.io.File;

public interface FileProcessorService {
    void process(final File file) throws TechnicalException;
}
