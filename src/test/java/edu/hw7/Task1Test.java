package edu.hw7;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    void oneThreadTest() {
        int incrementCount = 1_000;

        long result = Task1.multiThreadIncrement(1, incrementCount);

        assertThat(result)
            .isEqualTo(incrementCount);
    }

    @Test
    void multiThreadTest() {
        int threadsCount = 4;
        long incrementCount = 1_000_000;

        long result = Task1.multiThreadIncrement(threadsCount, incrementCount);

        assertThat(result)
            .isEqualTo(threadsCount * incrementCount);
    }
}
