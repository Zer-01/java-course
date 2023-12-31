package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;

public class Task2 {
    private static final char FILE_NAME_SEPARATOR = '.';

    private Task2() {
    }

    public static void cloneFile(Path path) {
        String fileName = path.getFileName().toString();
        String prefix = fileName.substring(0, fileName.lastIndexOf(FILE_NAME_SEPARATOR));
        String suffix = fileName.substring(fileName.lastIndexOf(FILE_NAME_SEPARATOR));

        int copyNumber = 1;
        Path copyPath = path.resolveSibling(prefix + " — копия" + suffix);

        while (Files.exists(copyPath)) {
            copyNumber++;
            copyPath = path.resolveSibling(prefix + " — копия (" + copyNumber + ")" + suffix);
        }
        try {
            Files.copy(path, copyPath);
        } catch (IOException e) {
            LogManager.getLogger().error(e);
        }
    }
}
