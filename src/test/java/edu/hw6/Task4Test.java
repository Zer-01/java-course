package edu.hw6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @TempDir
    Path tempDir;

    @Test
    void normalTest() throws IOException {
        Path tmpFile = Files.createFile(tempDir.resolve("Task4Tst.tmp"));

        Task4.outputStreamComp(tmpFile);
        String result;

        try (BufferedReader reader = new BufferedReader(new FileReader(tmpFile.toString()))) {
            result = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertThat(result)
            .isEqualTo("Programming is learned by writing programs. ― Brian Kernighan");
    }
}
