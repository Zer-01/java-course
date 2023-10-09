package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class Task5Test {
    @Test
    void twoDigitTest() {
        int number = 11;

        boolean result = Task5.isPalindromeDescendant(number);

        assertThat(result)
            .isEqualTo(true);
    }

    @Test
    void manyDigitTest1() {
        int number = 11210330;//11210330->2333->56->11->true

        boolean result = Task5.isPalindromeDescendant(number);

        assertThat(result)
            .isEqualTo(true);
    }

    @Test
    void manyDigitTest2() {
        int number = 132622;//132622->484->true

        boolean result = Task5.isPalindromeDescendant(number);

        assertThat(result)
            .isEqualTo(true);
    }

    @Test
    void manyDigitTest3() {
        int number = 13262332;//13262332->4855->1210->31->4->false

        boolean result = Task5.isPalindromeDescendant(number);

        assertThat(result)
            .isEqualTo(false);
    }
}
