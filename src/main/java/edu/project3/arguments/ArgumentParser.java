package edu.project3.arguments;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ArgumentParser {
    private ArgumentParser() {
    }

    public static ArgumentContainer parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Not enough parameters");
        }

        String path = null;
        OffsetDateTime from = null;
        OffsetDateTime to = null;
        ArgumentContainer.PrintFormat format = null;

        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "--path":
                    path = args[i + 1];
                    break;
                case "--from":
                    from = tryParseDate(args[i + 1]);
                    break;
                case "--to":
                    to = tryParseDate(args[i + 1]);
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

    private static OffsetDateTime tryParseDate(String string) {
        LocalDate date;
        try {
            date = LocalDate.parse(string, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            try {
                date = LocalDate.parse(string, DateTimeFormatter.ISO_DATE);
            } catch (DateTimeParseException ein) {
                throw new IllegalArgumentException("Invalid date format", ein);
            }
        }
        return date.atStartOfDay().atOffset(ZoneOffset.ofHours(0));
    }

    private static ArgumentContainer.PrintFormat tryParseFormat(String string) {
        return switch (string) {
            case "markdown" -> ArgumentContainer.PrintFormat.MARKDOWN;
            case "adoc" -> ArgumentContainer.PrintFormat.ADOC;
            default -> throw new IllegalArgumentException("Invalid print format");
        };
    }
}
