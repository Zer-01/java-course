package edu.project1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Project1Test {
    @Test
    void defeatTest() {
        Session session = new Session("abcd", 2);
        GuessResult result;

        result = session.guess('e'); //неудачная попытка 1
        result = session.guess('f'); //неудачная попытка 2

        assertThat(result instanceof GuessResult.Defeat)
            .isTrue();
    }

    @Test
    void winTest() {
        Session session = new Session("abc", 2);
        GuessResult result;

        result = session.guess('a'); //удачная попытка 1
        result = session.guess('b'); //удачная попытка 2
        result = session.guess('c'); //удачная попытка 3

        assertThat(result instanceof GuessResult.Win)
            .isTrue();
        assertThat(new String(result.state()).equals("abc"))
            .isTrue();
    }

    @Test
    void changeStateTest1() {
        Session session = new Session("abcd", 2);
        GuessResult result;

        result = session.guess('a');

        assertThat(result instanceof GuessResult.SuccessfulGuess)
            .isTrue();
        assertThat(new String(result.state()).equals("a***"))
            .isTrue();
    }

    @Test
    void changeStateTest2() {
        Session session = new Session("abcd", 2);
        GuessResult result;

        result = session.guess('t');

        assertThat(result instanceof GuessResult.FailedGuess)
            .isTrue();
        assertThat(new String(result.state()).equals("****"))
            .isTrue();
    }

    @Test
    void wrongAnswerLength() {
        Session session = new Session("a", 2);

        assertThat(session.isInProgress())
            .isFalse(); //Игра заканчивается до начала при некорректной длине загадонного слова
    }
}
