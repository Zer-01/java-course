package edu.project3;

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
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RawLogParser {
    private final static String PATH_ERROR = "Wrong path";
    private final static String PATH_FORMAT_ERROR = "Wrong path format";
    private static final Pattern UNKNOWN_FILE_PATTERN = Pattern.compile("(?<path>[^*]*)\\\\(?<fNamePart>[^*]*)\\*");
    private static final Pattern UNKNOWN_PATH_PATTERN =
        Pattern.compile("(?<pathPart>.*)\\\\\\*\\*\\\\(?<fName>[^*]*\\.[^*]*)");
    private static final Pattern LINK_PATTERN = Pattern.compile("https?://.*");

    public List<Boolean> parse(String path) {
        return List.of(true);
    }

    private static List<Boolean> parseUnknownFile(String string) {
        Matcher matcher = UNKNOWN_FILE_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(PATH_FORMAT_ERROR);
        }
        Path path = Paths.get(matcher.group("path"));
        String fileNamePart = matcher.group("fNamePart");

        try {
            Files.newDirectoryStream(path, fileNamePart + "*.txt").forEach(System.out::println);
        } catch (IOException e) {
            throw new IllegalArgumentException(PATH_ERROR, e);
        }
        return List.of(false);
    }

    private static List<Boolean> parseUnknownPath(String string) {
        Matcher matcher = UNKNOWN_PATH_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(PATH_FORMAT_ERROR);
        }
        Path pathPart = Paths.get(matcher.group("pathPart"));
        String fileName = matcher.group("fName");
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/" + fileName);

        try {
            Files.walkFileTree(pathPart, Set.of(), 2, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(file)) {
                        System.out.println(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new IllegalArgumentException(PATH_ERROR, e);
        }
        return List.of(false);
    }

    private static List<Boolean> parseLink(String link) {
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                .uri(new URI(link))
                .GET()
                .build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Wrong link", e);
        }

        String stringResponse;
        try {
            var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
            stringResponse = response.body();
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException("Wrong link or connection", e);
        }

        String[] linesStrings = stringResponse.split("\\r?\\n");
        for (String line : linesStrings) {
            System.out.println(line);
        }
        return List.of(true);
    }
}
