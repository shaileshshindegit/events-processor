package com.assignment.events.Respository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBManager {

    private static final Logger LOG = LoggerFactory.getLogger(DBManager.class);

    private static DBManager instance;
    private static Connection conn;
    private static String db = "jdbc:hsqldb:file:events_db;";
    private static String user = "SA";
    private static String password = "";

    private DBManager(){}

    static{
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            LOG.debug("Database driver loaded....");
        } catch (ClassNotFoundException e) {
            LOG.error("Unable to load database driver : {}",e.getMessage());
            e.printStackTrace();
        }
    }

    public static DBManager getInstance(){
        if(instance == null){
            instance = new DBManager();
        }
        return instance;
    }


    public Connection getConnection() throws SQLException {
        if (conn == null) {
            conn = DriverManager.getConnection(db, user, password);
        }
        return conn;
    }

    public static void initializeData() {
        LOG.info("Initializing application data....");
        try (Statement stmt = getInstance().getConnection().createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS events (" +
                    "id VARCHAR(50) PRIMARY KEY," +
                    "event_id VARCHAR(50) NOT NULL," +
                    "duration VARCHAR(20) NOT NULL, host VARCHAR(20), type VARCHAR(20), alert VARCHAR(5));");
            LOG.info("Data initialized successfully...");
        } catch (SQLException e) {
            LOG.error("Error while initializing data : {}",e.getMessage());
            e.printStackTrace();
        }
    }
}