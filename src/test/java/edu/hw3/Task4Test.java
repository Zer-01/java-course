package edu.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task4Test {
    static Arguments[] numbers() {
        return new Arguments[]{
            Arguments.of(2, "II"),
            Arguments.of(16, "XVI"),
            Arguments.of(1456, "MCDLVI"),
            Arguments.of(3999, "MMMCMXCIX")
        };
    }

    @ParameterizedTest
    @MethodSource("numbers")
    void normalTest(int in, String out) {
        String result = Task4.convertRoman(in);

        assertThat(result)
            .isEqualTo(out);
    }

    @Test
    void invalidRangeInput() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                Task4.convertRoman(0);
            });

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                Task4.convertRoman(4000);
            });
    }
}
