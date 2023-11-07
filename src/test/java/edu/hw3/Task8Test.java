package edu.hw3;

import edu.hw3.Task8.BackwardIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task8Test {
    @Test
    void itTest() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        Iterator<Integer> it = new BackwardIterator<>(list);
        List<Integer> result = new ArrayList<>();

        while (it.hasNext()) {
            result.add(it.next());
        }

        assertThat(result)
            .isEqualTo(List.of(5, 4, 3, 2, 1));
    }
}
