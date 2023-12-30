package edu.project3;

import edu.project3.logsParse.LogParser;
import edu.project3.logsParse.LogRecord;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class LogParserTest {
    @Test
    void normalTest() {
        String log = "210.245.80.75 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_2 HTTP/1.1\" 304 0 \"-\" "
            + "\"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\"";
        LogRecord expResult = new LogRecord(
            "210.245.80.75",
            OffsetDateTime.of(2015, 5, 17, 8, 5, 32, 0, ZoneOffset.UTC),
            "/downloads/product_2",
            "304",
            0,
            "Debian APT-HTTP/1.3 (1.0.1ubuntu2)"
        );

        LogRecord result = LogParser.parseLog(log);

        assertThat(result)
            .isEqualTo(expResult);
    }

    @Test
    void invalidLogTest() {
        String log = "210.245.80.75 - - \"GET /downloads/product_2 HTTP/1.1\" 304 0 \"-\" "
            + "\"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\"";

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                LogRecord result = LogParser.parseLog(log);
            });
    }
}
