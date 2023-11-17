package edu.project3.analyze;

import edu.project3.arguments.ArgumentContainer;
import java.util.List;
import java.util.Map;

public record LogReport(
    ArgumentContainer arguments,
    List<String> fileNames,
    long logsCount,
    long avgRespSize,
    List<Map.Entry<String, Integer>> reqResourcesCount,
    List<Map.Entry<String, Integer>> respCodesCount,
    List<Map.Entry<String, Integer>> remoteAdrCount,
    List<Map.Entry<String, Integer>> userAgentCount
) {
}
