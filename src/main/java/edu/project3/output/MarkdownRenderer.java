package edu.project3.output;

import edu.project3.analyze.LogReport;
import java.time.OffsetDateTime;

@SuppressWarnings("MultipleStringLiterals")
public class MarkdownRenderer implements StatRenderer {
    private final LogReport report;

    public MarkdownRenderer(LogReport report) {
        this.report = report;
    }

    @Override
    public String render() {
        return generalInfoTable()
            + resourceTable()
            + responseCodeTable()
            + remoteAddressTable()
            + userAgentTable();
    }

    private String generalInfoTable() {
        StringBuilder result = new StringBuilder();
        OffsetDateTime from = report.arguments().from();
        OffsetDateTime to = report.arguments().to();
        result.append("#### Общая информация\n\n")
            .append("|      Метрика      |    Значение |\n")
            .append("|:-----------------:|------------:|\n")
            .append("|     Файл(-ы)      | ").append(report.fileNames().get(0)).append(" |\n");
        for (int i = 1; i < report.fileNames().size(); i++) {
            result.append("|             | ").append(report.fileNames().get(i)).append(" |\n");
        }
        result.append("|    Начальная дата     |  ").append((from != null) ? from.toLocalDate() : " ").append(" |\n")
            .append("|     Конечная дата     |  ").append((to != null) ? to.toLocalDate() : " ").append(" |\n")
            .append("|  Количество запросов  |  ").append(report.logsCount()).append(" |\n")
            .append("| Средний размер ответа |  ").append(report.avgRespSize()).append("b |\n");
        return result.toString();
    }

    private String resourceTable() {
        StringBuilder result = new StringBuilder();
        result.append("\n####  Запрашиваемые ресурсы\n\n")
            .append("|   Ресурс    | Количество |\n")
            .append("|:-----------:|-----------:|\n");
        for (var res : report.reqResourcesCount()) {
            result.append("|  `").append(res.getKey()).append("`  |   ").append(res.getValue()).append("  |\n");
        }
        return result.toString();
    }

    private String responseCodeTable() {
        StringBuilder result = new StringBuilder();
        result.append("\n#### Коды ответа\n\n")
            .append("| Код |        Имя        | Количество |\n")
            .append("|:---:|:-----------------:|-----------:|\n");
        for (var code : report.respCodesCount()) {
            result.append("| ")
                .append(code.getKey()).append(" | ").append(STATUS_CODE_NAMES.get(code.getKey()))
                .append(" | ").append(code.getValue()).append(" |\n");
        }
        return result.toString();
    }

    private String remoteAddressTable() {
        StringBuilder result = new StringBuilder();
        result.append("\n#### Адреса \n\n")
            .append("|    Адрес    |  Количество |\n")
            .append("|:-----------:|------------:|\n");
        for (var res : report.remoteAdrCount()) {
            result.append("|  ").append(res.getKey()).append("  |  ").append(res.getValue()).append("  |\n");
        }
        return result.toString();
    }

    private String userAgentTable() {
        StringBuilder result = new StringBuilder();
        result.append("\n#### Пользовательский агент\n\n")
            .append("|   Агент    | Количество |\n")
            .append("|:----------:|-----------:|\n");
        for (var agents : report.userAgentCount()) {
            result.append("|  ").append(agents.getKey()).append("  |  ").append(agents.getValue()).append("  |\n");
        }
        return result.toString();
    }
}
