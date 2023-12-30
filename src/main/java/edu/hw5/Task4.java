package edu.hw5;

public class Task4 {
    public static final String PATTERN = ".*[~!@#$%^&*|]+.*";

    private Task4() {
    }

    public static boolean validatePass(String password) {
        return password.matches(PATTERN);
    }
}
