package edu.hw5.Task3;

import java.time.LocalDate;
import java.util.Optional;

public class Parser5 extends ChainParser {
    Parser5(ChainParser nextParser) {
        super(nextParser);
    }

    @Override
    public Optional<LocalDate> parse(String string) {
        Optional<LocalDate> parsedDateOpt;
        parsedDateOpt = switch (string) {
            case "today" -> Optional.of(LocalDate.now());
            case "tomorrow" -> Optional.of(LocalDate.now().plusDays(1));
            case "yesterday" -> Optional.of(LocalDate.now().minusDays(1));
            default -> (nextParser == null) ? Optional.empty() : nextParser.parse(string);
        };
        return parsedDateOpt;
    }
}
