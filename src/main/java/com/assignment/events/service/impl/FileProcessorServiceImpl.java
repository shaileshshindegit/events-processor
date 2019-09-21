package com.assignment.events.service.impl;

import com.assignment.events.Respository.EventRepository;
import com.assignment.events.exceptions.TechnicalException;
import com.assignment.events.models.LogEvent;
import com.assignment.events.service.FileProcessorService;
import com.google.gson.Gson;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.assignment.events.utils.AppConstants.EVENT_STARTED;
import static com.assignment.events.utils.AppConstants.FILE_PROCSSING_FAILED_MSG;

public class FileProcessorServiceImpl implements FileProcessorService {

    private static final Logger LOG = LoggerFactory.getLogger(FileProcessorServiceImpl.class);

    private EventRepository eventsRepository;

    public FileProcessorServiceImpl(EventRepository eventsRepository){
        this.eventsRepository = eventsRepository;
    }

    public void process(File file) throws TechnicalException {

        try (Stream linesStream = Files.lines(file.toPath())) {
            List<LogEvent> collect = (List<LogEvent>) linesStream.map(FileProcessorServiceImpl::jsonToModel).collect(Collectors.toList());
            LOG.debug("Data in file : {}",collect);

            //Collecting all events in HashMap and verify second event using HashMap key
            Map<String, LogEvent> logEventMap = new HashMap();

            collect.forEach(e -> {
                if (logEventMap.get(e.getId()) != null) {
                    //If object exist in hashmap then compare with currently iterating element
                    //to calculate event duration
                    LogEvent logEvent = logEventMap.get(e.getId());
                    long eventDuration;
                    if (EVENT_STARTED.equalsIgnoreCase(logEvent.getState())) {
                        eventDuration = e.getTimestamp() - logEvent.getTimestamp();
                    } else {
                        eventDuration = logEvent.getTimestamp() - e.getTimestamp();
                    }

                    logEvent.setDuration(eventDuration + "");
                    logEvent.setAlert("false");

                    //Set alert to true if event takes more then 4ms
                    if (eventDuration > 4) {
                        LOG.debug("Event with ID " + e.getId() + " took more than 4 ms");
                        logEvent.setAlert("true");
                    }

                    LOG.info("Logging event {} in Database",logEvent);
                    eventsRepository.saveEvent(logEvent);

                    //Removing event from HashMap so that if same events appears multiple times in log file,
                    // then system should handle it properly.
                    logEventMap.remove(e.getId());
                } else {
                    logEventMap.put(e.getId(), e);
                }
            });
        } catch (Exception exception) {
           LOG.error("Error processing file {}" + exception.getMessage());
           throw new TechnicalException(FILE_PROCSSING_FAILED_MSG,exception);
        }
    }

    private static LogEvent jsonToModel(Object line) {
        return new Gson().fromJson(line.toString(), LogEvent.class);
    }
}
