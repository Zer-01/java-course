package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task6Test {
    static Arguments[] subSequences() {
        return new Arguments[] {
            Arguments.of("abc", "achfdbaabgabcaabg"),
            Arguments.of("abc", "abcabcabc"),
            Arguments.of("", "abc"),
            Arguments.of("abc", "afbfc")
        };
    }

    @ParameterizedTest
    @MethodSource("subSequences")
    void substringTest(String s, String t) {
        boolean result = Task6.isSubSequence(s, t);

        assertThat(result)
            .isTrue();
    }

    static Arguments[] notSubSequences() {
        return new Arguments[] {
            Arguments.of("abc", "achfdbaabgaabg"),
            Arguments.of("a", "")
        };
    }

    @ParameterizedTest
    @MethodSource("notSubSequences")
    void notSubstringTest(String s, String t) {
        boolean result = Task6.isSubSequence(s, t);

        assertThat(result)
            .isFalse();
    }

    @Test
    void nullInputTest() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(()->{
                Task6.isSubSequence(null, "aa");
            });

        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(()->{
                Task6.isSubSequence("aa", null);
            });
    }
}
