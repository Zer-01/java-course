package edu.hw5;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task8Test {
    @Test
    void oddLengthTrueTest() {
        String input = "101";

        boolean result = Task8.oddLength(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void oddLengthFalseTest() {
        String input = "1010";

        boolean result = Task8.oddLength(input);

        assertThat(result)
            .isFalse();
    }

    @Test
    void start0AndOddOr1AndEvenTrueTest1() {
        String input = "010";

        boolean result = Task8.start0AndOddOr1AndEven(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void start0AndOddOr1AndEvenTrueTest2() {
        String input = "1101";

        boolean result = Task8.start0AndOddOr1AndEven(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void start0AndOddOr1AndEvenFalseTest1() {
        String input = "0101";

        boolean result = Task8.start0AndOddOr1AndEven(input);

        assertThat(result)
            .isFalse();
    }

    @Test
    void start0AndOddOr1AndEvenFalseTest2() {
        String input = "110";

        boolean result = Task8.start0AndOddOr1AndEven(input);

        assertThat(result)
            .isFalse();
    }

    @Test
    void quantity0MultipleOf3TrueTest() {
        String input = "101001100101";

        boolean result = Task8.quantity0MultipleOf3(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void quantity0MultipleOf3FalseTest() {
        String input = "1010011001010";

        boolean result = Task8.quantity0MultipleOf3(input);

        assertThat(result)
            .isFalse();
    }

    @Test
    void not11Or111TrueTest() {
        String input = "010111";

        boolean result = Task8.not11Or111(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void not11Or111FalseTest() {
        String input = "111";

        boolean result = Task8.not11Or111(input);

        assertThat(result)
            .isFalse();
    }

    @Test
    void everyOddIs1TrueTest() {
        String input = "0111";

        boolean result = Task8.everyOddIs1(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void everyOddIs1FalseTest() {
        String input = "010110";

        boolean result = Task8.everyOddIs1(input);

        assertThat(result)
            .isFalse();
    }

    @Test
    void atLeastTwo0AndNotMoreOne1TrueTest() {
        String input = "00100";

        boolean result = Task8.atLeastTwo0AndNotMoreOne1(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void atLeastTwo0AndNotMoreOne1FalseTest() {
        String input = "001001";

        boolean result = Task8.atLeastTwo0AndNotMoreOne1(input);

        assertThat(result)
            .isFalse();
    }

    @Test
    void noSeries1TrueTest() {
        String input = "00100101";

        boolean result = Task8.noSeries1(input);

        assertThat(result)
            .isTrue();
    }

    @Test
    void noSeries1FalseTest() {
        String input = "001001101";

        boolean result = Task8.noSeries1(input);

        assertThat(result)
            .isFalse();
    }
}
