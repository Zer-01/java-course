package edu.hw9;

import edu.hw9.Task2.DirectorySearch;
import edu.hw9.Task2.FileSearch;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class Task2Test {
    static @TempDir Path tmpDir;

    @BeforeAll
    public static void init() throws IOException {                //  tmpDir
        Path testDir1 = tmpDir.resolve("testDir1");         //  |-testDir1
        Path testDir2 = tmpDir.resolve("testDir2");         //  |  |-testFile1.txt
        Files.createDirectory(testDir1);                          //  |  |-testFile4.txt
        Files.createDirectory(testDir2);                          //  |-testDir2
        Files.createFile(tmpDir.resolve("testFile0.txt"));  //  |  |-testFile2.txt
        Files.createFile(testDir1.resolve("testFile1.txt"));//  |-testFile0.txt
        Files.createFile(testDir2.resolve("testFile2.txt"));//  |-testFile3
        createFileWithSize(tmpDir.resolve("testFile3.png"), 20);
        createFileWithSize(testDir1.resolve("testFile4.png"), 10);
    }

    @Test
    void DirectorySearchTest() throws FileNotFoundException {
        DirectorySearch searchTask = DirectorySearch.newSearch(tmpDir, 2);
        List<Path> expResult = List.of(tmpDir, tmpDir.resolve("testDir1"));

        List<Path> result = ForkJoinPool.commonPool().invoke(searchTask);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void FileSearchTest() throws FileNotFoundException {
        Predicate<Path> predicate = file -> {
            try {
                return file.getFileName().toString().matches(".*\\.png") && Files.size(file) > 9;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        FileSearch searchTask = FileSearch.newSearch(tmpDir, predicate);
        List<Path> expResult = List.of(
            tmpDir.resolve("testFile3.png"),
            tmpDir.resolve("testDir1").resolve("testFile4.png")
        );

        List<Path> result = ForkJoinPool.commonPool().invoke(searchTask);

        assertThat(result)
            .containsExactlyInAnyOrderElementsOf(expResult);
    }

    @Test
    void FileNotFoundTest() {
        assertThatExceptionOfType(FileNotFoundException.class)
            .isThrownBy(() -> FileSearch.newSearch(Path.of("nofile"), file -> true));

        assertThatExceptionOfType(FileNotFoundException.class)
            .isThrownBy(() -> DirectorySearch.newSearch(Path.of("nofile"), 10));
    }

    private static void createFileWithSize(Path file, long size) throws IOException {
        Files.createFile(file);
        byte[] data = new byte[(int) size];
        Files.write(file, data);
    }
}
