package edu.hw1;

public class Task1 {
    private Task1() {
    }

    private static final int SECONDS_IN_MINUTE = 60;

    public static int videoLengthToSeconds(String input) {
        String[] timeStr = input.split(":");
        if (timeStr.length != 2) {
            return -1;
        }
        int mins;
        int seconds;
        try {
            mins = Integer.parseInt(timeStr[0]);
            seconds = Integer.parseInt(timeStr[1]);
            if (seconds >= SECONDS_IN_MINUTE || seconds < 0 || mins < 0) {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            return -1;
        }
        return mins * SECONDS_IN_MINUTE + seconds;
    }
}
