package edu.project3.logsParse;

import java.time.OffsetDateTime;

public record LogRecord(String remoteAdr, OffsetDateTime time, String requestedResource, String responseCode,
                        long responseSize, String userAgent) {
}
