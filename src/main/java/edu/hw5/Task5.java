package edu.hw5;

public class Task5 {
    public static final String PATTERN = "[АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{2}\\d{2,3}";

    private Task5() {
    }

    public static boolean validateNumber(String password) {
        return password.matches(PATTERN);
    }
}
