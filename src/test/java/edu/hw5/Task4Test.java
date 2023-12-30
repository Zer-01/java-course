package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task4Test {
    static Arguments[] passwords() {
        return new Arguments[] {
            Arguments.of("fdvgbg!dfg"),
            Arguments.of("@"),
            Arguments.of("12#"),
            Arguments.of("!@$%")
        };
    }

    @ParameterizedTest
    @MethodSource("passwords")
    void validPasswordTest(String password) {
        boolean result = Task4.validatePass(password);

        assertThat(result)
            .isTrue();
    }

    static Arguments[] invalidPasswords() {
        return new Arguments[] {
            Arguments.of("fdvgbgfdfg"),
            Arguments.of("asd123"),
            Arguments.of(""),
            Arguments.of("   "),
            Arguments.of("1234")
        };
    }

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    void invalidPasswordTest(String password) {
        boolean result = Task4.validatePass(password);

        assertThat(result)
            .isFalse();
    }

    @Test
    void nullInput() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> {
                Task4.validatePass(null);
            });
    }
}
