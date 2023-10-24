package edu.hw2.Task3;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        if (manager == null || maxAttempts < 1) {
            throw new IllegalArgumentException();
        }
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void tryExecute(String command) {
        Exception cause = null;
        for (int i = 0; i < maxAttempts; i++) {
            try (Connection connection = manager.getConnection()) {
                connection.execute(command);
                return;
            } catch (Exception ex) {
                cause = ex;
            }
        }
        throw new ConnectionException("tryExecute error", cause);
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    public int getActiveConnectionsCount() {
        return manager.getActiveConnectionsCount();
    }
}
