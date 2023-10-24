package edu.project1.GuessResults;

import org.jetbrains.annotations.NotNull;

public record FailedGuess(char[] state, int attempt, int maxAttempts) implements GuessResult {
    public @NotNull String message() {
        return "Wrong!";
    }
}
