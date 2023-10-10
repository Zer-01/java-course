package edu.hw1;

import java.util.Arrays;

public class Task6 {
    private Task6() {
    }

    private static final int STANDARD_NUMB_LENGTH = 4;
    private static final int BASE = 10;
    private static final int KAPREKAR_CONST = 6174;
    private static final int MIN_INPUT_NUMBER = 1001;
    private static final int MAX_INPUT_NUMBER = 9999;

    public static int countK(int num) {
        int[] arr = intToArr(num);
        boolean elmsIsEqual = Arrays.stream(arr).allMatch(s -> s == arr[0]);
        if (num < MIN_INPUT_NUMBER || num > MAX_INPUT_NUMBER || elmsIsEqual) {
            throw new IllegalArgumentException();
        }
        return countKaprekar(num);
    }

    private static int countKaprekar(int num) {
        if (num == KAPREKAR_CONST) {
            return 0;
        }
        int[] numArr = intToArr(num);
        Arrays.sort(numArr);

        int min = arrToInt(numArr, false);
        int max = arrToInt(numArr, true);
        return countKaprekar(max - min) + 1;
    }

    private static int[] intToArr(int num) {
        int[] res = new int[STANDARD_NUMB_LENGTH];
        int number = num;
        for (int i = STANDARD_NUMB_LENGTH - 1; i >= 0; i--) {
            res[i] = number % BASE;
            number /= BASE;
        }
        return res;
    }

    private static int arrToInt(int[] arr, boolean reversed) {
        int res = 0;
        int mult = 1;
        if (!reversed) {
            for (int i = STANDARD_NUMB_LENGTH - 1; i >= 0; i--) {
                res += arr[i] * mult;
                mult *= BASE;
            }
        } else {
            for (int i = 0; i < STANDARD_NUMB_LENGTH; i++) {
                res += arr[i] * mult;
                mult *= BASE;
            }
        }
        return res;
    }
}
