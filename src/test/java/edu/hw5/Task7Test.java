package edu.hw5;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.stream;

public class Task7Test {
    @Test
    void atLeast3CharThird0TrueTest() {
        String input = "11010";

        boolean result = Task7.atLeast3CharThird0(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void atLeast3CharThird0FalseTest1() {
        String input = "11110";

        boolean result = Task7.atLeast3CharThird0(input);

        assertThat(result)
            .isFalse();
    }

    @Test
    void atLeast3CharThird0FalseTest2() {
        String input = "11";

        boolean result = Task7.atLeast3CharThird0(input);

        assertThat(result)
            .isFalse();
    }

    @Test
    void equalsStartEndTrueTest() {
        String input = "101010011";

        boolean result = Task7.equalsStartEnd(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void equalsStartEndFalseTest() {
        String input = "1010100110";

        boolean result = Task7.equalsStartEnd(input);

        assertThat(result)
            .isFalse();
    }

    @Test
    void length1To3TrueTest1() {
        String input = "101";

        boolean result = Task7.length1To3(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void length1To3TrueTest2() {
        String input = "1";

        boolean result = Task7.length1To3(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void length1To3FalseTest() {
        String input = "110101";

        boolean result = Task7.length1To3(input);

        assertThat(result)
            .isFalse();
    }
}
