package edu.hw8;

import edu.hw8.Task3.MultiThread.MTPasswordCracker;
import edu.hw8.Task3.SingleThread.PasswordCracker;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    @TempDir static Path tempPath;

    Map<String, String> expResult = Map.of(
        "a.v.petrov", "94Ag",
        "a.a.popov", "9999",
        "v.v.belov", "fgkI",
        "k.p.maslov", "YRt6"
    );

    @BeforeAll
    static void init() throws IOException {
        Files.createFile(tempPath.resolve("test.txt"));
        Files.writeString(
            tempPath.resolve("test.txt"),
            "a.a.popov   fa246d0262c3925617b0c72bb20eeb1d\n" +
                "a.v.petrov  ff9fac08e5f22da56eb0de4d606c4dba\n" +
                "v.v.belov   db18e4b20c648c7e2ecf744ffcd3ce03\n" +
                "k.p.maslov  1cc6daa9967040022a0c90d7cf7cb060"
        );
    }

    @Test
    void singleThreadTest() {
        PasswordCracker cracker = new PasswordCracker(4, tempPath.resolve("test.txt"));
        cracker.crack();

        Map<String, String> result = cracker.getResult();

        assertThat(result)
            .containsExactlyInAnyOrderEntriesOf(expResult);
    }

    @Test
    void multiThreadTest() {
        MTPasswordCracker cracker = new MTPasswordCracker(4, tempPath.resolve("test.txt"));
        cracker.crack(6);

        Map<String, String> result = cracker.getResult();

        assertThat(result)
            .containsExactlyInAnyOrderEntriesOf(expResult);
    }
}
