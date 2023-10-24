package edu.hw2.Task3;

public interface ConnectionManager {
    Connection getConnection();

    void closeConnection(Connection connection);

    int getActiveConnectionsCount();
}
