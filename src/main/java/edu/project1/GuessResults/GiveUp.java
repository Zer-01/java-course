package edu.project1.GuessResults;

import org.jetbrains.annotations.NotNull;

public record GiveUp(char[] state, int attempt, int maxAttempts) implements GuessResult {
    public @NotNull String message() {
        return "You gave up";
    }
}
