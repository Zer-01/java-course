package edu.project1;

import org.jetbrains.annotations.NotNull;

record GiveUp(char[] state, int attempt, int maxAttempts) implements GuessResult {
    public @NotNull String message() {
        return "You gave up";
    }
}
