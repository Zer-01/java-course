package edu.hw5;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task2Test {
    @Test
    void normalTest1() {
        int input = 1925;
        List<LocalDate> expResult = new ArrayList<>();
        expResult.add(LocalDate.of(1925, 2, 13));
        expResult.add(LocalDate.of(1925, 3, 13));
        expResult.add(LocalDate.of(1925, 11, 13));

        var result = Task2.getFridays13(input);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void illegalArgumentTest() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                Task2.getFridays13(0);
            });
    }

    @Test
    public void nextFriday13Test() {
        LocalDate in = LocalDate.of(2024, 9, 13);
        LocalDate expResult = LocalDate.of(2024, 12, 13);

        var result = Task2.nextFriday13(in);

        assertThat(result)
            .isEqualTo(expResult);
    }
}
