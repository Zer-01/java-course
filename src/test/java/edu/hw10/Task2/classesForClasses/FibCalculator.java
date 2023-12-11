package edu.hw10.Task2.classesForClasses;

import edu.hw10.Task2.Cache;

public interface FibCalculator {
    @Cache()
    long fib(int number);

    @Cache(persist = true)
    long fibNoFile(int number);
}
