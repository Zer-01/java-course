package edu.project3;

import edu.project3.analyze.LogAnalyzer;
import edu.project3.analyze.LogReport;
import edu.project3.arguments.ArgumentContainer;
import edu.project3.arguments.ArgumentParser;
import edu.project3.logsParse.LogsSourcesParser;
import edu.project3.output.Printer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private final static Logger LOGGER = LogManager.getLogger();
    private static final int MAX_ROWS = 3;

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        String[] arguments = {"--path", "https://raw.githubusercontent.com/"
            + "elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs", "--format", "markdown"};

        try {
            ArgumentContainer parsedArgs = ArgumentParser.parse(arguments);
            LogAnalyzer analyzer = new LogAnalyzer();
            LogReport report = analyzer.analyze(LogsSourcesParser.parse(parsedArgs), parsedArgs).getReport(MAX_ROWS);
            Printer printer = new Printer(report);
            Path path = Paths.get(System.getProperty("user.dir")
                + "\\src\\main\\java\\edu\\project3\\logReport.md");
            printer.printToFile(path);

            arguments[arguments.length - 1] = "adoc";
            parsedArgs = ArgumentParser.parse(arguments);
            analyzer = new LogAnalyzer();
            report = analyzer.analyze(LogsSourcesParser.parse(parsedArgs), parsedArgs).getReport(MAX_ROWS);
            printer = new Printer(report);
            path = path.getParent().resolve("logReport.adoc");
            printer.printToFile(path);
        }catch (IllegalArgumentException e) {
            LOGGER.error(e);
        }
    }
}
