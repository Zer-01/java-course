package edu.hw7;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task2Test {
    static Arguments[] factorials() {
        return new Arguments[] {
            Arguments.of(0, 1),
            Arguments.of(1, 1),
            Arguments.of(5, 120),
            Arguments.of(10, 3628800)
        };
    }

    @ParameterizedTest
    @MethodSource("factorials")
    void normalTest(long num, long expResult) {
        long result = Task2.parallelFactorial(num);

        assertThat(result)
            .isEqualTo(expResult);
    }

    @Test
    void illegalArgTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                Task2.parallelFactorial(-1);
            });
    }
}
