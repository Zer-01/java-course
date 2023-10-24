package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StableConnection implements Connection {
    private final ConnectionManager manager;
    private final static Logger LOGGER = LogManager.getLogger();

    public StableConnection(ConnectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String command) {
        LOGGER.info("\"" + command + "\" executed");
    }

    @Override
    public void close() {
        if (manager != null) {
            manager.closeConnection(this);
        }
    }
}
