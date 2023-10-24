package edu.hw2.Task3;

public class DefaultConnectionManager implements ConnectionManager {
    private int activeConnectionsCount = 0;
    private int stableConnectCount = 0;
    private final static int MAX_STABLE_CONNECT_COUNT = 2; //Каждое третье соединение - faulty

    @Override
    public Connection getConnection() {
        activeConnectionsCount++;
        if (stableConnectCount < MAX_STABLE_CONNECT_COUNT) {
            stableConnectCount++;
            return new StableConnection(this);
        } else {
            stableConnectCount = 0;
            return new FaultyConnection(this);
        }
    }

    @Override
    public void closeConnection(Connection connection) {
        if (connection != null) {
            activeConnectionsCount--;
        }
    }

    @Override
    public int getActiveConnectionsCount() {
        return activeConnectionsCount;
    }
}
