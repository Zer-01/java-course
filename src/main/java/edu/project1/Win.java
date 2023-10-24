package edu.project1;

import org.jetbrains.annotations.NotNull;

record Win(char[] state, int attempt, int maxAttempts) implements GuessResult {
    public @NotNull String message() {
        return "You won!";
    }
}
