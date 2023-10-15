package edu.hw2;

import static edu.hw2.Task1.Expr.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class Task1Test {
    @Test
    void evalTest() {
        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1)); //-1
        var sumTwoFour = new Addition(two, four); //2 + 4 = 6
        var mult = new Multiplication(sumTwoFour, negOne); // 6 * -1 = -6
        var exp = new Exponent(mult, 2); // -6 ^ 2 = 36
        var res = new Addition(exp, new Constant(1)); // 36 + 1 = 37

        assertThat(res.evaluate())
            .isEqualTo(37.0);
    }
}
