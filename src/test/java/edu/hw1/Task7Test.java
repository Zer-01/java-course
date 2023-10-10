package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class Task7Test {
    @Test
    void normalTest() {
        int num = 53; //110101

        int resultLeft = Task7.rotateLeft(num, 2);
        int resultRight = Task7.rotateRight(num, 2);

        assertThat(resultLeft)
            .isEqualTo(23); //010111
        assertThat(resultRight)
            .isEqualTo(29); //011101
    }

    @Test
    void negativeShift() {
        int num = 53;

        int resultLeft = Task7.rotateLeft(num, -2);
        int resultRight = Task7.rotateRight(num, -2);

        assertThat(resultLeft)
            .isEqualTo(29);
        assertThat(resultRight)
            .isEqualTo(23);
    }

    @Test
    void shiftZero() {
        int num = 0;

        int result = Task7.rotateLeft(num, 2);

        assertThat(result)
            .isEqualTo(0);
    }
}
