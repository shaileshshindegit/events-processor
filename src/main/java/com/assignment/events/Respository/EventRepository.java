package com.assignment.events.Respository;

import com.assignment.events.exceptions.TechnicalException;
import com.assignment.events.models.LogEvent;

public interface EventRepository {
    void saveEvent(LogEvent event) throws TechnicalException;
}
