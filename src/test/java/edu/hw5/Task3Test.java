package edu.hw5;

import edu.hw5.Task3.DateParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task3Test {
    static Arguments[] dates() {
        return new Arguments[] {
            Arguments.of("2020-10-10", LocalDate.of(2020, 10, 10)),
            Arguments.of("2020-12-2", LocalDate.of(2020, 12, 2)),
            Arguments.of("1/3/1976", LocalDate.of(1976, 3, 1)),
            Arguments.of("1/3/20", LocalDate.of(2020, 3, 1)),
            Arguments.of("tomorrow", LocalDate.now().plusDays(1)),
            Arguments.of("yesterday", LocalDate.now().minusDays(1)),
            Arguments.of("today", LocalDate.now()),
            Arguments.of("1 day ago", LocalDate.now().minusDays(1)),
            Arguments.of("2234 day ago", LocalDate.now().minusDays(2234))
        };
    }

    @ParameterizedTest
    @MethodSource("dates")
    void normalTest(String input, LocalDate expOut) {
        Optional<LocalDate> optOut = DateParser.parseDate(input);
        LocalDate out = optOut.orElse(null);

        assertThat(out)
            .isEqualTo(expOut);
    }

    @Test
    void illegalInputTest() {
        Optional<LocalDate> out = DateParser.parseDate("randomWord");

        assertThat(out.orElse(null))
            .isNull();
    }

    @Test
    void nullInputTest() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(()->{
                DateParser.parseDate(null);
            });
    }
}
