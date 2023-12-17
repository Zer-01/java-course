package edu.hw8.Task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private static final int PORT = 8888;
    private static final int BUFFER_SIZE = 1024;
    private SocketChannel channel;

    public void connect(String address) {
        try {
            channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(address, PORT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect() {
        connect("localhost");
    }

    public void close() {
        try {
            channel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendRequest(String request) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            buffer.put(request.getBytes());
            buffer.flip();
            channel.write(buffer);

            buffer.clear();
            int bytesRead = channel.read(buffer);
            buffer.flip();
            return new String(buffer.array(), 0, bytesRead).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
