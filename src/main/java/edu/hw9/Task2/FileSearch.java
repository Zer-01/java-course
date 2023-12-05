package edu.hw9.Task2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileSearch extends RecursiveTask<List<Path>> {
    private final Path directory;
    private final Predicate<Path> predicate;

    private FileSearch(Path directory, Predicate<Path> predicate) {
        this.directory = directory;
        this.predicate = predicate;
    }

    @Override
    public List<Path> compute() {
        try {
            if (Files.isDirectory(directory)) {
                List<FileSearch> subtasks =
                    Files.list(directory)
                        .map(dir -> new FileSearch(dir, predicate))
                        .toList();

                invokeAll(subtasks);

                return subtasks.stream()
                    .flatMap(task -> task.join().stream())
                    .collect(Collectors.toList());
            } else {
                if (predicate.test(directory)) {
                    return List.of(directory);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return List.of();
    }

    public static FileSearch newSearch(Path directory, Predicate<Path> predicate) throws FileNotFoundException {
        if (directory == null || predicate == null) {
            throw new IllegalArgumentException();
        }
        if (!Files.exists(directory)) {
            throw new FileNotFoundException();
        }
        return new FileSearch(directory, predicate);
    }
}
