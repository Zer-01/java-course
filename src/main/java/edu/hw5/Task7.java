package edu.hw5;

public class Task7 {
    private static final String PATTERN1 = "[01]{2}0[01]*";
    private static final String PATTERN2 = "([01]).*\\1$";
    private static final String PATTERN3 = "[10]{1,3}";

    private Task7() {
    }

    public static boolean atLeast3CharThird0(String string) {
        return string.matches(PATTERN1);
    }

    public static boolean equalsStartEnd(String string) {
        return string.matches(PATTERN2);
    }

    public static boolean length1To3(String string) {
        return string.matches(PATTERN3);
    }
}
