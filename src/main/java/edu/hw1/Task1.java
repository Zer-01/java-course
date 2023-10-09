package edu.hw1;

public class Task1 {
    private Task1() {
    }

    @SuppressWarnings("MagicNumber")
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
            if (seconds >= 60 || seconds < 0 || mins < 0) {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            return -1;
        }
        //if(seconds >= 60)
        return mins * 60 + seconds;
    }
}
