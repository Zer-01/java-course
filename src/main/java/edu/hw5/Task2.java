package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Task2 {
    private static final int MONTH_IN_YEAR = 12;
    private static final int DESIRED_FRIDAY = 13;

    private Task2() {

    }

    public static List<LocalDate> getFridays13(int year) {
        if (year < 1) {
            throw new IllegalArgumentException();
        }
        List<LocalDate> result = new ArrayList<>();
        LocalDate date = LocalDate.of(year, Month.JANUARY, DESIRED_FRIDAY);
        for (int month = 0; month < MONTH_IN_YEAR; month++) {
            if (date.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                result.add(date);
            }
            date = date.plusMonths(1);
        }
        return result;
    }

    public static LocalDate nextFriday13(LocalDate date) {
        LocalDate nextDate = date.plusDays(1);
        while (!(nextDate.getDayOfWeek().equals(DayOfWeek.FRIDAY) && nextDate.getDayOfMonth() == DESIRED_FRIDAY)) {
            nextDate = nextDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        }
        return nextDate;
    }
}
