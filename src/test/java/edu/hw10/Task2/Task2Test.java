package edu.hw10.Task2;

import edu.hw10.Task2.classesForClasses.FibCalculator;
import edu.hw10.Task2.classesForClasses.TestCalculator;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @TempDir Path tempDir;

    @Test
    void noFileTest() {
        Path testPath = tempDir.resolve("tmp.chc");
        FibCalculator calculator = new TestCalculator();
        FibCalculator proxy = CacheProxy.create(calculator, calculator.getClass(), testPath);

        long expResult = 3;
        long result = proxy.fibNoFile(4);

        assertThat(result)
            .isEqualTo(expResult);
    }

    @Test
    void withFileTest() {
        Path testPath = tempDir.resolve("tmp.chc");
        FibCalculator calculator = new TestCalculator();
        FibCalculator proxy = CacheProxy.create(calculator, calculator.getClass(), testPath);

        long expResult = 3;

        long result1 = proxy.fib(4);

        FibCalculator proxy2 = CacheProxy.create(calculator, calculator.getClass(), testPath);

        long result2 = proxy2.fib(4);

        assertThat(result1)
            .isEqualTo(expResult);
        assertThat(result2)
            .isEqualTo(expResult);
        assertThat(testPath)
            .isNotEmptyFile();
    }
}
