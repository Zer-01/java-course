package edu.project3.output;

import edu.project3.analyze.LogReport;
import java.time.OffsetDateTime;

@SuppressWarnings("MultipleStringLiterals")
public class AdocRenderer implements StatRenderer {
    private final LogReport report;

    public AdocRenderer(LogReport report) {
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
        result.append("\n\nОбщая информация\n")
            .append("|===\n")
            .append("|Метрика|Значение\n")
            .append("|Файл(-ы)|").append(report.fileNames().get(0)).append("\n");
        for (int i = 1; i < report.fileNames().size(); i++) {
            result.append("| | ").append(report.fileNames().get(i)).append("\n");
        }
        result.append("|Начальная дата|  ").append((from != null) ? from.toLocalDate() : " ").append("\n")
            .append("|Конечная дата|  ").append((to != null) ? to.toLocalDate() : " ").append("\n")
            .append("|Количество запросов|  ").append(report.logsCount()).append("\n")
            .append("|Средний размер ответа|  ").append(report.avgRespSize()).append("b\n")
            .append("|===\n");
        return result.toString();
    }

    private String resourceTable() {
        StringBuilder result = new StringBuilder();
        result.append("\n\nЗапрашиваемые ресурсы\n")
            .append("|===\n")
            .append("|Ресурс|Количество\n");
        for (var res : report.reqResourcesCount()) {
            result.append("|`").append(res.getKey()).append("`|").append(res.getValue()).append("\n");
        }
        result.append("|===\n");
        return result.toString();
    }

    private String responseCodeTable() {
        StringBuilder result = new StringBuilder();
        result.append("\n\nКоды ответа\n")
            .append("|===\n")
            .append("|Код|Имя|Количество\n");
        for (var code : report.respCodesCount()) {
            result.append("|")
                .append(code.getKey()).append("|").append(STATUS_CODE_NAMES.get(code.getKey()))
                .append("|").append(code.getValue()).append("\n");
        }
        result.append("|===\n");
        return result.toString();
    }

    private String remoteAddressTable() {
        StringBuilder result = new StringBuilder();
        result.append("\n\nАдреса\n")
            .append("|===\n")
            .append("|Адрес|Количество\n");
        for (var res : report.remoteAdrCount()) {
            result.append("|").append(res.getKey()).append("|").append(res.getValue()).append("\n");
        }
        result.append("|===\n");
        return result.toString();
    }

    private String userAgentTable() {
        StringBuilder result = new StringBuilder();
        result.append("\n\nПользовательский агент\n")
            .append("|===\n")
            .append("|Агент|Количество\n");
        for (var agents : report.userAgentCount()) {
            result.append("|").append(agents.getKey()).append("|").append(agents.getValue()).append("\n");
        }
        result.append("|===\n");
        return result.toString();
    }
}
