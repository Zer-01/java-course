package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private final ConnectionManager manager;
    private final static Logger LOGGER = LogManager.getLogger();
    private int successExecCount = 0;
    private final static int MAX_SUCCESS_EXEC_COUNT = 0; //Каждое выполнение - неудачное(для тестов)

    public FaultyConnection(ConnectionManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(String command) {
        if (successExecCount < MAX_SUCCESS_EXEC_COUNT) {
            LOGGER.info("\"" + command + "\" executed");
            successExecCount++;
        } else {
            successExecCount = 0;
            throw new ConnectionException("execute error");
        }
    }

    @Override
    public void close() {
        if (manager != null) {
            manager.closeConnection(this);
        }
    }
}
