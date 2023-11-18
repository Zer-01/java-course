package edu.hw5.Task3;

import java.time.LocalDate;
import java.util.Optional;

public abstract class ChainParser {
    public final ChainParser nextParser;

    public ChainParser(ChainParser nextParser) {
        this.nextParser = nextParser;
    }

    public abstract Optional<LocalDate> parse(String string);
}
