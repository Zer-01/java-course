package edu.hw5;

public class Task8 {
    private static final String PATTERN1 = "[01]([01]{2})*";
    private static final String PATTERN2 = "(0|1[01])([01]{2})*";
    private static final String PATTERN3 = "(1*01*01*01*)+";
    private static final String PATTERN4 = "(?!11$|111$)[01]*";
    private static final String PATTERN5 = "[01](1[01])*1?";
    private static final String PATTERN6 = "(0*1)?0*";
    private static final String PATTERN7 = "(?![01]*11)[01]*";

    private Task8() {
    }

    public static boolean oddLength(String string) {
        return string.matches(PATTERN1);
    }

    public static boolean start0AndOddOr1AndEven(String string) {
        return string.matches(PATTERN2);
    }

    public static boolean quantity0MultipleOf3(String string) {
        return string.matches(PATTERN3);
    }

    public static boolean not11Or111(String string) {
        return string.matches(PATTERN4);
    }

    public static boolean everyOddIs1(String string) {
        return string.matches(PATTERN5);
    }

    public static boolean atLeastTwo0AndNotMoreOne1(String string) {
        return string.matches(PATTERN6);
    }

    public static boolean noSeries1(String string) {
        return string.matches(PATTERN7);
    }
}
