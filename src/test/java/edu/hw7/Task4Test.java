package edu.hw7;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @Test
    void singleThreadTest() {
        long pointsCount1 = 10_000;
        long pointsCount2 = 100_000_000;

        double result1 = PiCalculate.singleThreadCalculate(pointsCount1);
        double result2 = PiCalculate.singleThreadCalculate(pointsCount2);

        assertThat(Math.abs(Math.PI - result1))
            .isLessThan(1);//погрешность меньше единицы
        assertThat(Math.abs(Math.PI - result1))
            .isGreaterThan(Math.abs(Math.PI - result2));//погрешность падает при увеличении кол-ва точек
    }

    @Test
    void multiThreadTest() {
        long pointsCount1 = 10_000;
        long pointsCount2 = 100_000_000;

        double result1 = PiCalculate.multiThreadCalculate(pointsCount1, 8);
        double result2 = PiCalculate.multiThreadCalculate(pointsCount2, 8);

        assertThat(Math.abs(Math.PI - result1))
            .isLessThan(1);
        assertThat(Math.abs(Math.PI - result1))
            .isGreaterThan(Math.abs(Math.PI - result2));
    }
}
