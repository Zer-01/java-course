package edu.hw1;

public class Task5 {
    private Task5() {
    }

    private static final int MIN_TWO_DIGIT_POSITIVE_NUM = 10;
    private static final int BASE = MIN_TWO_DIGIT_POSITIVE_NUM;

    public static boolean isPalindromeDescendant(int number) {

        int[] numberIntArr = Integer.toString(number).chars().map(c -> c - '0').toArray();
        if (number < MIN_TWO_DIGIT_POSITIVE_NUM || numberIntArr.length % 2 == 1) {
            return false;
        }

        int size = numberIntArr.length;
        while (size > 1 && size % 2 == 0 && !isPalindrome(numberIntArr, size)) {
            size = makeDescendant(numberIntArr, size);
        }
        return isPalindrome(numberIntArr, size);
    }

    private static boolean isPalindrome(int[] num, int size) {
        if (size < 2) {
            return false;
        }
        int left = 0;
        int right = size - 1;
        while (num[left] == num[right] && left <= right) {
            left++;
            right--;
        }
        return left > right;
    }

    private static int makeDescendant(int[] num, int size) {
        int tmp;
        int newSize = 0;
        for (int i = 0; i < size / 2; i++) {
            tmp = num[i * 2] + num[i * 2 + 1];
            if (tmp < MIN_TWO_DIGIT_POSITIVE_NUM) {
                num[i] = tmp;
                newSize += 1;
            } else {
                num[i] = tmp / BASE;
                num[i + 1] = tmp % BASE;
                newSize += 2;
            }
        }
        return newSize;
    }
}
