package edu.hw6;

import edu.hw6.Task3.AbstractFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    public static final AbstractFilter regularFile = Files::isRegularFile;
    @TempDir
    Path tempDir;

    @Test
    void regularFileFilterTest() throws IOException {
        Path regularFilePath = createFile("regularFile.txt");

        assertThat(regularFile.accept(regularFilePath))
            .isTrue();
        assertThat(regularFile.accept(tempDir))
            .isFalse();
    }

    @Test
    void largerThanFilterTest() throws IOException {
        Path largeFile = createFileWithSize("largeFile.txt", 200000);
        Path smallFile = createFileWithSize("smallFile.txt", 50000);

        assertThat(AbstractFilter.largerThan(100000).accept(largeFile))
            .isTrue();
        assertThat(AbstractFilter.largerThan(100000).accept(smallFile))
            .isFalse();
    }

    @Test
    void magicNumberFilterTest() throws IOException {
        Path pngFile = createFileWithBytes("image.png", 'P', 'N', 'G');
        Path txtFile = createFileWithBytes("text.txt", 'T', 'E', 'S', 'T');

        assertThat(AbstractFilter.magicNumber('P', 'N', 'G').accept(pngFile))
            .isTrue();
        assertThat(AbstractFilter.magicNumber('P', 'N', 'G').accept(txtFile))
            .isFalse();
    }

    @Test
    void globMatchesFilterTest() throws IOException {
        Path matchingFile = createFile("image.png");
        Path nonMatchingFile = createFile("text.txt");

        assertThat(AbstractFilter.globMatches(".*\\.png").accept(matchingFile))
            .isTrue();
        assertThat(AbstractFilter.globMatches(".*\\.png").accept(nonMatchingFile))
            .isFalse();
    }

    @Test
    void regexContainsFilterTest() throws IOException {
        Path matchingFile = createFile("file-123.txt");
        Path nonMatchingFile = createFile("text.txt");

        assertThat(AbstractFilter.regexContains("[-]").accept(matchingFile))
            .isTrue();
        assertThat(AbstractFilter.regexContains("[-]").accept(nonMatchingFile))
            .isFalse();
    }

    @Test
    void chainTest() throws IOException {
        Path file1 = createFile("Ofile-123.txt");
        Path file2 = createFile("file-123.txt");
        Path file3 = createFile("Ofile-123.png");

        AbstractFilter filter = regularFile
            .and(AbstractFilter.regexContains("O"))
            .and(AbstractFilter.globMatches(".*\\.txt"))
            .and(AbstractFilter.regexContains("[-]"));

        assertThat(filter.accept(file1))
            .isTrue();
        assertThat(filter.accept(file2))
            .isFalse();
        assertThat(filter.accept(file3))
            .isFalse();
    }

    private Path createFile(String fileName) throws IOException {
        return Files.createFile(tempDir.resolve(fileName));
    }

    private Path createFileWithSize(String fileName, long size) throws IOException {
        Path file = createFile(fileName);
        byte[] data = new byte[(int) size];
        Files.write(file, data);
        return file;
    }

    private Path createFileWithBytes(String fileName, int... bytes) throws IOException {
        Path file = createFile(fileName);
        byte[] data = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            data[i] = (byte) bytes[i];
        }
        Files.write(file, data);
        return file;
    }
}
