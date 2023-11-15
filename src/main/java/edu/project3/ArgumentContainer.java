package edu.project3;

import java.time.OffsetDateTime;

public record ArgumentContainer(String file, OffsetDateTime from, OffsetDateTime to, PrintFormat format) {
    public enum PrintFormat {
        ADOC,
        MARKDOWN
    }
}
