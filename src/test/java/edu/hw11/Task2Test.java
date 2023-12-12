package edu.hw11;

import edu.hw11.Task2.ArithmeticUtils;
import edu.hw11.Task2.DelegationClass;
import edu.hw11.Task2.Task;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task2Test {
    @Test
    void changeMethod() {
        int var1 = 2;
        int var2 = 3;
        int expResult1 = 5;
        int expResult2 = 6;

        int result1 = ArithmeticUtils.sum(var1, var2);

        Task.redefine(ArithmeticUtils.class, "sum", DelegationClass.class);

        int result2 = ArithmeticUtils.sum(var1, var2);

        assertThat(result1)
            .isEqualTo(expResult1);
        assertThat(result2)
            .isEqualTo(expResult2);
    }

    @Test
    void nullInput() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Task.redefine(null, null, null));
    }
}
