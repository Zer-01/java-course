package edu.project1;

import org.jetbrains.annotations.NotNull;

record FailedGuess(char[] state, int attempt, int maxAttempts) implements GuessResult {
    public @NotNull String message() {
        return "Wrong!";
    }
}
