package edu.project1.GuessResults;

import org.jetbrains.annotations.NotNull;

public record Win(char[] state, int attempt, int maxAttempts) implements GuessResult {
    public @NotNull String message() {
        return "You won!";
    }
}
