package edu.project3.arguments;

import java.time.OffsetDateTime;

public record ArgumentContainer(String file, OffsetDateTime from, OffsetDateTime to, PrintFormat format) {

}
