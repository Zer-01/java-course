package edu.hw7;

import java.util.stream.LongStream;

public class Task2 {
    private Task2() {
    }

    public static long parallelFactorial(long number) {
        if (number < 0) {
            throw new IllegalArgumentException();
        }
        if (number == 0) {
            return 1;
        }
        return LongStream.rangeClosed(1, number)
            .parallel()
            .reduce((x, y) -> x * y)
            .orElse(-1);
    }
}
