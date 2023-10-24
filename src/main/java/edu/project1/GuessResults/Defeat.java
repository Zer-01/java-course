package edu.project1.GuessResults;

import org.jetbrains.annotations.NotNull;

public record Defeat(char[] state, int attempt, int maxAttempts) implements GuessResult {
    public @NotNull String message() {
        return "You lost";
    }
}
