package edu.project1;

import org.jetbrains.annotations.NotNull;

public sealed interface GuessResult permits Defeat, FailedGuess, GiveUp, SuccessfulGuess, Win {
    char[] state();

    int attempt();

    int maxAttempts();

    @NotNull String message();
}
