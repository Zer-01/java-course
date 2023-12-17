package edu.hw8.Task2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FixedThreadPool implements ThreadPool {
    private final int threadsCount;
    private final Thread[] threads;
    private final Queue<Runnable> taskQueue;
    private volatile boolean isRunning;

    private FixedThreadPool(int threadsCount) {
        this.threadsCount = threadsCount;
        threads = new Thread[threadsCount];
        taskQueue = new ConcurrentLinkedQueue<>();
        isRunning = true;
    }

    @Override
    public void start() {
        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new Thread(() -> {
                Runnable tmp;
                while (isRunning) {
                    tmp = taskQueue.poll();
                    if (tmp != null) {
                        tmp.run();
                    }
                }
            });
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        taskQueue.offer(runnable);
    }

    @Override
    public void close() {
        try {
            isRunning = false;
            for (int i = 0; i < threadsCount; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static FixedThreadPool create(int threadsCount) {
        if (threadsCount < 1) {
            throw new IllegalArgumentException();
        }
        return new FixedThreadPool(threadsCount);
    }
}
