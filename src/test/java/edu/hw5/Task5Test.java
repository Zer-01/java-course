package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task5Test {
    static Arguments[] validNumbers() {
        return new Arguments[] {
            Arguments.of("А123ВЕ777"),
            Arguments.of("О777ОО177"),
            Arguments.of("В746УУ777")
        };
    }

    @ParameterizedTest
    @MethodSource("validNumbers")
    void validNumberTest(String number) {
        boolean result = Task5.validateNumber(number);

        assertThat(result)
            .isTrue();
    }

    static Arguments[] invalidNumbers() {
        return new Arguments[] {
            Arguments.of("123АВЕ777"),
            Arguments.of("А123ВГ77"),
            Arguments.of("А123ВЕ7777")
        };
    }

    @ParameterizedTest
    @MethodSource("invalidNumbers")
    void invalidNumberTest(String number) {
        boolean result = Task5.validateNumber(number);

        assertThat(result)
            .isFalse();
    }

    @Test
    void nullInput() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> {
                Task5.validateNumber(null);
            });
    }
}
