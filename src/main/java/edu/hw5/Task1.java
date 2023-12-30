package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task1 {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd, HH:mm");

    private Task1() {

    }

    public static String avgSessionDuration(String[] strings) {
        Duration sumOfDur = Duration.ZERO;

        for (String str : strings) {
            String[] times = str.split(" - ");
            if (times.length != 2) {
                throw new IllegalArgumentException();
            }
            Duration tmp;
            try {
                LocalDateTime beg = LocalDateTime.parse(times[0], TIME_FORMATTER);
                LocalDateTime end = LocalDateTime.parse(times[1], TIME_FORMATTER);
                tmp = Duration.between(beg, end);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(e);
            }
            sumOfDur = sumOfDur.plus(tmp);
        }
        sumOfDur = sumOfDur.dividedBy(strings.length);

        return String.format("%dч %dм", sumOfDur.toHoursPart(), sumOfDur.toMinutesPart());
    }
}
