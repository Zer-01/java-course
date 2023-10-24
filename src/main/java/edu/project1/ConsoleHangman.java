package edu.project1;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleHangman {
    private final static Logger LOGGER = LogManager.getLogger();
    private final Scanner scanner = new Scanner(System.in);
    private final static int MAX_ATTEMPTS = 5;
    private final static String EXIT_STR = "exit";

    public void run() {
        Session session;
        HangmanDictionary dictionary = new HangmanDictionary();
        do {
            session = new Session(dictionary.randomWord(), MAX_ATTEMPTS);
            while (session.isInProgress()) {
                GuessResult result = tryGuess(session, letterInput());
                if (result != null) {
                    printState(result);
                }
            }
        } while (continueRequest());
    }

    private boolean continueRequest() {
        String input;
        LOGGER.info("Continue?(y/n)");
        do {
            input = scanner.nextLine();
        } while (input.charAt(0) != 'y' & input.charAt(0) != 'n');
        return input.charAt(0) == 'y';
    }

    private void printState(GuessResult guess) {
        LOGGER.info(guess.message());
        LOGGER.info(
            "Mistake " + Integer.toString(guess.attempt()) + " out of " + Integer.toString(guess.maxAttempts()));
        LOGGER.info("The word: " + new String(guess.state()));
        LOGGER.info("=================================================");
    }

    private GuessResult tryGuess(Session session, String input) {
        if (input == null || input.length() != 1 && !input.equals(EXIT_STR)) {
            return null;
        }
        return (input.equals(EXIT_STR)) ? session.giveUp() : session.guess(input.toLowerCase().charAt(0));
    }

    private String letterInput() {
        LOGGER.info("Guess a letter:");
        return scanner.nextLine();
    }
}
