package edu.hw8.Task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BaseReader {
    private BaseReader() {
    }

    public static Map<String, String> read(Path path) {
        try (Stream<String> stream = Files.lines(path)) {
            return stream
                .map(str -> str.split("\\s+"))
                .collect(Collectors.toMap(str -> str[1], str -> str[0]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
