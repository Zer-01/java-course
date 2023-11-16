package edu.project3.logsParse;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final Pattern LOG_PATTERN = Pattern.compile(
        "(?<adr>[\\d.]*) - .* \\[(?<time>.*)] \"[^ ]* "
            + "(?<reqRes>[^ ]*) [^\"]*\" (?<respCode>\\d*) "
            + "(?<size>\\d*) \".*\" \"(?<agent>[^\"]*)\"");
    private static final String TIME_FORMAT = "dd'/'MMM/yyyy:HH:mm:ss Z";

    private LogParser() {
    }

    public static LogRecord parseLog(String log) {
        Matcher matcher = LOG_PATTERN.matcher(log);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid log format");
        }
        long size;
        try {
            size = Long.parseLong(matcher.group("size"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid size format", e);
        }
        return new LogRecord(
            matcher.group("adr"),
            parseTime(matcher.group("time")),
            matcher.group("reqRes"),
            matcher.group("respCode"),
            size,
            matcher.group("agent")
        );
    }

    private static OffsetDateTime parseTime(String string) {
        OffsetDateTime result;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.ENGLISH);
        try {
            result = OffsetDateTime.parse(string, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid time format in log", e);
        }
        return result;
    }
}
