package edu.hw1;

public class Task4 {
    private Task4() {
    }

    public static String fixString(String origString) {
        if (origString == null) {
            throw new NullPointerException();
        }

        char[] charArr = origString.toCharArray();
        char temp;
        for (int i = 0; i < charArr.length - 1; i += 2) {
            temp = charArr[i];
            charArr[i] = charArr[i + 1];
            charArr[i + 1] = temp;
        }
        return new String(charArr);
    }
}
