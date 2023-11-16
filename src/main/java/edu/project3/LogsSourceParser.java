package edu.project3;

import java.io.BufferedReader;
import java.io.FileReader;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogsSourceParser {
    private final static String PATH_ERROR = "Wrong path";
    private final static String PATH_FORMAT_ERROR = "Wrong path format";
    private static final Pattern UNKNOWN_FILE_PATTERN = Pattern.compile("(?<path>[^*]*)\\\\(?<fNamePart>[^*]*)\\*");
    private static final Pattern UNKNOWN_PATH_PATTERN =
        Pattern.compile("(?<pathPart>.*)\\\\\\*\\*\\\\(?<fName>[^*]*\\.[^*]*)");
    private static final Pattern LINK_PATTERN = Pattern.compile("https?://.*");

    public List<Boolean> parse(String path) {
        return List.of(true);
    }

    private static List<LogRecord> parseUnknownFile(String string) {
        Matcher matcher = UNKNOWN_FILE_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(PATH_FORMAT_ERROR);
        }
        Path path = Paths.get(matcher.group("path"));
        String fileNamePart = matcher.group("fNamePart");
        List<LogRecord> logList = new ArrayList<>();
        try {
            Files.newDirectoryStream(path, fileNamePart + "*.txt").forEach(file -> parseFile(file, logList));
        } catch (IOException e) {
            throw new IllegalArgumentException(PATH_ERROR, e);
        }
        return logList;
    }

    private static List<LogRecord> parseUnknownPath(String string) {
        Matcher matcher = UNKNOWN_PATH_PATTERN.matcher(string);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(PATH_FORMAT_ERROR);
        }
        Path pathPart = Paths.get(matcher.group("pathPart"));
        String fileName = matcher.group("fName");
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/" + fileName);

        List<LogRecord> logList = new ArrayList<>();
        try {
            Files.walkFileTree(pathPart, Set.of(), 2, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if (pathMatcher.matches(file)) {
                        parseFile(file, logList);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new IllegalArgumentException(PATH_ERROR, e);
        }
        return logList;
    }

    private static List<LogRecord> parseLink(String link) {
        List<LogRecord> logList = new ArrayList<>();
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

        String[] linesStrings = stringResponse.split("\\r?\\n");
        for (String line : linesStrings) {
            logList.add(LogParser.parseLog(line));
        }
        return logList;
    }

    private static void parseFile(Path file, List<LogRecord> list) {
        String tmpString;
        try (BufferedReader reader = new BufferedReader(new FileReader(file.toString()))) {
            tmpString = reader.readLine();
            while (!tmpString.isEmpty()) {
                list.add(LogParser.parseLog(tmpString));
                tmpString = reader.readLine();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Parse file error", e);
        }
    }
}
