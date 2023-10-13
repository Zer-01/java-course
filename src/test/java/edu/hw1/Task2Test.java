package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @Test
    void testPositive() {
        int num1 = 56087;
        int num2 = 2;
        int result1;
        int result2;

        result1 = Task2.countDigits(num1);
        result2 = Task2.countDigits(num2);

        assertThat(result1)
            .isEqualTo(5);
        assertThat(result2)
            .isEqualTo(1);
    }

    @Test
    void testNegative() {
        int num1 = -357644;
        int num2 = -2;
        int result1;
        int result2;

        result1 = Task2.countDigits(num1);
        result2 = Task2.countDigits(num2);

        assertThat(result1)
            .isEqualTo(6);
        assertThat(result2)
            .isEqualTo(1);
    }

    @Test
    void testZero() {
        int num = 0;
        int result;

        result = Task2.countDigits(num);

        assertThat(result)
            .isEqualTo(1);
    }
}
