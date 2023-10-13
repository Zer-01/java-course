package edu.hw1;

import java.util.Arrays;

public class Task3 {
    private Task3() {
    }

    public static boolean isNestable(int[] arr1, int[] arr2) {
        if (arr1 == null || arr2 == null) {
            throw new NullPointerException();
        }

        if (arr1.length == 0) {
            return true;
        } else if (arr2.length == 0) {
            return false;
        }

        int min1 = Arrays.stream(arr1).min().getAsInt();
        int max1 = Arrays.stream(arr1).max().getAsInt();
        int min2 = Arrays.stream(arr2).min().getAsInt();
        int max2 = Arrays.stream(arr2).max().getAsInt();

        return min1 > min2 && max1 < max2;
    }
}
