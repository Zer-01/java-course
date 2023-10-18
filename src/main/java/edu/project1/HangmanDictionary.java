package edu.project1;

import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class HangmanDictionary implements Dictionary {
    private Random rand = new Random();
    private final String[] words = new String[] {
        "word1",
        "word2",
        "word3",
        "word4"
    };

    public @NotNull String wordAtNumber(int number) {
        if (number < 0 || number > words.length) {
            throw new IllegalArgumentException();
        }
        return words[number];
    }

    public @NotNull String randomWord() {
        return words[rand.nextInt(words.length)];
    }
}
