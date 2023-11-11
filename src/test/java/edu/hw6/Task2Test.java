package edu.hw6;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    void normalTest() {
        Path tmpFile = null;
        try {
            tmpFile = Files.createTempFile("Tsk1Tst", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Task2.cloneFile(tmpFile);
        Task2.cloneFile(tmpFile);
        Task2.cloneFile(tmpFile);

        String fileName = tmpFile.getFileName().toString();
        String prefix = fileName.substring(0, fileName.lastIndexOf('.'));
        String suffix = fileName.substring(fileName.lastIndexOf('.'));

        assertThat(tmpFile.resolveSibling(prefix + " — копия" + suffix))
            .exists();
        assertThat(tmpFile.resolveSibling(prefix + " — копия (2)" + suffix))
            .exists();
        assertThat(tmpFile.resolveSibling(prefix + " — копия (3)" + suffix))
            .exists();
    }
}
