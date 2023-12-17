package edu.hw8;

import edu.hw8.Task1.Client;
import edu.hw8.Task1.Server;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Task1Test {
    @Test
    void oneClientTest() throws InterruptedException {
        Server server = new Server(4);
        Client client = new Client();
        String in = "личности";
        String expOut = "Не переходи на личности там, где их нет";
        server.startInNewThread();
        Thread.sleep(150);
        client.connect();

        String out = client.sendRequest(in);
        client.close();
        server.stop();

        assertThat(out)
            .isEqualTo(expOut);
    }

    @Test
    void twoClientTest() throws InterruptedException {
        Server server = new Server(4);
        Client client1 = new Client();
        Client client2 = new Client();
        boolean[] isContinue = {true};
        String in = "личности";
        String expOut = "Не переходи на личности там, где их нет";
        server.startInNewThread();
        Thread.sleep(150);
        client1.connect();
        Thread thread = new Thread(()->clientTester(client2, isContinue));
        thread.start();

        String out = client1.sendRequest(in);
        isContinue[0] = false;
        thread.join();
        client1.close();
        client2.close();
        server.stop();

        assertThat(out)
            .isEqualTo(expOut);
    }

    @Test
    void invalidMaxConnections() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(()->{
                new Server(-2);
            });
    }

    private void clientTester(Client client, boolean[] isContinue) {
        client.connect();
        String in = "личности";
        String out = "Не переходи на личности там, где их нет";
        while (isContinue[0]) {
            if(!client.sendRequest(in).equals(out)) {
                throw new RuntimeException("Wrong answer");
            }
        }
    }
}
