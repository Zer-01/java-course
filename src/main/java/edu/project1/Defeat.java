package edu.project1;

import org.jetbrains.annotations.NotNull;

record Defeat(char[] state, int attempt, int maxAttempts) implements GuessResult {
    public @NotNull String message() {
        return "You lost";
    }
}
