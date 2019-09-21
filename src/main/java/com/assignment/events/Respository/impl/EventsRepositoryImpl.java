package com.assignment.events.Respository.impl;

import com.assignment.events.Respository.DBManager;
import com.assignment.events.Respository.EventRepository;
import com.assignment.events.exceptions.TechnicalException;
import com.assignment.events.models.LogEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsRepositoryImpl implements EventRepository {

    private static final Logger LOG = LoggerFactory.getLogger(EventsRepositoryImpl.class);
    private static final String INSERT_EVENT_SQL = "INSERT INTO events VALUES (?,?,?,?,?,?)";

    public void saveEvent(LogEvent event) throws TechnicalException {
        try (PreparedStatement preparedStatement = DBManager.getInstance().getConnection().prepareStatement(INSERT_EVENT_SQL)) {
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, event.getId());
            preparedStatement.setString(3, event.getDuration());
            preparedStatement.setString(4, event.getHost());
            preparedStatement.setString(5, event.getType());
            preparedStatement.setString(6, event.getAlert());
            preparedStatement.executeUpdate();
            preparedStatement.getConnection().commit();
            LOG.debug("Event {} inserted successfully in DB", event.getId());
        } catch (SQLException e) {
            LOG.error("Unable to save event : {}", e.getMessage());
            e.printStackTrace();
            throw new TechnicalException("Unable to save event",e);
        }
    }
}
