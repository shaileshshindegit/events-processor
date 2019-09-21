package com.assignment.events;

import com.assignment.events.Respository.DBManager;
import com.assignment.events.Respository.impl.EventsRepositoryImpl;
import com.assignment.events.exceptions.TechnicalException;
import com.assignment.events.service.FileProcessorService;
import com.assignment.events.service.impl.FileProcessorServiceImpl;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsProcessorApp {

    private static final Logger LOG = LoggerFactory.getLogger(EventsProcessorApp.class);
    private FileProcessorService service;

    public EventsProcessorApp(FileProcessorService service) {
        this.service = service;
    }

    public static void main(String[] args) throws TechnicalException {
        String filePath = args[0];
        //FileProcessorService should be injected by some external means.
        EventsProcessorApp app = new EventsProcessorApp(new FileProcessorServiceImpl(new EventsRepositoryImpl()));
        app.process(filePath);
    }

    private void process(String filePath) throws TechnicalException {

        LOG.info("Event processing application started...");
        LOG.info("Processing file...");
        File file = new File(filePath);

        DBManager.getInstance().initializeData();
        service.process(file);

        LOG.info("File processing finished...");
        LOG.info("Event processing application stopped...");
    }
}
