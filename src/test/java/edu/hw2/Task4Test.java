package edu.hw2;

import static edu.hw2.Task4.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    @Test
    void normalTest() {
        CallingInfo result;

        result = callingInfo();

        assertThat(result)
            .isEqualTo(new CallingInfo("edu.hw2.Task4Test", "normalTest"));
    }
}
