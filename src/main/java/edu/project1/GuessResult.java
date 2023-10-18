package edu.project1;

import org.jetbrains.annotations.NotNull;

public sealed interface GuessResult {
    char[] state();

    int attempt();

    int maxAttempts();

    @NotNull String message();

    record Defeat(char[] state, int attempt, int maxAttempts) implements GuessResult {
        public @NotNull String message() {
            return "You lost";
        }
    }

    record Win(char[] state, int attempt, int maxAttempts) implements GuessResult {
        public @NotNull String message() {
            return "You won!";
        }
    }

    record SuccessfulGuess(char[] state, int attempt, int maxAttempts) implements GuessResult {
        public @NotNull String message() {
            return "Correct!";
        }
    }

    record FailedGuess(char[] state, int attempt, int maxAttempts) implements GuessResult {
        public @NotNull String message() {
            return "Wrong!";
        }
    }

    record GiveUp(char[] state, int attempt, int maxAttempts) implements GuessResult {
        public @NotNull String message() {
            return "You gave up";
        }
    }
}
