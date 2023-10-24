package edu.project1;

import java.util.Arrays;

import edu.project1.GuessResults.*;
import org.jetbrains.annotations.NotNull;

public class Session {
    private final String answer;
    private final char[] userAnswer;
    private final int maxAttempts;
    private int attempts = 0;
    private boolean inProgress = true;
    private int openedLetters = 0;

    public Session(String answer, int maxAttempts) {
        this.answer = answer;
        this.maxAttempts = maxAttempts;
        this.userAnswer = new char[answer.length()];
        Arrays.fill(userAnswer, '*');
        if (answer.length() < 2) {
            inProgress = false;
        }
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public @NotNull GuessResult guess(char letter) {
        GuessResult result;
        if (isAnswerRight(letter)) {
            modifyAnswer(letter);
            if (openedLetters < answer.length()) {
                result = new SuccessfulGuess(userAnswer, attempts, maxAttempts);
            } else {
                inProgress = false;
                result = new Win(userAnswer, attempts, maxAttempts);
            }
        } else {
            attempts++;
            if (attempts < maxAttempts) {
                result = new FailedGuess(userAnswer, attempts, maxAttempts);
            } else {
                result = new Defeat(userAnswer, attempts, maxAttempts);
                inProgress = false;
            }
        }
        return result;
    }

    public @NotNull GuessResult giveUp() {
        inProgress = false;
        return new GiveUp(userAnswer, attempts, maxAttempts);
    }

    private boolean isAnswerRight(char letter) {
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == letter) {
                return true;
            }
        }
        return false;
    }

    private void modifyAnswer(char letter) {
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == letter && userAnswer[i] == '*') {
                userAnswer[i] = letter;
                openedLetters++;
            }
        }
    }
}
