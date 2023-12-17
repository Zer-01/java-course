package edu.hw8.Task3;

import java.util.Arrays;

public class PasswordGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final char[] chars;
    private final int maxSize;
    private int passwordsRemain;

    public PasswordGenerator(int maxSize) {
        if (maxSize < 1) {
            throw new IllegalArgumentException();
        }
        this.maxSize = maxSize;
        this.chars = new char[maxSize];
        Arrays.fill(chars, ' ');
        this.chars[maxSize - 1] = CHARACTERS.charAt(0);
        passwordsRemain = 0;
        for (int i = 1; i <= maxSize; i++) {
            passwordsRemain += (int) Math.pow(CHARACTERS.length(), i);
        }
    }

    //работа с паролем похожа на работу с числом с основанием CHARACTERS.length()
    public synchronized String nextPassword() {
        String result = new String(chars).trim();
        passwordsRemain--;
        int index = maxSize - 1;
        while (index >= 0) {
            if (chars[index] == CHARACTERS.charAt(CHARACTERS.length() - 1)) {
                chars[index] = CHARACTERS.charAt(0);
            } else {
                chars[index] = CHARACTERS.charAt(CHARACTERS.indexOf(chars[index]) + 1);
                break;
            }
            index--;
        }
        return result;
    }

    public void skip(int count) {
        int index = maxSize - 1;
        int remainSum = count;
        while (index >= 0 && remainSum > 0) {
            int newVal =
                ((chars[index] == ' ') ? -1 : CHARACTERS.indexOf(chars[index])) + remainSum % CHARACTERS.length();
            if (newVal == -1) {
                newVal = CHARACTERS.length() - 1;
                remainSum--;
            }
            if (newVal < CHARACTERS.length()) {
                chars[index] = CHARACTERS.charAt(newVal);
                remainSum /= CHARACTERS.length();
            } else {
                newVal -= CHARACTERS.length();
                chars[index] = CHARACTERS.charAt(newVal);
                remainSum /= CHARACTERS.length();
                remainSum += 1;
            }
            index--;
        }
        passwordsRemain -= count;
    }

    public void bound(int partNum, int partsCount) {
        int passwordsInPart = passwordsRemain / partsCount;
        skip(passwordsInPart * partNum);
        passwordsRemain = passwordsInPart + 2;
    }

    public boolean hasNext() {
        return passwordsRemain > 0;
    }
}
