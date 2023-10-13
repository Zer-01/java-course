package edu.hw1;

public class Task2 {
    private Task2() {

    }

    public static int countDigits(int num) {
        if (num == 0) {
            return 1;
        }
        int absNum = Math.abs(num);
        return (int) Math.log10(absNum) + 1;
    }
}
