package edu.hw2;

import edu.hw2.Task3.Connection;
import edu.hw2.Task3.ConnectionException;
import edu.hw2.Task3.ConnectionManager;
import edu.hw2.Task3.DefaultConnectionManager;
import edu.hw2.Task3.FaultyConnectionManager;
import edu.hw2.Task3.PopularCommandExecutor;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task3Test {
    @Test
    void normalTest() {
        PopularCommandExecutor executor = new PopularCommandExecutor(new DefaultConnectionManager(), 1);
        executor.updatePackages();

        int activeConnect = executor.getActiveConnectionsCount();

        assertThat(activeConnect)
            .isEqualTo(0); //Исключений не было, подключение закрылось => работает
    }

    @Test
    void catchException() {
        PopularCommandExecutor executor = new PopularCommandExecutor(new DefaultConnectionManager(), 1);

        for(int i = 0; i<2;i++) {
            executor.updatePackages(); //первые 2 выполнения - удачные
        }

        assertThatExceptionOfType(ConnectionException.class)
            .isThrownBy(()->{
                executor.updatePackages();
            });
    }

    @Test
    void faultyConnManagerTest() {
        ConnectionManager manager = new FaultyConnectionManager();

        Connection connection = manager.getConnection();

        assertThat(manager.getActiveConnectionsCount())
            .isEqualTo(1);

        try {
            connection.close();
        } catch (Exception ex) {

        }

        assertThat(manager.getActiveConnectionsCount())
            .isEqualTo(0);
    }
}
