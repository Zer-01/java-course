package edu.hw8;

import edu.hw8.Task2.FixedThreadPool;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    void oneThreadTest() throws InterruptedException {
        long expResult = 2;
        final long[] result = new long[1];

        FixedThreadPool fixedThreadPool = FixedThreadPool.create(1);
        fixedThreadPool.execute(() -> {
            result[0] = fibonacci(46);
        });

        fixedThreadPool.start();
        Thread.sleep(100);
        fixedThreadPool.close();

        assertThat(result[0])
            .isEqualTo(1836311903);
    }

    @Test
    void threeThreadTest() throws InterruptedException {
        long expResult = 2;
        final long[] result = new long[3];

        FixedThreadPool fixedThreadPool = FixedThreadPool.create(3);
        fixedThreadPool.execute(() -> result[0] = fibonacci(46));
        fixedThreadPool.execute(() -> result[1] = fibonacci(46));
        fixedThreadPool.execute(() -> result[2] = fibonacci(46));

        fixedThreadPool.start();
        Thread.sleep(100);
        fixedThreadPool.close();

        assertThat(result) //выполняется за то же время, что и предыдущий тест
            .containsOnly(1836311903);
    }

    private static long fibonacci(long n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
