package edu.hw1;

public class Task7 {
    private Task7() {
    }

    private static final int INT_SIZE = 31; //without sign bit

    public static int rotateLeft(int n, int shift) {
        if (shift < 0) {
            return rotateRight(n, Math.abs(shift));
        }
        if (n == 0) {
            return 0;
        }
        int highestBitPos = getHighestBitPos(n);
        int minShift = shift % highestBitPos;
        return (n << minShift)
            & (Integer.MAX_VALUE >> (INT_SIZE - highestBitPos))
            | (n >> (highestBitPos - minShift));
    }

    public static int rotateRight(int n, int shift) {
        if (shift < 0) {
            return rotateLeft(n, Math.abs(shift));
        }
        if (n == 0) {
            return 0;
        }
        int highestBitPos = getHighestBitPos(n);
        int minShift = shift % highestBitPos;
        return (n >> minShift)
            | (n << (highestBitPos - minShift))
            & (Integer.MAX_VALUE >> (INT_SIZE - highestBitPos));
    }

    private static int getHighestBitPos(int num) {
        int oneBit = Integer.highestOneBit(num);
        int pos = 1;
        while (oneBit != 1) {
            oneBit >>= 1;
            pos++;
        }
        return pos;
    }
}
