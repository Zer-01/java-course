package edu.hw3;

import edu.hw3.Task7.NullTreeComparator;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;

public class Task7Test {
    @Test
    void compTest() {
        TreeMap<String, String> tree = new TreeMap<>(new NullTreeComparator());

        tree.put(null, "test");
        tree.put("notNull", "test2");

        assertThat(tree.containsKey(null))
            .isTrue();
    }
}
