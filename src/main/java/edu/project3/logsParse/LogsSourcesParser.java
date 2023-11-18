package edu.project3.logsParse;

import edu.project3.arguments.ArgumentContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(new URI(link))
                .GET()
                .build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid link", e);
        }

        String stringResponse;
        try {
            var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
            stringResponse = response.body();
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException("Invalid link or bad connection", e);
        }

        Path tmpFile;
        try {
            tmpFile = Files.createTempFile("Logs", null);
            Files.writeString(tmpFile, stringResponse);
        } catch (IOException e) {
            throw new RuntimeException("Creating temp file error", e);
        }
        return List.of(tmpFile);
    }

    private static int getEndOfFileParent(String pathStr) {
        int firstAsterPos = pathStr.indexOf('*');
        if (firstAsterPos == -1) {
            firstAsterPos = pathStr.length() - 1;
        }
        int lastIndex = pathStr.lastIndexOf('\\', firstAsterPos);
        return (lastIndex == -1) ? pathStr.lastIndexOf('/', firstAsterPos) : lastIndex;
    }
}
