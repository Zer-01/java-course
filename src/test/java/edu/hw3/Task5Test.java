package edu.hw3;

import edu.hw3.Task5.Contact;
import edu.hw3.Task5.ContactParser;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task5Test {
    @Test
    void ASCTest() {
        String[] input = {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"};
        String expectedResult = "[Thomas Aquinas, Rene Descartes, David Hume, John Locke]";

        Contact[] result = ContactParser.parseContacts(input, "ASC");

        assertThat(Arrays.toString(result))
            .isEqualTo(expectedResult);
    }

    @Test
    void DESCTest() {
        String[] input = {"Paul Erdos", "Leonhard Euler", "Carl Gauss"};
        String expectedResult = "[Carl Gauss, Leonhard Euler, Paul Erdos]";

        Contact[] result = ContactParser.parseContacts(input, "DESC");

        assertThat(Arrays.toString(result))
            .isEqualTo(expectedResult);
    }

    @Test
    void sortWithNameTest() {
        String[] input = {"Paul Erdos", "Leonhard", "Carl Gauss"};
        String expectedResult = "[Paul Erdos, Carl Gauss, Leonhard]";

        Contact[] result = ContactParser.parseContacts(input, "ASC");

        assertThat(Arrays.toString(result))
            .isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void IllegalAE(String[] input) {
        Contact[] result = ContactParser.parseContacts(input, "ASC");
        assertThat(result)
            .isEqualTo(new Contact[] {});
    }

    @Test
    void invalidParam() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> {
                ContactParser.parseContacts(new String[] {}, "desc");
            });
    }
}
