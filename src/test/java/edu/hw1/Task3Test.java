package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task3Test {
    @Test
    void normalTest() {
        int[] arr1 = new int[] {2, 6, 9, 15};
        int[] arr2 = new int[] {-5, 21};

        boolean result = Task3.isNestable(arr1, arr2);

        assertThat(result)
            .isEqualTo(true);
    }

    @Test
    void normalTest2() {
        int[] arr1 = new int[] {1, 2, 3, 5};
        int[] arr2 = new int[] {3, 6};

        boolean result = Task3.isNestable(arr1, arr2);

        assertThat(result)
            .isEqualTo(false);
    }

    @Test
    void emptyArrs() {
        int[] arr1 = new int[] {};
        int[] arr2 = new int[] {3, 6};

        boolean result1 = Task3.isNestable(arr1, arr2);
        boolean result2 = Task3.isNestable(arr2, arr1);

        assertThat(result1)
            .isEqualTo(true);
        assertThat(result2)
            .isEqualTo(false);
    }

    @Test
    void nullPointerTest() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(()->{
                Task3.isNestable(null, null);
            });
    }
}
