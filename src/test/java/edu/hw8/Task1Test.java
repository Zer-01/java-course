package edu.hw8;

import edu.hw8.Task1.Client;
import edu.hw8.Task1.Server;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

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
        String in = "личности";
        String expOut = "Не переходи на личности там, где их нет";
        server.startInNewThread();
        Thread.sleep(150);
        client1.connect();
        Thread thread = new Thread(()->clientTester(client2));
        thread.start();

        String out = client1.sendRequest(in);
        thread.interrupt();
        client1.close();
        client2.close();
        server.stop();

        assertThat(out)
            .isEqualTo(expOut);
    }

    private void clientTester(Client client) {
        client.connect();
        String in = "личности";
        String out = "Не переходи на личности там, где их нет";
        while (client.sendRequest(in).equals(out)) {
        }
        throw new RuntimeException("Wrong answer");
    }
}
