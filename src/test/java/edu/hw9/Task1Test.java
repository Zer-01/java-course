package edu.hw9;

import edu.hw9.Task1.Metric;
import edu.hw9.Task1.StatsCollector;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task1Test {
    boolean inProgress;

    @Test
    void normalTest() throws InterruptedException {
        int generatorThreadsCount = 2;
        Thread[] threads = new Thread[generatorThreadsCount];
        final StatsCollector collector = new StatsCollector();
        String testInStr = "Test";
        double[] testArr = {1.0, 2.5, 3.5, -1.5, -0.5};

        Metric expMetric = new Metric(testInStr, 5, 1.0, 3.5, -1.5);

        for (int i = 0; i < generatorThreadsCount; i++) {
            threads[i] = new Thread(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                long counter = 0;
                while (inProgress) {
                    collector.push(String.valueOf(counter++), random.doubles(10).toArray());
                }
            });
            threads[i].start();
        }
        inProgress = true;
        collector.start(4);

        Thread.sleep(750);
        collector.push(testInStr, testArr);
        Thread.sleep(750);

        inProgress = false;
        for (int i = 0; i < generatorThreadsCount; i++) {
            threads[i].join();
        }
        Thread.sleep(500);
        collector.stop();

        assertThat(collector.stats())
            .contains(expMetric);
    }

    @Test
    void NullPushTest() {
        StatsCollector collector = new StatsCollector();
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> collector.push("test", null));
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> collector.push(null, new double[] {1.0, 2.0}));
    }
}
