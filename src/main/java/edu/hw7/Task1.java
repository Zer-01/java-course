package edu.hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Task1 {
    private Task1() {
    }

    public static long multiThreadIncrement(int threadsCount, long incrementCount) {
        AtomicLong atomic = new AtomicLong();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadsCount; i++) {
            threads.add(new Thread(() -> {
                for (long j = 0; j < incrementCount; j++) {
                    atomic.incrementAndGet();
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return atomic.get();
    }
}
