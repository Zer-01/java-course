package edu.hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class PiCalculate {
    private static final double SQUARE_SIDE = 1;
    private static final double CIRCLE_RADIUS = SQUARE_SIDE / 2;
    private static final double FORMULA_CONST = 4;

    private PiCalculate() {
    }

    public static double singleThreadCalculate(long dotsCount) {
        Random random = new Random();
        double x;
        double y;
        long circleCount = 0;
        for (long i = 0; i < dotsCount; i++) {
            x = random.nextDouble(0, SQUARE_SIDE);
            y = random.nextDouble(0, SQUARE_SIDE);
            if (isInCircle(x, y)) {
                circleCount++;
            }
        }
        return FORMULA_CONST * ((double) circleCount / (double) dotsCount);
    }

    public static double multiThreadCalculate(long dotsCount, int threadsCount) {
        List<Thread> threads = new ArrayList<>(threadsCount);
        AtomicLong circleCount = new AtomicLong();
        for (int i = 0; i < threadsCount; i++) {
            long iterationsCount = (i == 0)
                ? dotsCount / threadsCount + dotsCount % threadsCount
                : dotsCount / threadsCount;
            threads.add(new Thread(() -> {
                double x;
                double y;
                long threadCircleCount = 0;
                ThreadLocalRandom random = ThreadLocalRandom.current();
                for (long j = 0; j < iterationsCount; j++) {
                    x = random.nextDouble(0, SQUARE_SIDE);
                    y = random.nextDouble(0, SQUARE_SIDE);
                    if (isInCircle(x, y)) {
                        threadCircleCount++;
                    }
                }
                circleCount.addAndGet(threadCircleCount);
            }));
        }

        for (var thread : threads) {
            thread.start();
        }

        for (var thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return FORMULA_CONST * ((double) circleCount.get() / (double) dotsCount);
    }

    private static boolean isInCircle(double x, double y) {
        return Math.pow(x - CIRCLE_RADIUS, 2) + Math.pow(y - CIRCLE_RADIUS, 2) <= CIRCLE_RADIUS * CIRCLE_RADIUS;
    }
}
