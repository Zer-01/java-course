package edu.project3;

import edu.project3.analyze.LogAnalyzer;
import edu.project3.analyze.LogReport;
import edu.project3.arguments.ArgumentContainer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class LogAnalyzerTest {
    static @TempDir Path tmpDir;

    @BeforeAll
    public static void init() throws IOException {
        Files.createFile(tmpDir.resolve("testFile.txt"));
        Files.createFile(tmpDir.resolve("emptyFile.txt"));
        String input =
            "217.168.17.5 - - [17/May/2015:08:05:02 +0000] \"GET /downloads/product_2 HTTP/1.1\" 404 337 \"-\" "
                + "\"Debian APT-HTTP/1.3 (0.8.10.3)\"\n"

                + "217.168.17.5 - - [17/May/2015:08:05:42 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 332 \"-\" "
                + "\"Debian APT-HTTP/1.3 (0.8.10.3)\"\n"

                + "80.91.33.133 - - [17/May/2015:08:05:01 +0000] \"GET /downloads/product_1 HTTP/1.1\" "
                + "304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"";
        Files.writeString(tmpDir.resolve("testFile.txt"), input);

    }

    @Test
    void normalTest() {
        ArgumentContainer emptyArgs = new ArgumentContainer(null, null, null, null);
        LogReport expResult = new LogReport(emptyArgs, List.of("testFile.txt"), 3, 223,
            List.of(Map.entry("/downloads/product_1", 2), Map.entry("/downloads/product_2", 1)),
            List.of(Map.entry("404", 2), Map.entry("304", 1)),
            List.of(Map.entry("217.168.17.5", 2), Map.entry("80.91.33.133", 1)),
            List.of(
                Map.entry("Debian APT-HTTP/1.3 (0.8.10.3)", 2),
                Map.entry("Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)", 1)
            )
        );
        LogAnalyzer analyzer = new LogAnalyzer();
        LogReport result = analyzer.analyze(List.of(tmpDir.resolve("testFile.txt")), emptyArgs).getReport(3);

        assertThat(result)
            .isEqualTo(expResult);
    }

    @Test
    void emptyFile() {
        ArgumentContainer emptyArgs = new ArgumentContainer(null, null, null, null);
        LogAnalyzer analyzer = new LogAnalyzer();

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                analyzer.analyze(List.of(tmpDir.resolve("emptyFile.txt")), emptyArgs);
            });
    }
}
