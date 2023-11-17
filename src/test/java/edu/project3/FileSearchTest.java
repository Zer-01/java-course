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

    @BeforeAll
    public static void init() throws IOException {                //  tmpDir
        Path testDir1 = tmpDir.resolve("TestDir1");         //  |-testDir1
        Path testDir2 = tmpDir.resolve("TestDir2");         //  |  |-testFile1.txt
        Files.createDirectory(testDir1);                          //  |-testDir2
        Files.createDirectory(testDir2);                          //  |  |-testFile2.txt
        Files.createFile(tmpDir.resolve("testFile0.txt"));  //  |-testFile0.txt
        Files.createFile(testDir1.resolve("testFile1.txt"));//
        Files.createFile(testDir2.resolve("testFile2.txt"));//
    }

    @Test
    void fullPathSearchTest() {
        ArgumentContainer input =
            new ArgumentContainer(tmpDir.toString() + "\\testDir1\\testFile1.txt", null, null, null);
        List<Path> expResult = List.of(tmpDir.resolve("testDir1").resolve("testFile1.txt"));

        List<Path> result = LogsSourcesParser.parse(input);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void notFullPathSearchTest() {
        ArgumentContainer input =
            new ArgumentContainer(tmpDir.toString() + "\\**\\testFile2.txt", null, null, null);
        List<Path> expResult = List.of(tmpDir.resolve("testDir2").resolve("testFile2.txt"));

        List<Path> result = LogsSourcesParser.parse(input);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void nutFullNameOfFileTest() {
        ArgumentContainer input =
            new ArgumentContainer(tmpDir.toString() + "\\testDir2\\tes*", null, null, null);
        List<Path> expResult = List.of(tmpDir.resolve("testDir2").resolve("testFile2.txt"));

        List<Path> result = LogsSourcesParser.parse(input);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void nutFullNameAndPathOfFileTest() {
        ArgumentContainer input =
            new ArgumentContainer(tmpDir.toString() + "\\**\\tes*", null, null, null);
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
            new ArgumentContainer(tmpDir.toString() + "\\**\\nope", null, null, null);

        List<Path> result = LogsSourcesParser.parse(input);

        assertThat(result)
            .isEmpty();
    }
}
