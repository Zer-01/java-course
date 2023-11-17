package edu.project3.output;

import edu.project3.analyze.LogReport;
import edu.project3.arguments.PrintFormat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Printer {
    private String output;
    private final LogReport report;
    private final static Logger LOGGER = LogManager.getLogger();

    public Printer(LogReport report) {
        this.output = null;
        this.report = report;
        render();
    }

    public void printToCli() {
        LOGGER.info(output);
    }

    public void printToFile(Path filePath) {
        try {
            Files.writeString(filePath, output);
        } catch (IOException e) {
            throw new RuntimeException("File write error", e);
        }
    }

    private void render() {
        StatRenderer renderer =
            (report.arguments().format() == PrintFormat.ADOC) ? new AdocRenderer(report) : new MarkdownRenderer(report);
        output = renderer.render();
    }
}
