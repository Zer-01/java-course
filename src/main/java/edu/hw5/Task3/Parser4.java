package edu.hw5.Task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Parser4 extends ChainParser {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("d/M/uu");

    public Parser4(ChainParser nextParser) {
        super(nextParser);
    }

    @Override
    public Optional<LocalDate> parse(String string) {
        return TimeFormatParser.formatParse(string, timeFormatter, nextParser);
    }
}
