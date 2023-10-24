package edu.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task2Test {

    static Arguments[] clusters() {
        return new Arguments[]{
            Arguments.of("()()()", Arrays.asList("()", "()", "()")),
            Arguments.of("((()))", Arrays.asList("((()))")),
            Arguments.of("((()))(())()()(()())", Arrays.asList("((()))", "(())", "()", "()", "(()())")),
            Arguments.of("((())())(()(()()))", Arrays.asList("((())())", "(()(()()))"))
        };
    }

    @ParameterizedTest
    @MethodSource("clusters")
    void normalTest(String in, List<String> out) {
        List<String> result = Task2.clusterize(in);

        assertThat(result)
            .isEqualTo(out);
    }



    @ParameterizedTest
    @ValueSource(strings = {"()()(", "())"}) //несбалансированные наборы
    @EmptySource //пустая строка
    void IllegalAE(String input) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(()->{
                Task2.clusterize(input);
            });
    }

    @Test
    void nullInput() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(()->{
                Task2.clusterize(null);
            });
    }
}
