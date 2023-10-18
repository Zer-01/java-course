package edu.project1;

import org.jetbrains.annotations.NotNull;

public interface Dictionary {
    @NotNull String randomWord();

    @NotNull String wordAtNumber(int number);
}
