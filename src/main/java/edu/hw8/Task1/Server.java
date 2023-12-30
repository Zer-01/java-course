package edu.hw8.Task1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private static final int PORT = 8888;
    private final int maxConnections;
    private final AtomicInteger connectionsCount = new AtomicInteger();
    private static final int CLIENT_BUFFER_SIZE = 1024;
    private boolean isInWork;
    private Thread thread;

    public Server(int maxConnections) {
        if (maxConnections < 1) {
            throw new IllegalArgumentException();
        }
        this.maxConnections = maxConnections;
    }

    public void start() {
        isInWork = true;
        try (Selector selector = Selector.open();
             ExecutorService executorService = Executors.newFixedThreadPool(maxConnections)) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (isInWork) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable() && connectionsCount.get() < maxConnections) {
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel client = serverChannel.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                        connectionsCount.incrementAndGet();
                    } else if (key.isReadable()) {
                        key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
                        executorService.submit(() -> processRequest(key));
                    }
                }
            }

            executorService.shutdownNow();
            closeSelectorChannels(selector);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startInNewThread() {
        thread = new Thread(this::start);
        thread.start();
    }

    public void stop() {
        if (thread == null) {
            return;
        }
        try {
            isInWork = false;
            thread.join();
            thread = null;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void processRequest(SelectionKey key) {
        try {
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(CLIENT_BUFFER_SIZE);
            int bytesRead = client.read(buffer);
            key.interestOps(key.interestOps() | SelectionKey.OP_READ);
            key.selector().wakeup();

            if (bytesRead == -1 || bytesRead == 0) {
                if (bytesRead == -1) {
                    key.cancel();
                    client.close();
                    connectionsCount.decrementAndGet();
                }
                return;
            }

            String request = new String(buffer.array(), 0, bytesRead).trim();
            String response = AnsweringMachine.getAnswer(request);

            buffer.clear();
            buffer.put(response.getBytes());
            buffer.flip();
            client.write(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeSelectorChannels(Selector selector) throws IOException {
        for (SelectionKey key : selector.keys()) {
            key.channel().close();
            key.cancel();
        }
    }
}
