package edu.project3.analyze;

import edu.project3.arguments.ArgumentContainer;
import edu.project3.logsParse.LogParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class LogAnalyzer {
    private long requestsCount;
    private long avgRespSize;
    private final List<String> fileNames;
    private final Map<String, Integer> reqResourcesCount;
    private final Map<String, Integer> respCodesCount;
    private final Map<String, Integer> remoteAdrCount;
    private final Map<String, Integer> userAgentCount;

    public LogAnalyzer() {
        requestsCount = 0;
        avgRespSize = 0;
        fileNames = new ArrayList<>();
        reqResourcesCount = new HashMap<>();
        respCodesCount = new HashMap<>();
        remoteAdrCount = new HashMap<>();
        userAgentCount = new HashMap<>();
    }

    public void analyze(List<Path> paths, ArgumentContainer args) {
        for (Path path : paths) {
            fileNames.add(path.getFileName().toString());
            try (Stream<String> linesStream = Files.lines(path)) {
                linesStream
                    .map(LogParser::parseLog)
                    .filter(log -> log.time().isAfter(args.from()))
                    .filter(log -> log.time().isBefore(args.to()))
                    .forEach(logRecord -> {
                        requestsCount++;
                        avgRespSize += logRecord.responseSize();
                        reqResourcesCount.put(
                            logRecord.requestedResource(),
                            reqResourcesCount.getOrDefault(logRecord.requestedResource(), 0) + 1
                        );
                        respCodesCount.put(
                            logRecord.responseCode(),
                            respCodesCount.getOrDefault(logRecord.responseCode(), 0) + 1
                        );
                        remoteAdrCount.put(
                            logRecord.remoteAdr(),
                            remoteAdrCount.getOrDefault(logRecord.remoteAdr(), 0) + 1
                        );
                        userAgentCount.put(
                            logRecord.userAgent(),
                            userAgentCount.getOrDefault(logRecord.userAgent(), 0) + 1
                        );
                    });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        avgRespSize += requestsCount;
    }

    public LogReport getReport(int maxTableRows) {
        return new LogReport(
            fileNames,
            requestsCount,
            avgRespSize,
            sortMap(reqResourcesCount, maxTableRows),
            sortMap(respCodesCount, maxTableRows),
            sortMap(remoteAdrCount, maxTableRows),
            sortMap(userAgentCount, maxTableRows)
        );
    }

    private List<Map.Entry<String, Integer>> sortMap(Map<String, Integer> map, int maxTableRows) {
        return map.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .limit(maxTableRows)
            .toList();
    }
}
