package edu.hw2.Task3;

public class FaultyConnectionManager implements ConnectionManager {
    private int activeConnectionsCount = 0;

    @Override
    public Connection getConnection() {
        activeConnectionsCount++;
        return new FaultyConnection(this);
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
