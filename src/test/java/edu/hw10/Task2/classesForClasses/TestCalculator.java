package edu.hw10.Task2.classesForClasses;

public class TestCalculator implements FibCalculator {
    @Override
    public long fib(int number) {
        return calc(number);
    }

    @Override
    public long fibNoFile(int number) {
        return calc(number);
    }

    private long calc(int num) {
        if (num == 1 || num == 0) {
            return num;
        }

        return calc(num - 1) + calc(num - 2);
    }
}
