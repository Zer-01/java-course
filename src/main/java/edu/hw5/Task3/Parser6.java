package edu.hw5.Task3;

import java.time.LocalDate;
import java.util.Optional;

public class Parser6 extends ChainParser {
    private final static String PATTERN_OF_INPUT = "\\d+ days? ago";

    public Parser6(ChainParser nextParser) {
        super(nextParser);
    }

    @Override
    public Optional<LocalDate> parse(String string) {
        if (string.matches(PATTERN_OF_INPUT)) {
            String intStr = string.split(" ", 2)[0];
            int daysAgo = Integer.parseInt(intStr);
            return Optional.of(LocalDate.now().minusDays(daysAgo));
        } else {
            return (nextParser == null) ? Optional.empty() : nextParser.parse(string);
        }
    }
}
