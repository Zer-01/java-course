package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task8Test {
    @Test
    void normalTest1() {
        int[][] input = {{0, 0, 0, 1, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0, 0},
                         {0, 1, 0, 0, 0, 1, 0, 0},
                         {0, 0, 0, 0, 1, 0, 1, 0},
                         {0, 1, 0, 0, 0, 1, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0, 0},
                         {0, 1, 0, 0, 0, 0, 0, 1},
                         {0, 0, 0, 0, 1, 0, 0, 0}};

        boolean result = Task8.knightBoardCapture(input);

        assertThat(result)
            .isEqualTo(true);
    }

    @Test
    void normalTest2() { //     \/
        int[][] input = {{0, 0, 1, 1, 0, 0, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0, 0},
                         {0, 1, 0, 0, 0, 1, 0, 0},
                         {0, 0, 0, 0, 1, 0, 1, 0},
                         {0, 1, 0, 0, 0, 1, 0, 0},
                         {0, 0, 0, 0, 0, 0, 0, 0},
                         {0, 1, 0, 0, 0, 0, 0, 1},
                         {0, 0, 0, 0, 1, 0, 0, 0}};

        boolean result = Task8.knightBoardCapture(input);

        assertThat(result)
            .isEqualTo(false);
    }

    @Test
    void nullPtrTest() {
        int[][] input = null;

        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> {
                Task8.knightBoardCapture(input);
            });
    }
}
