package edu.project1;

import java.util.Random;
import org.jetbrains.annotations.NotNull;

public class HangmanDictionary implements Dictionary {
    private Random rand = new Random();
    private final String[] words = new String[] {
        "boat",
        "jupiter",
        "attempt"
    };

    public @NotNull String randomWord() {
        return words[rand.nextInt(words.length)];
    }
}
