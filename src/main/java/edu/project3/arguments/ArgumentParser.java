package edu.project3.arguments;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ArgumentParser {
    private final static LocalTime END_OF_DAY = LocalTime.of(23, 59, 59);

    private ArgumentParser() {
    }

    public static ArgumentContainer parse(String[] args) {
        if (args.length == 0 || args.length % 2 != 0) {
            throw new IllegalArgumentException("Not enough parameters");
        }

        String path = null;
        OffsetDateTime from = null;
        OffsetDateTime to = null;
        PrintFormat format = null;

        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "--path":
                    path = args[i + 1];
                    break;
                case "--from":
                    from = tryParseDate(args[i + 1]).atStartOfDay().atOffset(ZoneOffset.UTC);
                    break;
                case "--to":
                    to = tryParseDate(args[i + 1]).atTime(END_OF_DAY).atOffset(ZoneOffset.UTC);
                    break;
                case "--format":
                    format = tryParseFormat(args[i + 1]);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid argument: " + args[i]);
            }
        }

        if (path == null) {
            throw new IllegalArgumentException("Path not specified");
        }

        return new ArgumentContainer(path, from, to, format);
    }

    private static LocalDate tryParseDate(String string) {
        LocalDate date;
        try {
            date = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException ein) {
            throw new IllegalArgumentException("Invalid date format", ein);
        }
        return date;
    }

    private static PrintFormat tryParseFormat(String string) {
        return switch (string) {
            case "markdown" -> PrintFormat.MARKDOWN;
            case "adoc" -> PrintFormat.ADOC;
            default -> throw new IllegalArgumentException("Invalid print format");
        };
    }
}
