package edu.project3;

import edu.project3.arguments.ArgumentContainer;
import edu.project3.arguments.ArgumentParser;
import edu.project3.arguments.PrintFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ArgumentParserTest {
    @Test
    void allArgumentsTest() {
        String[] args =
            {"--path", "https://testlink.com", "--from", "2023-08-31", "--to", "2023-09-22", "--format", "adoc"};
        ArgumentContainer expResult = new ArgumentContainer(
            "https://testlink.com",
            OffsetDateTime.of(2023, 8, 31, 0, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2023, 9, 22, 23, 59, 59, 0, ZoneOffset.UTC),
            PrintFormat.ADOC
        );

        ArgumentContainer result = ArgumentParser.parse(args);

        assertThat(result)
            .isEqualTo(expResult);
    }

    @Test
    void onlyPathTest() {
        String[] args = {"--path", "https://testlink.com"};
        ArgumentContainer expResult = new ArgumentContainer(
            "https://testlink.com", null, null, null
        );

        ArgumentContainer result = ArgumentParser.parse(args);

        assertThat(result)
            .isEqualTo(expResult);
    }

    @Test
    void emptyInputTest() {
        String[] args = {};

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                ArgumentContainer result = ArgumentParser.parse(args);
            });
    }

    @Test
    void noValuesTest() {
        String[] args = {"--path", "https://testlink.com", "--from", "--to"};

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                ArgumentContainer result = ArgumentParser.parse(args);
            });
    }

    @Test
    void noPathTest() {
        String[] args =
            {"--from", "2023-08-31", "--to", "2023-09-22", "--format", "adoc"};

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                ArgumentContainer result = ArgumentParser.parse(args);
            });
    }
}
