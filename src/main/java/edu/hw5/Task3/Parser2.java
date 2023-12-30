package edu.hw5.Task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Parser2 extends ChainParser {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-d");

    public Parser2(ChainParser nextParser) {
        super(nextParser);
    }

    @Override
    public Optional<LocalDate> parse(String string) {
        return TimeFormatParser.formatParse(string, timeFormatter, nextParser);
    }
}
