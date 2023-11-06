package edu.hw5.Task3;

import java.time.LocalDate;
import java.util.Optional;

public class DateParser {
    private DateParser() {

    }

    public static Optional<LocalDate> parseDate(String string) {
        ChainParser parser =
            new Parser1(
                new Parser2(
                    new Parser3(
                        new Parser4(
                            new Parser5(
                                new Parser6(null)
                            )
                        )
                    )
                )
            );
        return parser.parse(string);
    }
}
