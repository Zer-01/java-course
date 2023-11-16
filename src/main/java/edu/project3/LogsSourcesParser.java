package edu.project3;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogsSourcesParser {
    private final static String PATH_ERROR = "Wrong path";
    private final static String PATH_FORMAT_ERROR = "Wrong path format";
    private static final Pattern UNKNOWN_FILE_PATTERN = Pattern.compile("(?<path>[^*]*)\\\\(?<fNamePart>[^*]*)\\*");
    private static final Pattern UNKNOWN_PATH_PATTERN =
        Pattern.compile("(?<pathPart>.*)\\\\\\*\\*\\\\(?<fName>[^*]*\\.[^*]*)");
    private static final Pattern LINK_PATTERN = Pattern.compile("https?://.*");

    public List<Path> parse(String path) {
        List<Path> result;
        if (path.matches(UNKNOWN_FILE_PATTERN.toString())) {
            result = parseUnknownFile(path);
        } else if (path.matches(UNKNOWN_PATH_PATTERN.toString())) {
            result = parseUnknownPath(path);
        } else {
            Path tmpPath = Paths.get(path);
            if (Files.exists(tmpPath)) {
                result = List.of(tmpPath);
            } else {
                throw new IllegalArgumentException("Invalid file path");
            }
        }
        return result;
    }

    private static List<Path> parseUnknownFile(String string) {
        Matcher matcher = UNKNOWN_FILE_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(PATH_FORMAT_ERROR);
        }
        Path path = Paths.get(matcher.group("path"));
        String fileNamePart = matcher.group("fNamePart");
        List<Path> pathList = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, fileNamePart + "*.txt")) {
            stream.forEach(pathList::add);
        } catch (IOException e) {
            throw new IllegalArgumentException(PATH_ERROR, e);
        }
        return pathList;
    }

    private static List<Path> parseUnknownPath(String string) {
        Matcher matcher = UNKNOWN_PATH_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(PATH_FORMAT_ERROR);
        }
        Path pathPart = Paths.get(matcher.group("pathPart"));
        String fileName = matcher.group("fName");
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/" + fileName);

        List<Path> pathList = new ArrayList<>();
        try {
            Files.walkFileTree(pathPart, Set.of(), 2, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(file)) {
                        pathList.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new IllegalArgumentException(PATH_ERROR, e);
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
        try (HttpClient client = HttpClient.newHttpClient()) {
            var response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
            stringResponse = response.body();
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException("Invalid link or bad connection", e);
        }

        Path tmpFile;
        try {
            tmpFile = Files.createTempFile("Logs", null);
            Files.write(tmpFile, stringResponse.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error creating temp file", e);
        }
        return List.of(tmpFile);
    }
}
