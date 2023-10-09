package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task4Test {
    @Test
    void normalTest() {
        String str = "hTsii  s aimex dpus rtni.g21";

        String newStr = Task4.fixString(str);

        assertThat(newStr)
            .isEqualTo("This is a mixed up string.12");
    }

    @Test
    void emptyStringTest() {
        String str = "";

        String newStr = Task4.fixString(str);

        assertThat(newStr)
            .isEqualTo("");
    }

    @Test
    void nullPointerTest() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(()->{
                Task4.fixString(null);
            });
    }
}
