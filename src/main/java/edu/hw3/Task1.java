package edu.hw3;

public class Task1 {
    private Task1() {

    }

    public static String atbash(String input) {
        StringBuilder result = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char origChar = input.charAt(i);
            char modifiedChar = origChar;

            if (Character.toLowerCase(origChar) >= 'a' && Character.toLowerCase(origChar) <= 'z') {
                char offset = Character.isUpperCase(origChar) ? 'A' : 'a';
                modifiedChar = (char) (offset + 'z' - Character.toLowerCase(modifiedChar));
            }
            result.append(modifiedChar);
        }
        return result.toString();
    }
}
