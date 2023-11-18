package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task1Test {
    @Test
    void normalTest() {
        String[] input = {
            "2022-03-12, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-02, 01:20" };

        String out = Task1.avgSessionDuration(input);

        assertThat(out)
            .isEqualTo("3ч 40м");
    }

    static Arguments[] durations() {
        return new Arguments[] {
            Arguments.of((Object) new String[] {
                "2022-03-122, 20:20 - 2022-03-12, 23:50",
                "2022-04-01, 21:30 - 2022-04-02, 01:20" }),
            Arguments.of((Object) new String[] {
                "2022-03-12, 20:20",
                "2022-04-01, 21:30 - 2022-04-02, 01:20" }),
        };
    }

    @ParameterizedTest
    @MethodSource("durations")
    void illegalAETest(String[] input){
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(()->{
                Task1.avgSessionDuration(input);
            });
    }

    @Test
    void nullPETest() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(()->{
                Task1.avgSessionDuration(null);
            });
    }
}
