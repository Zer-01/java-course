package edu.hw6.Task3;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    default AbstractFilter and(AbstractFilter other) {
        return (Path path) -> this.accept(path) && other.accept(path);
    }

    static AbstractFilter largerThan(long size) {
        return (Path path) -> {
            try {
                return Files.size(path) > size;
            } catch (IOException e) {
                return false;
            }
        };
    }

    static AbstractFilter globMatches(String glob) {
        return path -> path.getFileName().toString().matches(glob);
    }

    static AbstractFilter regexContains(String regex) {
        return path -> path.getFileName().toString().matches(".*" + regex + ".*");
    }

    static AbstractFilter magicNumber(int... magicBytes) {
        return (Path path) -> {
            try (FileInputStream fis = new FileInputStream(path.toString())) {
                byte[] buffer = new byte[magicBytes.length];
                int bytesRead = fis.read(buffer);
                if (magicBytes.length > bytesRead) {
                    return false;
                }
                for (int i = 0; i < magicBytes.length; i++) {
                    if (magicBytes[i] != buffer[i]) {
                        return false;
                    }
                }
                return true;
            } catch (IOException e) {
                return false;
            }
        };
    }
}
