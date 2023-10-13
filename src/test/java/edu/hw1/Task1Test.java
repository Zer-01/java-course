package edu.hw1;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    @Test
    void parseMinutes() {
        int result;
        String time = "10:00";

        result = Task1.videoLengthToSeconds(time);

        assertThat(result)
            .isEqualTo(600);
    }

    @Test
    void parseTime() {
        int result;
        String time = "20:35";

        result = Task1.videoLengthToSeconds(time);

        assertThat(result)
            .isEqualTo(20 * 60 + 35);
    }

    @Test
    void parseWrongSeconds() {
        int result;
        String time = "00:61";

        result = Task1.videoLengthToSeconds(time);

        assertThat(result)
            .isEqualTo(-1);
    }

    @Test
    void wrongFormat() {
        int result;
        String time = "0030";

        result = Task1.videoLengthToSeconds(time);

        assertThat(result)
            .isEqualTo(-1);
    }
}
