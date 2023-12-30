package edu.hw5.Task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class TimeFormatParser {
    private TimeFormatParser() {

    }

    public static Optional<LocalDate> formatParse(
        String string,
        DateTimeFormatter timeFormatter,
        ChainParser nextParser
    ) {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(string, timeFormatter);
        } catch (DateTimeParseException e) {
            if (nextParser == null) {
                return Optional.empty();
            } else {
                return nextParser.parse(string);
            }
        }
        return Optional.of(parsedDate);
    }
}
