package edu.hw3;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class Task1Test {
    @Test
    void normalTest1() {
        String input = "Hello world!";

        String out = Task1.atbash(input);

        assertThat(out)
            .isEqualTo("Svool dliow!");
    }

    @Test
    void normalTest2() {
        String input = "Any fool can write code that a computer can understand.";

        String out = Task1.atbash(input);

        assertThat(out)
            .isEqualTo("Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw.");
    }

    @Test
    void nullInput() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(()->{
                Task1.atbash(null);
            });
    }

    @Test
    void emptyInput() {
        String input = "";

        String out = Task1.atbash(input);

        assertThat(out)
            .isEqualTo("");
    }

    @Test
    void notOnlyLatinAlphabet() {
        String input = "Hello Друг .{}[]''@\\|/";

        String out = Task1.atbash(input);

        assertThat(out)
            .isEqualTo("Svool Друг .{}[]''@\\|/");
    }
}
