package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @TempDir
    Path tempDir;

    @Test
    void normalTest() throws IOException {
        Path tmpFile = Files.createFile(tempDir.resolve("Task2Tst.txt"));

        Task2.cloneFile(tmpFile);
        Task2.cloneFile(tmpFile);
        Task2.cloneFile(tmpFile);

        assertThat(tempDir.resolve("Task2Tst — копия.txt"))
            .exists();
        assertThat(tempDir.resolve("Task2Tst — копия (2).txt"))
            .exists();
        assertThat(tempDir.resolve("Task2Tst — копия (3).txt"))
            .exists();
    }
}
