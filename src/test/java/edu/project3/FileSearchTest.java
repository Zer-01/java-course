package edu.project3;

import edu.project3.arguments.ArgumentContainer;
import edu.project3.logsParse.LogsSourcesParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class FileSearchTest {
    static @TempDir Path tmpDir;
    static char delim;

    @BeforeAll
    public static void init() throws IOException {                //  tmpDir
        Path testDir1 = tmpDir.resolve("testDir1");         //  |-testDir1
        Path testDir2 = tmpDir.resolve("testDir2");         //  |  |-testFile1.txt
        Files.createDirectory(testDir1);                          //  |-testDir2
        Files.createDirectory(testDir2);                          //  |  |-testFile2.txt
        Files.createFile(tmpDir.resolve("testFile0.txt"));  //  |-testFile0.txt
        Files.createFile(testDir1.resolve("testFile1.txt"));//
        Files.createFile(testDir2.resolve("testFile2.txt"));//

        delim = System.getProperty("os.name").contains("Windows") ? '\\' : '/';
    }

    @Test
    void fullPathSearchTest() {
        ArgumentContainer input =
            new ArgumentContainer(resolveAndToString(tmpDir, "testDir1", "testFile1.txt"), null, null, null);
        List<Path> expResult = List.of(tmpDir.resolve("testDir1").resolve("testFile1.txt"));

        List<Path> result = LogsSourcesParser.parse(input);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void notFullPathSearchTest() {
        ArgumentContainer input =
            new ArgumentContainer(resolveAndToString(tmpDir, "**", "testFile2.txt"), null, null, null);
        List<Path> expResult = List.of(tmpDir.resolve("testDir2").resolve("testFile2.txt"));

        List<Path> result = LogsSourcesParser.parse(input);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void nutFullNameOfFileTest() {
        ArgumentContainer input =
            new ArgumentContainer(resolveAndToString(tmpDir, "testDir2", "tes*"), null, null, null);
        List<Path> expResult = List.of(tmpDir.resolve("testDir2").resolve("testFile2.txt"));

        List<Path> result = LogsSourcesParser.parse(input);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void nutFullNameAndPathOfFileTest() {
        ArgumentContainer input =
            new ArgumentContainer(resolveAndToString(tmpDir, "**", "tes*"), null, null, null);
        List<Path> expResult = List.of(
            tmpDir.resolve("testDir1").resolve("testFile1.txt"),
            tmpDir.resolve("testDir2").resolve("testFile2.txt"),
            tmpDir.resolve("testFile0.txt")
        );

        List<Path> result = LogsSourcesParser.parse(input);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void fileNotExistTest() {
        ArgumentContainer input =
            new ArgumentContainer(resolveAndToString(tmpDir, "**", "nope"), null, null, null);

        List<Path> result = LogsSourcesParser.parse(input);

        assertThat(result)
            .isEmpty();
    }

    private String resolveAndToString(Path dir, String... strings) {
        StringBuilder builder = new StringBuilder();
        builder.append(dir.toString());
        for (String str : strings) {
            builder.append(delim).append(str);
        }
        return builder.toString();
    }
}
