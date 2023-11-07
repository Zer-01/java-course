package edu.hw3;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task3Test {
    @Test
    void stringsInputTest() {
        List<String> list = Arrays.asList("this", "and", "that", "and");
        Map<String, Integer> outMap = new HashMap<>();
        outMap.put("that", 1);
        outMap.put("and", 2);
        outMap.put("this", 1);

        Map<String, Integer> map = Task3.freqDict(list);

        assertThat(map)
            .containsExactlyEntriesOf(outMap);
    }

    @Test
    void intsInputTest() {
        List<Integer> list = Arrays.asList(1, 1, 2, 3, 4, 2, 1);
        Map<Integer, Integer> outMap = new HashMap<>();
        outMap.put(1, 3);
        outMap.put(2, 2);
        outMap.put(3, 1);
        outMap.put(4, 1);

        Map<Integer, Integer> map = Task3.freqDict(list);

        assertThat(map)
            .containsExactlyEntriesOf(outMap);
    }

    @Test
    void nullInput() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> {
                Task3.freqDict(null);
            });
    }

    @Test
    void emptyInput() {
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> outMap = new HashMap<>();

        Map<Integer, Integer> map = Task3.freqDict(list);

        assertThat(map)
            .containsExactlyEntriesOf(map);
    }
}
