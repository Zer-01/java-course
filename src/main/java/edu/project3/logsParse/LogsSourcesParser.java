package edu.project3.logsParse;

import edu.project3.arguments.ArgumentContainer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class LogsSourcesParser {
    private final static String TXT_FILE_EXTENSION = ".txt";

    private LogsSourcesParser() {
    }

    public static List<Path> parse(ArgumentContainer container) {
        String pathStr = container.file();
        if (pathStr.indexOf("https://") == 0) {
            return parseLink(pathStr);
        } else {
            return parsePath(pathStr);
        }
    }

    private static List<Path> parsePath(String pathStr) {
        int lastParentIndex = getEndOfFileParent(pathStr);
        String globPattern = "glob:**/"
            + pathStr.substring(lastParentIndex + 1).replaceAll("\\\\", "/")
            + (pathStr.contains(TXT_FILE_EXTENSION) ? "" : TXT_FILE_EXTENSION);

        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(globPattern);
        Path pathParent = Path.of(pathStr.substring(0, lastParentIndex + 1));

        List<Path> pathList = new ArrayList<>();
        try {
            Files.walkFileTree(pathParent, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(file)) {
                        pathList.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Illegal file path", e);
        }
        return pathList;
    }

    private static List<Path> parseLink(String link) {
        HttpURLConnection connection;
        Path tmpFile;
        try {
            URI uri = new URI(link);
            URL url = uri.toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            tmpFile = Files.createTempFile("Logs", null);
        } catch (URISyntaxException | IOException e) {
            throw new IllegalArgumentException(e);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile.toString()))) {
            String line = reader.readLine();
            while (line != null) {
                writer.write(line);
                writer.newLine();
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return List.of(tmpFile);
    }

    private static int getEndOfFileParent(String pathStr) {
        int firstAsterPos = pathStr.indexOf('*');
        if (firstAsterPos == -1) {
            firstAsterPos = pathStr.length() - 1;
        }

        return pathStr.lastIndexOf(File.separator, firstAsterPos);
    }
}
