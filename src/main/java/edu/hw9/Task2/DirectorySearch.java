package edu.hw9.Task2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class DirectorySearch extends RecursiveTask<List<Path>> {
    private final Path directory;
    private final int searchFilesCount;

    private DirectorySearch(Path directory, int searchFilesCount) {
        this.directory = directory;
        this.searchFilesCount = searchFilesCount;
    }

    @Override
    public List<Path> compute() {
        try {
            if (Files.isDirectory(directory)) {
                List<DirectorySearch> subtasks =
                    Files.list(directory)
                        .map(dir -> new DirectorySearch(dir, searchFilesCount))
                        .toList();

                invokeAll(subtasks);

                List<Path> result = subtasks.stream()
                    .flatMap(task -> task.join().stream())
                    .collect(Collectors.toList());

                if (Files.list(directory).count() >= searchFilesCount) {
                    result.add(directory);
                }

                return result;
            } else {
                return List.of();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static DirectorySearch newSearch(Path directory, int searchFilesCount) throws FileNotFoundException {
        if (searchFilesCount < 0 || directory == null) {
            throw new IllegalArgumentException();
        }
        if (!Files.exists(directory)) {
            throw new FileNotFoundException();
        }
        return new DirectorySearch(directory, searchFilesCount);
    }
}
